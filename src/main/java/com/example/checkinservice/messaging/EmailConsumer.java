package com.example.checkinservice.messaging;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.example.checkinservice.model.AttendanceEvent;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class EmailConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "attendanceQueue")
    public void sendEmail(AttendanceEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmployeeEmail()); 
        message.setSubject("Work Hours Recorded");
        message.setText("You worked " + event.getHoursWorked() + " hours today!");
        
        mailSender.send(message);
        System.out.println("Email sent to " + event.getEmployeeEmail());
    }
}
