package com.example.lifolio.dto.alarm;

import lombok.*;

import java.util.List;

public class AlarmRes {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PlanningRes{
        private int success;
    }


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PlanningUserList{
        private Long userId;
        private String nickname;
        private String fcmToken;

    }

}
