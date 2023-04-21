package com.saimone.SportsComplexApplication.repositories;

import com.saimone.SportsComplexApplication.models.AttendanceMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceModeRepo extends JpaRepository<AttendanceMode, Integer> {
}
