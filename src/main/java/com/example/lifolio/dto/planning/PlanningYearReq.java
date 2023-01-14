package com.example.lifolio.dto.planning;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

public class PlanningYearReq {
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PostGoalOfYearReq {
        private String title;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate date;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class UpdateGoalOfYearReq{
        private String title;
        private LocalDate date;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class UpdateGoalOfYearSuccessReq{
        private int success;
    }
}
