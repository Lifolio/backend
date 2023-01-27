package com.example.lifolio.controller;


import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.alarm.AlarmReq;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public BaseResponse<UserRes.AlarmList> getAlarmList(@AuthenticationPrincipal User user){
            Long userId=user.getId();
            UserRes.AlarmList alarmList = alarmService.getAlarmList(userId);
            return new BaseResponse<>(alarmList);
    }

    @ResponseBody
    @PatchMapping("/allAlarm")
    public BaseResponse<String> updateAllAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            System.out.println(userId);
            alarmService.setAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/myAllAlarm")
    public BaseResponse<String> updateMyAllAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setMyAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/weekAlarm")
    public BaseResponse<String> updateWeekAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setWeekAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/badgeAlarm")
    public BaseResponse<String> updateBadgeAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setBadgeAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/plannigAllAlarm")
    public BaseResponse<String> updatePlanningAllAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setPlanningAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/todoAlarm")
    public BaseResponse<String> updateTodoAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setTodoAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/goalAlarm")
    public BaseResponse<String> updateGoalAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setGoalAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/socialAllAlarm")
    public BaseResponse<String> updateSocialAllAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setSocialAllAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/uploadAlarm")
    public BaseResponse<String> updateUploadAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setUploadAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/interestAlarm")
    public BaseResponse<String> updateInterestAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setInterestAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/likeAlarm")
    public BaseResponse<String> updateLikeAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setLikeAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }
    @ResponseBody
    @PatchMapping("/marketingAlarm")
    public BaseResponse<String> updateMarketingAlarm(@AuthenticationPrincipal User user,@RequestBody AlarmReq.AllAlarmUpdateReq allAlarmUpdateReq){
            Long userId=user.getId();
            alarmService.setMarketingAlarm(userId,allAlarmUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }
}
