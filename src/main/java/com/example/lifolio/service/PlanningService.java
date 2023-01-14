package com.example.lifolio.service;

import com.example.lifolio.dto.home.HomeRes;
import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.entity.GoalOfYear;
import com.example.lifolio.entity.PlanningYear;
import com.example.lifolio.repository.PlanningYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanningService {

    private final PlanningYearRepository planningYearRepository;
    public void setGoalOfYear(Long userId , PlanningReq.PostGoalOfYearReq postGoalOfYearReq) {
        PlanningYear toSavePlanningYear = PlanningYear.builder()
                .userId(userId)
                .title(postGoalOfYearReq.getTitle())
                .date(postGoalOfYearReq.getDate())
                .success(0)
                .build();

        planningYearRepository.save(toSavePlanningYear);
    }
}
