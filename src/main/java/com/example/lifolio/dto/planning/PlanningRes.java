package com.example.lifolio.dto.planning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class PlanningRes {
    @AllArgsConstructor
    @Setter
    @Getter

    public static class GetGoalOfYearRes{
        private int success;
        private String title;

    }

    @AllArgsConstructor

    @Setter
    @Getter
    @Builder
    public static class GetPlanning{
        private Long planningId;
        private String title;
        private int success;
        private LocalDateTime time;
    }

    @AllArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class TimeRes{
        private LocalDateTime startTime;
        private LocalDateTime finishTime;
    }
}
