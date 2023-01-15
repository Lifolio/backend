package com.example.lifolio.dto.planning;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PlanningReq {

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

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PostPlanningReq {
        private String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime finishTime;
    }
}
