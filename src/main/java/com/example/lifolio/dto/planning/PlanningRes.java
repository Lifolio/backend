package com.example.lifolio.dto.planning;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class PlanningRes {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostGoalOfYearRes {
        private String title;
        private Date date;
        
    }
}
