package com.example.lifolio.dto.alarm;

import lombok.*;

public class AlarmReq {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class AllAlarmUpdateReq {
        private int alarm;
    }


}
