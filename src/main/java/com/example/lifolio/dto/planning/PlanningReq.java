package com.example.lifolio.dto.planning;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
}
