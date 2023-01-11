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




}
