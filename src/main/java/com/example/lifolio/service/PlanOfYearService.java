package com.example.lifolio.service;

import com.example.lifolio.dto.planOfYear.PlanOfYearReq;
import com.example.lifolio.dto.planOfYear.PlanOfYearRes;
import com.example.lifolio.entity.PlanOfYear;
import com.example.lifolio.repository.PlanOfYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlanOfYearService {

    private final PlanOfYearRepository planOfYearRepository;

    public void setPlanOfYear(Long userId, PlanOfYearReq.PostPlanOfYearReq postPlanOfYearReq) {
        PlanOfYear toSavePlanOfYear = PlanOfYear.builder()
                .userId(userId)
                .title(postPlanOfYearReq.getTitle())
                .planYear(postPlanOfYearReq.getPlanYear())
                .planMonth(postPlanOfYearReq.getPlanMonth())
                .build();

        planOfYearRepository.save(toSavePlanOfYear);
    }


    public void updatePlanOfYear(Long userId, Long PlanOfYearId, PlanOfYearReq.UpdatePlanOfYearReq updatePlanOfYearReq) {
        PlanOfYear planOfYear = planOfYearRepository.getOne(PlanOfYearId);

        planOfYearRepository.save(planOfYear);

    }

    public void deletePlanOfYear(Long PlanOfYearId) { planOfYearRepository.deleteById(PlanOfYearId); }

}
