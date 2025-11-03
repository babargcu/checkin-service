package com.example.checkinservice.messaging;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.example.checkinservice.model.AttendanceEvent;

@Component
public class LegacyApiConsumer {

    @RabbitListener(queues = "attendanceQueue")
    public void handleAttendance(AttendanceEvent event) {
        System.out.println("Sending to legacy system: Employee " 
            + event.getEmployeeId() + " worked " + event.getHoursWorked() + " hours.");
                try {
            Thread.sleep(500); // simulate latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
