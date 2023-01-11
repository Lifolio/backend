package com.example.lifolio.converter;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.Alarm;
import com.example.lifolio.entity.Authority;
import com.example.lifolio.entity.User;

import java.util.Collections;

public class UserConverter {

    public static User postUser(String id, String social, String name) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        return User.builder().
                username(id).
                name(name).
                nickname(id).
                social(social).
                authorities(Collections.singleton(authority))
                .activated(true).
                build();

    }

    public static Alarm postAlarm(Long userId){
        return Alarm.builder()
                .userId(userId)
                .build();
    }

    public static UserRes.AlarmList GetAlarmList(Alarm alarm, int allAlarm, int socialAlarm, int myAlarm, int planningAlarm) {
        return UserRes.AlarmList.builder()
                .AllAlarm(allAlarm)
                .myAlarm(myAlarm)
                .weekAlarm(alarm.getWeekAlarm())
                .badgeAlarm(alarm.getBadgeAlarm())
                .planningAlarm(planningAlarm)
                .todoAlarm(alarm.getTodoAlarm())
                .goalAlarm(alarm.getGoalAlarm())
                .socialAlarm(socialAlarm)
                .uploadAlarm(alarm.getUploadAlarm())
                .interestAlarm(alarm.getInterestAlarm())
                .likeAlarm(alarm.getLikeAlarm())
                .marketingAlarm(alarm.getMarketingAlarm())
                .build();
    }
}
