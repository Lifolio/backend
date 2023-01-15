package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TokenProvider jwtProvider;

    @GetMapping("")
    public BaseResponse<String> test() {
        try {
            Long userId = jwtProvider.getUserIdx();
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



    /*
    @GetMapping("/jwt")
    public String getJwt(){

    }

     */
}
