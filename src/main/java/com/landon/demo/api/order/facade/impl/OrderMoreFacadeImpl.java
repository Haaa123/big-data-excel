package com.landon.demo.api.order.facade.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.landon.demo.api.order.dto.OrderExcelDto;
import com.landon.demo.api.order.facade.OrderMoreFacade;
import com.landon.demo.base.utils.BeanUtils;
import com.landon.demo.module.order.entity.OrderEntity;
import com.landon.demo.module.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author SSP
 * @date 2021/3/3 0003 10:55
 */
@Service
public class OrderMoreFacadeImpl implements OrderMoreFacade {

    private final OrderService orderService;

    public OrderMoreFacadeImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String exportMore() {
        String url = "http://localhost/fileName.zip";
        String fileName = this.createExcel();
        return url.replace("fileName", fileName);
    }

    /**
     * 如果采用一次性处理，那么大批量的数据直接驻入内存将会直接导致OOM
     * 对于查数据的可选方案：仍利用分页原理，采用滚动查询策略，性能表现更佳
     * 在查数据的做法上，采用多线程查询，每个线程分到自己范围内的数据，异步组装Excel,为了提高系统性能
     * 考虑每个线程查询出2w条数据集合输出到一个独立的Excel，那么100,000,0 条数据将会存在50个Excel
     * 将这50个Excel放在在一个文件夹，最终可打包成zip返回
     * @return 文件名
     */
    private String createExcel() {
        // zip文件名
        String zipFileName = "2020订单数据";
        ZipOutputStream zipOutputStream;
        String filePath = "files/" + zipFileName + ".zip";
        File file = new File(filePath);
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
            int processor = 10;
            ExecutorService executorService = new ThreadPoolExecutor(processor, processor, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), new ThreadFactoryBuilder().setNameFormat("EasyExcel-task-%d").build());
            CountDownLatch countDownLatch = new CountDownLatch(processor);
            for (int i = 1; i <= processor; i++) {
                long start = ((i - 1) * 10000L + 1);
                long offset = 10000L;
                int finalI = i;
                executorService.execute(() -> streamWriteToExcel(start, offset, countDownLatch, zipOutputStream, finalI));
            }
            countDownLatch.await();
            zipOutputStream.close();
            executorService.shutdown();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return zipFileName;
    }

    /**
     * 写Excel
     * @param start 开始位置
     * @param offset 偏移量
     * @param countDownLatch 线程计数
     */
    private void streamWriteToExcel(long start, long offset, CountDownLatch countDownLatch, ZipOutputStream zipOutputStream, int finalI) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String excelFileName = "2020订单数据";
        long startTime = System.currentTimeMillis();

        // 用于统计是否到了偏移量位置
        long count = 0L;
        ExcelWriter writer = EasyExcel.write(outputStream, OrderExcelDto.class).build();
        WriteSheet writeSheet = new WriteSheet();
        writeSheet.setSheetName("订单信息");

        for(;;) {
            // 为防止oom，每次查询100条数据，直到到达该线程的目标最大值
            long segment = 1000L;
            List<OrderEntity> orderEntityList = orderService.findAllBySubsectionSearch(false, start, segment);
            // 可能存在该线程拿到的理论数据范围是大于实际范围的
            boolean isEmpty = orderEntityList.isEmpty();
            boolean isEnd = count == offset;
            if (isEmpty || isEnd) {
                break;
            } else {
                count += segment;
                // Java Object -> ExcelData Object
                List<OrderExcelDto> orderExcelDtoList = BeanUtils.convert(orderEntityList, OrderExcelDto::new);
                start = orderEntityList.stream().map(OrderEntity::getId).max(Long::compareTo).orElse(Long.MAX_VALUE);
                writer.write(orderExcelDtoList, writeSheet);
            }
        }
        writer.finish();

        // 向压缩流中写入excel
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ZipEntry zipEntry = new ZipEntry(excelFileName + "_" + finalI + ".xlsx");
            zipOutputStream.putNextEntry(zipEntry);
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reentrantLock.unlock();
        }
        countDownLatch.countDown();
        long endTime = System.currentTimeMillis();
        System.out.println("当前执行的线程是：" + Thread.currentThread().getName() + ", 导出Excel耗时：" + (endTime - startTime));
    }
}
