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
                    title("π"+planningUser.getNickname()+"λ μ€λμ TODOβ° νν©!π").
                    body("μ€λμ κ³ν "+todoPlanning.size()+"κ° λͺ¨λ μλ£ νμ΄μ κ³ μ νμ΄μπ").
                    targetToken(planningUser.getFcmToken()).build();
        }
        else {
            return RequestDTO.builder().
                    title("π"+planningUser.getNickname() + "λ μ€λμ TODOβ° νν©!π").
                    body("μ€λμ κ³ν " + todoPlanning.size() + "κ° μ€ " + success + "κ° μλ£ νμ΄μ!").
                    targetToken(planningUser.getFcmToken()).build();
        }
    }

    public static RequestDTO AlarmWeek(AlarmRes.PlanningUserList planningUser, List<Integer> todoPlanning) {
        int success= Collections.frequency(todoPlanning,1);
        int total=todoPlanning.size();
        if(success==total){
            return RequestDTO.builder().
                    title("π"+planningUser.getNickname()+"λ μ΄λ²μ£Ό TODOβ° νν©!π").
                    body("μ΄λ²μ£Ό κ³ν "+todoPlanning.size()+"κ° λͺ¨λ μλ£ νμ΄μ κ³ μ νμ΄μπ").
                    targetToken(planningUser.getFcmToken()).build();
        }
        else {
            return RequestDTO.builder().
                    title("π"+planningUser.getNickname() + "λ μ΄λ²μ£Ό TODOβ° νν©!π").
                    body("μ΄λ²μ£Ό κ³ν " + todoPlanning.size() + "κ° μ€ " + success + "κ° μλ£ νμ΄μ!").
                    targetToken(planningUser.getFcmToken()).build();
        }
    }

    public static RequestDTO AlarmMonth(AlarmRes.PlanningUserList planningUser, List<Integer> todoPlanning) {
        int success= Collections.frequency(todoPlanning,1);
        int total=todoPlanning.size();
        if(success==total){
            return RequestDTO.builder().
                    title("π"+planningUser.getNickname()+"λ μ΄λ²λ¬ TODOβ° νν©!π").
                    body("μ΄λ²λ¬ κ³ν "+todoPlanning.size()+"κ° λͺ¨λ μλ£ νμ΄μ κ³ μ νμ΄μπ").
                    targetToken(planningUser.getFcmToken()).build();
        }
        else {
            return RequestDTO.builder().
                    title("π"+planningUser.getNickname() + "λ μ΄λ²λ¬ TODOβ° νν©!π").
                    body("μ΄λ²λ¬ κ³ν " + todoPlanning.size() + "κ° μ€ " + success + "κ° μλ£ νμ΄μ!").
                    targetToken(planningUser.getFcmToken()).build();
        }
    }
}
