package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TokenProvider jwtProvider;

    @GetMapping("")
    public BaseResponse<String> test() throws BaseException {
        Long userId=jwtProvider.getUserIdx();
        return new BaseResponse<>("유저 아이디값:"+userId);
    }

    @GetMapping("test")
    public BaseResponse<String> testTest() throws BaseException {
        return new BaseResponse<>("테스트");
    }

    /*
    @GetMapping("/jwt")
    public String getJwt(){

    }

     */
}
