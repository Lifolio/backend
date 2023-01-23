package com.example.lifolio.converter;

import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.entity.Planning;

import java.util.ArrayList;
import java.util.List;

public class PlanningConvertor {
    public static Planning setTodo(Long userId, PlanningReq.PostPlanningReq postPlanningReq) {
        return Planning.builder().
                userId(userId)
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
}
