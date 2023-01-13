package com.example.lifolio.service;

import com.example.lifolio.converter.UserConverter;
import com.example.lifolio.dto.alarm.AlarmReq;
import com.example.lifolio.dto.home.HomeReq;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.Alarm;
import com.example.lifolio.entity.User;
import com.example.lifolio.repository.AlarmRepository;
import com.example.lifolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public UserRes.AlarmList getAlarmList(Long userId) {
        User user = userRepository.getOne(userId);

        Alarm alarm = alarmRepository.findByUserId(userId);

        int allAlarm=1;
        int myAlarm=1;
        int planningAlarm=1;
        int socialAlarm=1;

        //MyAlarm 설정
        if(alarm.getWeekAlarm()==0&&alarm.getBadgeAlarm()==0){
            myAlarm=0;
        }
        //PlanningAlarm
        if(alarm.getTodoAlarm()==0&&alarm.getGoalAlarm()==0){
            planningAlarm=0;
        }
        //SocialAlarm
        if(alarm.getUploadAlarm()==0&&alarm.getInterestAlarm()==0){
            socialAlarm=0;
        }
        if(socialAlarm==0&&myAlarm==0&&planningAlarm==0){
            allAlarm=0;
        }

        return UserConverter.GetAlarmList(alarm,allAlarm,socialAlarm,myAlarm,planningAlarm);

    }

    @SneakyThrows
    public void setAllAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1) {
            alarm.updateAllAlarm(1,1,1,1,1,1,1,1);
        }
        else{
            alarm.updateAllAlarm(0,0,0,0,0,0,0,0);
        }
        alarmRepository.save(alarm);
    }

    public void setMyAllAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateMyAllAlarm(1,1);
        }
        else{
            alarm.updateMyAllAlarm(0,0);
        }
        alarmRepository.save(alarm);
    }

    public void setWeekAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateWeekAlarm(1);
        }
        else{
            alarm.updateWeekAlarm(0);
        }
        alarmRepository.save(alarm);
    }

    public void setBadgeAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateBadgeAlarm(1);
        }
        else{
            alarm.updateBadgeAlarm(0);
        }
        alarmRepository.save(alarm);
    }

    public void setPlanningAllAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updatePlanningAllAlarm(1,1);
        }
        else{
            alarm.updatePlanningAllAlarm(0,0);
        }
        alarmRepository.save(alarm);
    }

    public void setTodoAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateTodoAlarm(1);
        }
        else{
            alarm.updateTodoAlarm(0);
        }
        alarmRepository.save(alarm);
    }

    public void setGoalAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateGoalAlarm(1);
        }
        else{
            alarm.updateGoalAlarm(0);
        }
        alarmRepository.save(alarm);
    }

    public void setSocialAllAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateSocialAllAlarm(1,1);
        }
        else{
            alarm.updateSocialAllAlarm(0,0);
        }
        alarmRepository.save(alarm);
    }

    public void setUploadAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateUploadAlarm(1);
        }
        else{
            alarm.updateUploadAlarm(0);
        }
        alarmRepository.save(alarm);
    }

    public void setInterestAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateInterestAlarm(1);
        }
        else{
            alarm.updateInterestAlarm(0);
        }
        alarmRepository.save(alarm);
    }

    public void setLikeAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateLikeAlarm(1);
        }
        else{
            alarm.updateLikeAlarm(0);
        }
        alarmRepository.save(alarm);
    }

    public void setMarketingAlarm(Long userId, AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq) {
        Alarm alarm = alarmRepository.findByUserId(userId);
        if(allAlarmUpdateReq.getAlarm() == 1){
            alarm.updateMarketingAlarm(1);
        }
        else{
            alarm.updateMarketingAlarm(0);
        }
        alarmRepository.save(alarm);
    }
}
