package com.example.lifolio.repository;

import com.example.lifolio.entity.GoalOfYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalOfYearRepository extends JpaRepository<GoalOfYear, Long> {

    GoalOfYear findByUserIdAndYear(Long userId, int year);
}