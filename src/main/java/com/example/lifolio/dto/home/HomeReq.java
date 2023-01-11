package com.example.lifolio.dto.home;


import lombok.*;

public class HomeReq {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class CustomUpdateReq {
        private String emoji; //이모지
        private String customName; //커스텀폴리오 이름
        private Long category; //카테고리
        private int concept; //뷰 컨셉
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PostGoalReq {
        //아이디는 securityContext에서 불러옴
        private String goal;
    }
}
