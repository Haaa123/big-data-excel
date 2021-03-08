package com.landon.demo.api.order.facade.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.landon.demo.api.order.dto.OrderExcelDto;
import com.landon.demo.api.order.facade.OrderSingletonFacade;
import com.landon.demo.base.utils.BeanUtils;
import com.landon.demo.module.order.entity.OrderEntity;
import com.landon.demo.module.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author SSP
 * @date 2021/3/3 0003 10:55
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderSingletonFacadeImpl implements OrderSingletonFacade {

    private final OrderService orderService;

    public OrderSingletonFacadeImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void exportSingleton(HttpServletResponse response) {
        long startTime = System.currentTimeMillis();

        String fileName = new String("2020年订单数据".getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("application/vnd.openmosix-officiated.spreadsheet.sheet");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        List<OrderEntity> orderEntityList;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            ExcelWriter writer = EasyExcel
                    .write()
                    .autoCloseStream(true)
                    .excelType(ExcelTypeEnum.XLSX)
                    .file(os)
                    .head(OrderExcelDto.class)
                    .build();
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetName("订单信息");

            long segment = 1000L;
            long start = 0L;
            for(;;) {
                // 每次查询1000条数据到堆内存，直到到达目标最大值
                orderEntityList = orderService.findAllBySubsectionSearch(false, start, segment);
                boolean isNull = orderEntityList.isEmpty();
                if (isNull) {
                    writer.finish();
                    break;
                } else {
                    // Java Object -> ExcelData Object
                    List<OrderExcelDto> orderExcelDtoList = BeanUtils.convert(orderEntityList, OrderExcelDto::new);
                    start = orderEntityList.stream().map(OrderEntity::getId).max(Long::compareTo).orElse(Long.MAX_VALUE);
                    writer.write(orderExcelDtoList, writeSheet);
                    orderEntityList.clear();
                }
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("当前执行的线程是：" + Thread.currentThread().getName() + ", 导出Excel耗时：" + (endTime - startTime));
    }
}
