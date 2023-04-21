package com.saimone.SportsComplexApplication.repositories;

import com.saimone.SportsComplexApplication.models.MedicalExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalExaminationRepo extends JpaRepository<MedicalExamination, Integer> {
    List<MedicalExamination> findAllByCustomerId(Integer customer_id);
}
