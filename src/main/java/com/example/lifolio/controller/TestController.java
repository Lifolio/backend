package com.example.lifolio.controller;

import com.example.lifolio.base.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public BaseResponse<String> test() {
        return new BaseResponse<>("test");
    }
}
