package com.example.lifolio.repository;


import com.example.lifolio.entity.MyFolioWith;
import com.example.lifolio.entity.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {

    List<Planning> findTByUserIdAndStartDateBetweenOrderByStartDateAsc(Long userId, LocalDateTime startDate, LocalDateTime finishDate);

    List<Planning> findTop3ByUserIdAndStartDateBetweenOrderByStartDateAsc(Long userId, LocalDateTime startDate, LocalDateTime finishDate);
}
