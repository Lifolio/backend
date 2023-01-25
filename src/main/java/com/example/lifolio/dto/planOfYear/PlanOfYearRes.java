package com.example.lifolio.dto.planOfYear;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PlanOfYearRes {

    @AllArgsConstructor
    @Setter
    @Getter
    public static class GetPlanOfYearRes{
        private String title;
        private String planYear;
        private String planMonth;

    }
}
