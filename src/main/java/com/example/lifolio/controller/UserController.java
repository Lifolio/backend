package com.example.lifolio.controller;

import antlr.Token;
import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.base.BaseResponseStatus;
import com.example.lifolio.dto.*;
import com.example.lifolio.jwt.JwtFilter;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

import static com.example.lifolio.base.BaseResponseStatus.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider jwtProvider;

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public BaseResponse<TokenRes> login(@Valid @RequestBody LoginUserReq loginUserReq){
        TokenRes tokenRes = userService.login(loginUserReq);
        return new BaseResponse<>(tokenRes);
    }


    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/signup")
    public BaseResponse<TokenRes> signup(@Valid @RequestBody SignupUserReq signupUserReq) throws BaseException {
        try {
            if (userService.checkUserId(signupUserReq.getUsername())) {
                return new BaseResponse<>(USERS_EXISTS_ID);
            }
            if (userService.checkNickName(signupUserReq.getNickname())) {
                return new BaseResponse<>(USERS_EXISTS_NICKNAME);
            }
            return new BaseResponse<>(userService.signup(signupUserReq));
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "새로운 비밀번호 설정", notes = "새로운 비밀번호 설정")
    @PatchMapping("/password")
    public BaseResponse<PasswordRes> setNewPassword(PasswordReq passwordReq){
        //이름, 아이디
        PasswordRes passwordRes = userService.setNewPassword(passwordReq);
        if(passwordRes != null){
            return new BaseResponse<>(passwordRes);
        } else {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
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


    @GetMapping("/check/nickname")
    public BaseResponse<String> checkNickName(@Param("nickname") String nickName) {
        String result="";
        if(userService.checkNickName(nickName)){
            return new BaseResponse<>(USERS_EXISTS_NICKNAME);
        }
        else{
            result="사용 가능합니다.";
        }
        return new BaseResponse<>(result);

    }

    @GetMapping("/check/userId")
    public BaseResponse<String> checkUserId(@Param("userId") String userId){
        String result="";
        if(userService.checkUserId(userId)){
            return new BaseResponse<>(USERS_EXISTS_ID);
        }
        else{
            result="사용 가능합니다.";
        }
        return new BaseResponse<>(result);

    }


}