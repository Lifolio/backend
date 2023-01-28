package com.example.lifolio.repository;

import com.example.lifolio.entity.PlanningYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanningYearRepository extends JpaRepository<PlanningYear, Long> {
    List<PlanningYear> findAllByUserIdOrderByDateAsc(Long userId);

    List<PlanningYear> findByUserId(Long userId);

}
