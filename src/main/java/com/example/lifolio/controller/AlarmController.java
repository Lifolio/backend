package com.example.lifolio.controller;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.alarm.AlarmReq;
import com.example.lifolio.dto.home.HomeReq;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.lifolio.base.BaseResponseStatus.INVALID_USER_JWT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final TokenProvider tokenProvider;

    @ResponseBody
    @GetMapping("")
    public BaseResponse<UserRes.AlarmList> getAlarmList(){
        try {
            Long userId=tokenProvider.getUserIdx();
            UserRes.AlarmList alarmList = alarmService.getAlarmList(userId);
            return new BaseResponse<>(alarmList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/allAlarm/{userId}")
    public BaseResponse<String> updateAllAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/myAllAlarm/{userId}")
    public BaseResponse<String> updateMyAllAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setMyAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/weekAlarm/{userId}")
    public BaseResponse<String> updateWeekAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setWeekAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/badgeAlarm/{userId}")
    public BaseResponse<String> updateBadgeAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setBadgeAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/plannigAllAlarm/{userId}")
    public BaseResponse<String> updatePlanningAllAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setPlanningAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/todoAlarm/{userId}")
    public BaseResponse<String> updateTodoAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setTodoAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/goalAlarm/{userId}")
    public BaseResponse<String> updateGoalAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setGoalAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/socialAllAlarm/{userId}")
    public BaseResponse<String> updateSocialAllAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setSocialAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/uploadAlarm/{userId}")
    public BaseResponse<String> updateUploadAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setUploadAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/interestAlarm/{userId}")
    public BaseResponse<String> updateInterestAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setInterestAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/likeAlarm/{userId}")
    public BaseResponse<String> updateLikeAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setLikeAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @ResponseBody
    @PatchMapping("/marketingAlarm/{userId}")
    public BaseResponse<String> updateMarketingAlarm(@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            alarmService.setMarketingAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
