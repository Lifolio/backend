package com.example.lifolio.controller;

import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.LoginUserReq;
import com.example.lifolio.dto.SignupUserReq;
import com.example.lifolio.dto.TokenRes;
import com.example.lifolio.jwt.JwtFilter;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;



@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/login")
    public BaseResponse<TokenRes> login(@Valid @RequestBody LoginUserReq loginUserReq){
        TokenRes tokenRes = userService.login(loginUserReq);
        return new BaseResponse<>(tokenRes);
    }

    @PostMapping("/signup")
    public BaseResponse<SignupUserReq> signup(@Valid @RequestBody SignupUserReq signupUserReq) {
        return new BaseResponse<>(userService.signup(signupUserReq));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public BaseResponse<SignupUserReq> getMyUserInfo() {
        return new BaseResponse<>(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse<SignupUserReq> getUserInfo(@PathVariable String username) {
        return new BaseResponse<>(userService.getUserWithAuthorities(username));
    }
}