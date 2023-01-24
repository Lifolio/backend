package com.example.lifolio.repository;

import com.example.lifolio.entity.Planning;
import com.example.lifolio.entity.PlanningMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanningMonthRepository extends JpaRepository<PlanningMonth, Long> {

    List<PlanningMonth> findByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDateTime startTime, LocalDateTime finishTime);
}