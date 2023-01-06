package com.example.lifolio.dto.home;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostGoalReq {
    //아이디는 securityContext에서 불러옴
    private String goal;
}
