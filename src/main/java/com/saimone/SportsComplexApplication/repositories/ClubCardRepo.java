package com.saimone.SportsComplexApplication.repositories;

import com.saimone.SportsComplexApplication.models.ClubCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubCardRepo extends JpaRepository<ClubCard, Integer> {
    List<ClubCard> findAllByCustomerId(Integer customer_id);
}
