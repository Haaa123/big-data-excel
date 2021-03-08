package com.landon.demo.api.order.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * @author SSP
 * @date 2021/3/3 0003 20:44
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {


    @Override
    public Executor getAsyncExecutor() {
        // 定义线程池
        int processor = 50;
        return new ThreadPoolExecutor(processor, processor, 1000, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(), new ThreadFactoryBuilder().setNameFormat("EasyExcel-task-%d").build());
    }
}
