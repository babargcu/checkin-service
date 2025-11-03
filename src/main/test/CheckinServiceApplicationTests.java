package com.example.checkinservice;

import com.example.checkinservice.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
class CheckinServiceApplicationTests {

    @Autowired
    private AttendanceService service;

    @Test
    void testCheckInAndOut() {
        service.checkIn("emp123");
        service.checkOut("emp123");
        System.out.println("Test check-in/check-out completed successfully");
    }
}
