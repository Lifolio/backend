package com.example.lifolio.repository;

import com.example.lifolio.entity.GoalOfYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalOfYearRepository extends JpaRepository<GoalOfYear, Long> {

    Optional<GoalOfYear> findByUserIdAndYear(Long userId, int year);

    List<GoalOfYear> findAllByUserIdOrderByYearAsc(Long userId);


    Optional<GoalOfYear> findTop1ByUserIdAndYearOrderByCreatedAtDesc(Long userId, int year);
}