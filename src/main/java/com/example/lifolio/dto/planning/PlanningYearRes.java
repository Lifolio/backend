package com.example.lifolio.dto.planning;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PlanningYearRes {
    @AllArgsConstructor
    @Setter
    @Getter
    public static class GetGoalOfYearRes{
        private int success;
        private String title;

    }
}
