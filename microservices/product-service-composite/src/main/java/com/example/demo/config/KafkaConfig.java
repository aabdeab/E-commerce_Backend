package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class KafkaConfig {

    private final Integer threadPoolSize = 10;
    private final Integer taskQueueSize = 100;

    @Bean
    public Scheduler publishEventScheduler() {
        return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "publish-pool");
    }
}

