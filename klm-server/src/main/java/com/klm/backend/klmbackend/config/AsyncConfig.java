package com.klm.backend.klmbackend.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
     
	/**
	 * Override default configuration of ThreadPoolTaskExecutor
	 */
    @Override
    public Executor getAsyncExecutor() {
    	
    	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("async-task-");	// adding custom prefix as per assignment
        executor.initialize();
        
        return executor;
    }
     
}