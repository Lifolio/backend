package com.example.lifolio.dto.planOfYear;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

public class PlanOfYearReq {

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PostPlanOfYearReq {
        private String title;
        private String planYear;
        private String planMonth;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class UpdatePlanOfYearReq{
        private String title;
        private String planYear;
        private String planMonth;
    }
}
