package com.echovale.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/20 21:11
 */

@EnableAsync
@Configuration
public class ServiceAsyncConfig {
    @Bean("ServiceNoneCore")
    public ThreadPoolTaskExecutor serviceNoneCoreThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(19);
        executor.setQueueCapacity(1000);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setThreadNamePrefix("service-none-core-");
        executor.initialize();
        return executor;
    }
}
