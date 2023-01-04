package com.example.lifolio.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class GetGoalRes {
    private int year;
    private String goal;
}
