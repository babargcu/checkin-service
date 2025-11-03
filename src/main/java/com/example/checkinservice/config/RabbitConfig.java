package com.example.checkinservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue attendanceQueue() {
        return new Queue("attendanceQueue", true);
    }
}
