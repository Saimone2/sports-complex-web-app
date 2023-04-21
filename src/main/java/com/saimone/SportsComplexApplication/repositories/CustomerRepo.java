package com.saimone.SportsComplexApplication.repositories;

import com.saimone.SportsComplexApplication.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
