package com.example.checkinservice.service;

import com.example.checkinservice.model.AttendanceEvent;
import com.example.checkinservice.repository.AttendanceEventRepository;
import com.example.checkinservice.messaging.QueueProducer;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceEventRepository repo;

    @Autowired
    private QueueProducer producer;

    /**
     * Handles both check-in and check-out for a given employee.
     * If the employee has an active check-in (checkOutTime == null), it performs check-out.
     * Otherwise, it creates a new check-in record.
     *
     * @param employeeId The employee ID
     * @return Message indicating the action performed
     */
  public String handleCheck(String employeeId) {
    if (employeeId == null || employeeId.isBlank()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employeeId is required");
    }

    Optional<AttendanceEvent> ongoing = repo
            .findTopByEmployeeIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(employeeId);

    if (ongoing.isPresent()) {
        AttendanceEvent e = ongoing.get();
        e.setCheckOutTime(LocalDateTime.now());

        double hours = Duration.between(e.getCheckInTime(), e.getCheckOutTime()).toMinutes() / 60.0;
        e.setHoursWorked(hours);
        e.setEmployeeEmail("babarali.rana7@gmail.com");

        // Save first
        repo.save(e);

        try {
            producer.enqueue(e);
        } catch (Exception ex) {
            System.err.println("Failed to enqueue attendance event: " + ex.getMessage());
            // Optionally, store a flag in the DB to retry later
        }

        return "Check-out recorded. Hours worked: " + String.format("%.2f", hours);
    } else {
        AttendanceEvent e = new AttendanceEvent();
        e.setEmployeeId(employeeId);
        e.setCheckInTime(LocalDateTime.now());
        repo.save(e);

        return "Check-in recorded";
    }
}

}
