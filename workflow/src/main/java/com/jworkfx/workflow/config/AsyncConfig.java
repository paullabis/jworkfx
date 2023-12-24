package com.jworkfx.workflow.config;

import com.jworkfx.workflow.WorkflowProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "workflowThreadPoolExecutor")
    public Executor workflowThreadPoolExecutor(WorkflowProperties properties) {
        return Executors.newFixedThreadPool(properties.getThreadPoolSize());
    }

}
