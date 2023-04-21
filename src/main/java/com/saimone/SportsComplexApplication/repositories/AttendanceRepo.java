package com.saimone.SportsComplexApplication.repositories;

import com.saimone.SportsComplexApplication.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
    List<Attendance> findAllByCustomerId(Integer customer_id);
}
