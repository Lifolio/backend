package com.example.lifolio.converter;

import com.example.lifolio.dto.alarm.AlarmRes;
import com.example.lifolio.dto.alarm.RequestDTO;

import java.util.Collections;
import java.util.List;

public class AlarmConvertor {
    public static RequestDTO RequestDto(AlarmRes.PlanningUserList planningUser, List<Integer> todoPlanning) {
        int success= Collections.frequency(todoPlanning,1);
        int total=todoPlanning.size();
        if(success==total){
            return RequestDTO.builder().
                    title("🌈"+planningUser.getNickname()+"님 오늘의 TODO⏰ 현황!🌈").
                    body("오늘의 계획 "+todoPlanning.size()+"개 모두 완료 했어요 고생 했어요🎉").
                    targetToken(planningUser.getFcmToken()).build();
        }
        else {
            return RequestDTO.builder().
                    title("🌈"+planningUser.getNickname() + "님 오늘의 TODO⏰ 현황!🌈").
                    body("오늘의 계획 " + todoPlanning.size() + "개 중 " + success + "개 완료 했어요!").
                    targetToken(planningUser.getFcmToken()).build();
        }
    }

    public static RequestDTO AlarmWeek(AlarmRes.PlanningUserList planningUser, List<Integer> todoPlanning) {
        int success= Collections.frequency(todoPlanning,1);
        int total=todoPlanning.size();
        if(success==total){
            return RequestDTO.builder().
                    title("🌈"+planningUser.getNickname()+"님 이번주 TODO⏰ 현황!🌈").
                    body("이번주 계획 "+todoPlanning.size()+"개 모두 완료 했어요 고생 했어요🎉").
                    targetToken(planningUser.getFcmToken()).build();
        }
        else {
            return RequestDTO.builder().
                    title("🌈"+planningUser.getNickname() + "님 이번주 TODO⏰ 현황!🌈").
                    body("이번주 계획 " + todoPlanning.size() + "개 중 " + success + "개 완료 했어요!").
                    targetToken(planningUser.getFcmToken()).build();
        }
    }

    public static RequestDTO AlarmMonth(AlarmRes.PlanningUserList planningUser, List<Integer> todoPlanning) {
        int success= Collections.frequency(todoPlanning,1);
        int total=todoPlanning.size();
        if(success==total){
            return RequestDTO.builder().
                    title("🌈"+planningUser.getNickname()+"님 이번달 TODO⏰ 현황!🌈").
                    body("이번달 계획 "+todoPlanning.size()+"개 모두 완료 했어요 고생 했어요🎉").
                    targetToken(planningUser.getFcmToken()).build();
        }
        else {
            return RequestDTO.builder().
                    title("🌈"+planningUser.getNickname() + "님 이번달 TODO⏰ 현황!🌈").
                    body("이번달 계획 " + todoPlanning.size() + "개 중 " + success + "개 완료 했어요!").
                    targetToken(planningUser.getFcmToken()).build();
        }
    }
}
