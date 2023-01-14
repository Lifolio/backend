package com.example.lifolio.repository;

import com.example.lifolio.entity.PlanningYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanningYearRepository extends JpaRepository<PlanningYear, Long> {
    List<PlanningYear> findAllByUserIdOrderByDateAsc(Long userId);

}
