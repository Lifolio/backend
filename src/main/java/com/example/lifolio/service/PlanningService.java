package com.example.lifolio.service;

import com.example.lifolio.dto.planning.PlanningYearReq;
import com.example.lifolio.dto.planning.PlanningYearRes;
import com.example.lifolio.entity.PlanningYear;
import com.example.lifolio.repository.PlanningYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanningService {

    private final PlanningYearRepository planningYearRepository;
    public void setGoalOfYear(Long userId , PlanningYearReq.PostGoalOfYearReq postGoalOfYearReq) {
        PlanningYear toSavePlanningYear = PlanningYear.builder()
                .userId(userId)
                .title(postGoalOfYearReq.getTitle())
                .date(postGoalOfYearReq.getDate())
                .build();

        planningYearRepository.save(toSavePlanningYear);
    }

    public List<PlanningYearRes.GetGoalOfYearRes> getGoalsByUserId(Long userId) {
        List<PlanningYear> PY_List = planningYearRepository.findAllByUserIdOrderByDateAsc(userId);
        List<PlanningYearRes.GetGoalOfYearRes> getGoalOfYearResList = new ArrayList<>();

        if(PY_List.isEmpty()) return null;

        for(PlanningYear p: PY_List){
            PlanningYearRes.GetGoalOfYearRes getGoalOfYearRes = new PlanningYearRes.GetGoalOfYearRes(p.getSuccess(),p.getTitle());
            getGoalOfYearResList.add(getGoalOfYearRes);
        }
        return getGoalOfYearResList;
    }

    public void updateGoalOfYear(Long userId, Long planningYearId, PlanningYearReq.UpdateGoalOfYearReq updateGoalOfYearReq) {
        PlanningYear planningYear = planningYearRepository.getOne(planningYearId);
        planningYear.updateGoalOfYear(updateGoalOfYearReq.getDate(),updateGoalOfYearReq.getTitle());
        planningYearRepository.save(planningYear);
    }

    public void updateGoalOfYearSuccess(Long planningYearId, PlanningYearReq.UpdateGoalOfYearSuccessReq updateGoalOfYearSuccessReq) {
        PlanningYear planningYear = planningYearRepository.getOne(planningYearId);
        planningYear.updateGoalOfYearSuccess(updateGoalOfYearSuccessReq.getSuccess());
        planningYearRepository.save(planningYear);
    }

    public void deleteGoalOfYear(Long planningYearId) {
        planningYearRepository.deleteById(planningYearId);
    }
}
