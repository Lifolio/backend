package com.example.lifolio.converter;

import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.entity.Planning;
import com.example.lifolio.entity.PlanningMonth;
import com.example.lifolio.entity.PlanningWeek;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanningConvertor {
    public static Planning setTodo(Long userId, PlanningReq.PostPlanningReq postPlanningReq) {
        return Planning.builder().
                userId(userId)
                .date(LocalDateTime.parse(postPlanningReq.getDate()+"T13:00:00"))
                .title(postPlanningReq.getTitle())
                .build();
    }

    public static PlanningWeek setTodoWeek(Long userId, PlanningReq.PostPlanningReq postPlanningReq) {
        return PlanningWeek.builder().
                userId(userId)
                .date(LocalDateTime.parse(postPlanningReq.getDate()+"T13:00:00"))
                .title(postPlanningReq.getTitle())
                .build();
    }

    public static PlanningMonth setTodoMonth(Long userId, PlanningReq.PostPlanningReq postPlanningReq) {
        return PlanningMonth.builder().
                userId(userId)
                .date(LocalDateTime.parse(postPlanningReq.getDate()+"T13:00:00"))
                .title(postPlanningReq.getTitle())
                .build();
    }

    public static List<PlanningRes.GetPlanning> getTodoList(List<Planning> planning) {
        List<PlanningRes.GetPlanning> todoList=new ArrayList<>();
        for (Planning result : planning) {
            PlanningRes.GetPlanning todo = new PlanningRes.GetPlanning(result.getId(),result.getTitle(),result.getSuccess(),result.getDate());
            todoList.add(todo);
        }
        return todoList;
    }

    public static List<PlanningRes.GetPlanning> getTodoListWeek(List<PlanningWeek> planningWeek) {
        List<PlanningRes.GetPlanning> todoList=new ArrayList<>();
        for (PlanningWeek result : planningWeek) {
            PlanningRes.GetPlanning todo = new PlanningRes.GetPlanning(result.getId(),result.getTitle(),result.getSuccess(),result.getDate());
            todoList.add(todo);
        }
        return todoList;
    }

    public static List<PlanningRes.GetPlanning> getTodoListMonth(List<PlanningMonth> planningMonth) {
        List<PlanningRes.GetPlanning> todoList=new ArrayList<>();
        for (PlanningMonth result : planningMonth) {
            PlanningRes.GetPlanning todo = new PlanningRes.GetPlanning(result.getId(),result.getTitle(),result.getSuccess(),result.getDate());
            todoList.add(todo);
        }
        return todoList;
    }

}
