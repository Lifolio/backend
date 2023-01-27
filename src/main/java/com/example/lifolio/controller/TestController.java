package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.alarm.RequestDTO;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.FirebaseCloudMessageService;
import com.example.lifolio.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TokenProvider tokenProvider;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final NotificationService notificationService;

    @GetMapping("/user")
    public BaseResponse<String> test(@AuthenticationPrincipal User user) {
            Long userId = user.getId();
            System.out.println("유저 아이디값:" + userId);
            return new BaseResponse<>("유저 아이디값:" + userId);

    }

    @GetMapping("")
    public BaseResponse<String> testTest() throws BaseException, IOException {
        notificationService.scheduledTodoWeekAlarm();
        notificationService.scheduledTodoAlarm();
        notificationService.scheduledTodoMonthAlarm();

        return new BaseResponse<>("테스트");
    }


    @GetMapping("/authUser")
    public BaseResponse<String>  getUserIdx(@AuthenticationPrincipal User user){
        Long userId=user.getId();
        String result="유저 아이디 값" + userId;
        return new BaseResponse<>(result);
    }

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException {
        System.out.println(requestDTO.getTargetToken() + " "
                +requestDTO.getTitle() + " " + requestDTO.getBody());

        firebaseCloudMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ResponseEntity.ok().build();
    }





    /*
    @GetMapping("/jwt")
    public String getJwt(){

    }

     */
}
