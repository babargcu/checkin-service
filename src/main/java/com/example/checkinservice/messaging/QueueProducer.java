package com.example.checkinservice.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.checkinservice.model.AttendanceEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueueProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public QueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void enqueue(AttendanceEvent e) {
        try {
            String json = objectMapper.writeValueAsString(e);
            rabbitTemplate.convertAndSend("attendanceQueue", json);
        } catch (Exception ex) {
            System.err.println("Failed to enqueue attendance event: " + ex.getMessage());
        }
    }
}

