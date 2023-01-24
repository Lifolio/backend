package com.example.lifolio.repository;

import com.example.lifolio.entity.Planning;
import com.example.lifolio.entity.PlanningWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanningWeekRepository extends JpaRepository<PlanningWeek, Long> {
    List<PlanningWeek> findByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDateTime startDate, LocalDateTime finishDate);

}