package com.saimone.SportsComplexApplication.repositories;

import com.saimone.SportsComplexApplication.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Integer> {
}
