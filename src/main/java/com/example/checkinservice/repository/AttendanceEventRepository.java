package com.example.checkinservice.repository;

import com.example.checkinservice.model.AttendanceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Long> {
    Optional<AttendanceEvent> findTopByEmployeeIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(String employeeId);
}
