package com.saimone.SportsComplexApplication.repositories;

import com.saimone.SportsComplexApplication.models.CardSets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardSetsRepo extends JpaRepository<CardSets, Integer> {
    List<CardSets> findAllByClubCardId(Integer clubcard_id);

}
