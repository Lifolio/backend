package com.example.lifolio.dto.home;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class GetGoalRes {
    private int year;
    private String goal;
    private LocalDate createDate;
}
