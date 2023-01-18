package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.alarm.RequestDTO;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TokenProvider jwtProvider;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @GetMapping("")
    public BaseResponse<String> test() {
        try {
            Long userId = jwtProvider.getUserIdx();
            System.out.println("유저 아이디값:" + userId);
            return new BaseResponse<>("유저 아이디값:" + userId);
        }catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("test")
    public BaseResponse<String> testTest() throws BaseException {
        return new BaseResponse<>("테스트");
    }


    @GetMapping("/authUser")
    public BaseResponse<String>  getUserIdx(@AuthenticationPrincipal User user){
        String userId=user.getUsername();
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
