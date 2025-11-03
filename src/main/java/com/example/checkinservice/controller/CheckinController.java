package com.example.checkinservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

import com.example.checkinservice.service.AttendanceService;

@RestController
@RequestMapping("/api")
public class CheckinController {

    @Autowired
    private AttendanceService service;

    @PostMapping("/attendance")
    public ResponseEntity<String> handleAttendance(@RequestBody Map<String, String> req) {
        String message = service.handleCheck(req.get("employeeId"));
        return ResponseEntity.ok(message);
    }
}
