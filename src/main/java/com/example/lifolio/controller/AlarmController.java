package com.example.lifolio.controller;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


}
