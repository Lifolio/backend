package com.example.lifolio.converter;

import com.example.lifolio.dto.alarm.AlarmRes;
import com.example.lifolio.dto.alarm.RequestDTO;

import java.util.Collections;
import java.util.List;

public class AlarmConvertor {
    public static RequestDTO RequestDto(AlarmRes.PlanningUserList planningUser, List<Integer> todoPlanning) {
        int success= Collections.frequency(todoPlanning,1);
        return RequestDTO.builder().
                title(planningUser.getNickname()+"님 오늘의 TODO⏰ 상태!").
                body("오늘의 계획 "+todoPlanning.size()+"개 중 "+success+"개 완료했어요!").
                targetToken(planningUser.getFcmToken()).build();
    }
}
