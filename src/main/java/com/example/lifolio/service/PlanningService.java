package com.example.lifolio.service;

import com.example.lifolio.converter.PlanningConvertor;
import com.example.lifolio.converter.TimeConvertor;
import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.entity.Planning;
import com.example.lifolio.entity.PlanningYear;
import com.example.lifolio.repository.PlanningRepository;
import com.example.lifolio.repository.PlanningYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlanningService {

    private final PlanningYearRepository planningYearRepository;
    private final PlanningRepository planningRepository;

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

    public int getPlanningSuccess(Long planningId) {
        Optional<Planning> planning=planningRepository.findById(planningId);
        return planning.get().getSuccess();
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

    public List<PlanningRes.GetPlanning> getTodoList(Long userId) {
        PlanningRes.TimeRes timeRes=TimeConvertor.getToday();

        List<Planning> planning=planningRepository.findTop3ByUserIdAndStartDateBetweenOrderByStartDateAsc(userId,timeRes.getStartTime(),timeRes.getFinishTime());

        if(planning.isEmpty())
            return null;

        return PlanningConvertor.getTodoList(planning);
    }

    public List<PlanningRes.GetPlanning> getTodoListThisWeek(Long userId) {

        PlanningRes.TimeRes timeRes=TimeConvertor.getThisWeek();


        List<Planning> planning=planningRepository.findTop3ByUserIdAndStartDateBetweenOrderByStartDateAsc(userId,timeRes.getStartTime(),timeRes.getFinishTime());

        if(planning.isEmpty())
            return null;

        return PlanningConvertor.getTodoList(planning);
    }

    public List<PlanningRes.GetPlanning> getTodoListThisMonth(Long userId) {
        PlanningRes.TimeRes timeRes=TimeConvertor.getThisMonth();


        List<Planning> planning=planningRepository.findTop3ByUserIdAndStartDateBetweenOrderByStartDateAsc(userId,timeRes.getStartTime(),timeRes.getFinishTime());

        if(planning.isEmpty())
            return null;

        return PlanningConvertor.getTodoList(planning);
    }


}
