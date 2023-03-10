package com.example.lifolio.service;

import com.example.lifolio.converter.PlanningConvertor;
import com.example.lifolio.converter.TimeConvertor;
import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.entity.*;
import com.example.lifolio.repository.PlanningMonthRepository;
import com.example.lifolio.repository.PlanningRepository;
import com.example.lifolio.repository.PlanningWeekRepository;
import com.example.lifolio.repository.PlanningYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlanningService {

    private final PlanningYearRepository planningYearRepository;
    private final PlanningRepository planningRepository;
    private final PlanningWeekRepository planningWeekRepository;
    private final PlanningMonthRepository planningMonthRepository;

    public void setGoalOfYear(Long userId , PlanningReq.PostGoalOfYearReq postGoalOfYearReq) {
        PlanningYear toSavePlanningYear = PlanningYear.builder()
                .userId(userId)
                .title(postGoalOfYearReq.getTitle())
                .date(postGoalOfYearReq.getDate())
                .build();

        planningYearRepository.save(toSavePlanningYear);
    }

    public List<PlanningRes.GetGoalOfYearRes> getGoalsByUserId(Long userId) {
        List<PlanningYear> PY_List = planningYearRepository.findAllByUserIdOrderByDateAsc(userId);
        List<PlanningRes.GetGoalOfYearRes> getGoalOfYearResList = new ArrayList<>();

        if(PY_List.isEmpty()) return null;

        for(PlanningYear p: PY_List){
            PlanningRes.GetGoalOfYearRes getGoalOfYearRes = new PlanningRes.GetGoalOfYearRes(p.getSuccess(),p.getTitle());
            getGoalOfYearResList.add(getGoalOfYearRes);
        }
        return getGoalOfYearResList;
    }

    public int getAllGoalofYear(Long userId) {
        int allGoal = 0;
        List<PlanningYear> PlanningYearList = planningYearRepository.findAllByUserIdOrderByDateAsc(userId);
        allGoal = PlanningYearList.size();
        return allGoal;
    }

    public int getSuccessGoalOfYear(Long userId) {
        int successGoal = 0;
        List<PlanningRes.GetSuccessGoalOfYearRes> SuccessGoalOfYearArray = new ArrayList<>();
        List<PlanningYear> PlanningYearList = planningYearRepository.findAllByUserIdOrderByDateAsc(userId);
        for(PlanningYear planningYear: PlanningYearList){
            if (planningYear.getSuccess() == 1) {
                PlanningRes.GetSuccessGoalOfYearRes SuccessGoalOfYear = new PlanningRes.GetSuccessGoalOfYearRes(planningYear.getSuccess());
                SuccessGoalOfYearArray.add(SuccessGoalOfYear);
                successGoal = SuccessGoalOfYearArray.size();
            }
        }
        return successGoal;
    }

    public float getGoalOfYearAchievement(Long userId) {

        float allGoal = 0;
        float successGoal = 0;

        List<PlanningRes.GetSuccessGoalOfYearRes> SuccessGoalOfYearArray = new ArrayList<>();
        List<PlanningYear> PlanningYearList = planningYearRepository.findAllByUserIdOrderByDateAsc(userId);
        allGoal = PlanningYearList.size();

        for(PlanningYear planningYear: PlanningYearList){
            if (planningYear.getSuccess() == 1) {
                PlanningRes.GetSuccessGoalOfYearRes SuccessGoalOfYear = new PlanningRes.GetSuccessGoalOfYearRes(planningYear.getSuccess());
                SuccessGoalOfYearArray.add(SuccessGoalOfYear);
                successGoal = SuccessGoalOfYearArray.size();
            }
        }

        if(allGoal!=0) {
            return (successGoal / allGoal) * 100;
        } else return 0;

    }

    public void updateGoalOfYear(Long userId, Long planningYearId, PlanningReq.UpdateGoalOfYearReq updateGoalOfYearReq) {
        PlanningYear planningYear = planningYearRepository.getOne(planningYearId);
        planningYear.updateGoalOfYear(updateGoalOfYearReq.getDate(),updateGoalOfYearReq.getTitle());
        planningYearRepository.save(planningYear);
    }

    public void updateGoalOfYearSuccess(Long planningYearId, PlanningReq.UpdateGoalOfYearSuccessReq updateGoalOfYearSuccessReq) {
        PlanningYear planningYear = planningYearRepository.getOne(planningYearId);
        planningYear.updateGoalOfYearSuccess(updateGoalOfYearSuccessReq.getSuccess());
        planningYearRepository.save(planningYear);
    }

    public void deleteGoalOfYear(Long planningYearId) {
        planningYearRepository.deleteById(planningYearId);
    }

    public void setTodo(Long userId, PlanningReq.PostPlanningReq postPlanningReq) {
        Planning planning = PlanningConvertor.setTodo(userId,postPlanningReq);
        planningRepository.save(planning);
    }

    public boolean existsPlanning(Long planningId) {
        return planningRepository.existsById(planningId);
    }

    public int getPlanningYearSuccess(Long planningYearId) {
        Optional<PlanningYear> planningYear=planningYearRepository.findById(planningYearId);
        return planningYear.get().getSuccess();
    }

    public int getPlanningSuccess(Long planningId) {
        Optional<Planning> planning=planningRepository.findById(planningId);
        return planning.get().getSuccess();
    }



    public void checkSuccessByYear(Long planningYearId) {
        Optional<PlanningYear> planningYear =planningYearRepository.findById(planningYearId);
        planningYear.get().updateGoalOfYearSuccess(1);
        planningYearRepository.save(planningYear.get());

    }

    public void unCheckSuccessByYear(Long planningYearId) {
        Optional<PlanningYear> planningYear =planningYearRepository.findById(planningYearId);
        planningYear.get().updateGoalOfYearSuccess(0);
        planningYearRepository.save(planningYear.get());

    }

    public void checkSuccess(Long planningId) {
        Optional<Planning> planning =planningRepository.findById(planningId);
        planning.get().updateSuccess(1);
        planningRepository.save(planning.get());

    }

    public void unCheckSuccess(Long planningId) {
        Optional<Planning> planning =planningRepository.findById(planningId);
        planning.get().updateSuccess(0);
        planningRepository.save(planning.get());
    }

    public void deletePlanning(Long planningId) {
        planningRepository.deleteById(planningId);
    }

    public List<PlanningRes.GetPlanning> getTodoList(String date, Long userId) {
        PlanningRes.TimeRes timeRes=TimeConvertor.getToday(date);

        List<Planning> planning=planningRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId,timeRes.getStartTime(),timeRes.getFinishTime());

        if(planning.isEmpty())
            return null;

        return PlanningConvertor.getTodoList(planning);
    }

    public List<PlanningRes.GetPlanning> getTodoListThisWeek(String date, Long userId) {

        PlanningRes.TimeRes timeRes=TimeConvertor.getThisWeek(date);


        List<PlanningWeek> planningWeek=planningWeekRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId,timeRes.getStartTime(),timeRes.getFinishTime());

        if(planningWeek.isEmpty())
            return null;

        return PlanningConvertor.getTodoListWeek(planningWeek);
    }

    public List<PlanningRes.GetPlanning> getTodoListThisMonth(String date, Long userId) {
        PlanningRes.TimeRes timeRes=TimeConvertor.getThisMonth(date);


        List<PlanningMonth> planningMonth=planningMonthRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId,timeRes.getStartTime(),timeRes.getFinishTime());

        if(planningMonth.isEmpty())
            return null;

        return PlanningConvertor.getTodoListMonth(planningMonth);
    }


    public void patchPlan(Long userId, PlanningReq.PostPlanningReq postPlanningReq, Long planningId) {
        Optional<Planning> planning =planningRepository.findById(planningId);

        planning.get().updateInfo(postPlanningReq);

        planningRepository.save(planning.get());
    }

    public void setTodoWeek(Long userId, PlanningReq.PostPlanningReq postPlanningReq) {
        PlanningWeek planning = PlanningConvertor.setTodoWeek(userId,postPlanningReq);
        planningWeekRepository.save(planning);
    }

    public void setTodoMonth(Long userId, PlanningReq.PostPlanningReq postPlanningReq) {
        PlanningMonth planning = PlanningConvertor.setTodoMonth(userId,postPlanningReq);
        planningMonthRepository.save(planning);
    }

    public boolean existsPlanningWeek(Long planningId) {
        return planningWeekRepository.existsById(planningId);
    }
    public boolean existsPlanningMonth(Long planningId) {
        return planningMonthRepository.existsById(planningId);
    }

    public int getPlanningWeekSuccess(Long planningId) {
        Optional<PlanningWeek> planning=planningWeekRepository.findById(planningId);
        return planning.get().getSuccess();
    }

    public int getPlanningMonthSuccess(Long planningId) {
        Optional<PlanningMonth> planning=planningMonthRepository.findById(planningId);
        return planning.get().getSuccess();
    }

    public void checkWeekSuccess(Long planningId) {
        Optional<PlanningWeek> planning=planningWeekRepository.findById(planningId);
        planning.get().updateSuccess(1);
        planningWeekRepository.save(planning.get());
    }

    public void unCheckWeekSuccess(Long planningId) {
        Optional<PlanningWeek> planning=planningWeekRepository.findById(planningId);
        planning.get().updateSuccess(1);
        planningWeekRepository.save(planning.get());
    }

    public void checkMonthSuccess(Long planningId) {
        Optional<PlanningMonth> planning=planningMonthRepository.findById(planningId);
        planning.get().updateSuccess(1);
        planningMonthRepository.save(planning.get());
    }

    public void unCheckMonthSuccess(Long planningId) {
        Optional<PlanningMonth> planning=planningMonthRepository.findById(planningId);
        planning.get().updateSuccess(1);
        planningMonthRepository.save(planning.get());
    }

    public void deletePlanningWeek(Long planningId) {
        planningWeekRepository.deleteById(planningId);
    }

    public void deletePlanningMonth(Long planningId) {
        planningMonthRepository.deleteById(planningId);
    }

    public void patchPlanWeek(Long userId, PlanningReq.PostPlanningInfoReq postPlanningReq, Long planningId) {
        Optional<PlanningWeek> planning =planningWeekRepository.findById(planningId);

        planning.get().updateInfo(postPlanningReq);

        planningWeekRepository.save(planning.get());
    }

    public void patchPlanMonth(Long userId, PlanningReq.PostPlanningInfoReq postPlanningReq, Long planningId) {
        Optional<PlanningMonth> planning =planningMonthRepository.findById(planningId);

        planning.get().updateInfo(postPlanningReq);

        planningMonthRepository.save(planning.get());
    }
}
