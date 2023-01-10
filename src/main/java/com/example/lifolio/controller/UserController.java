package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.user.*;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.util.List;

import static com.example.lifolio.base.BaseResponseStatus.*;
import static com.example.lifolio.base.BaseResponseStatus.EMPTY_ACCESS_TOKEN;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider jwtProvider;

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public BaseResponse<TokenRes> login(@Valid @RequestBody LoginUserReq loginUserReq){
        if(loginUserReq.getUsername()==null){
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }
        if(loginUserReq.getPassword()==null){
            return new BaseResponse<>(USERS_EMPTY_USER_PASSWORD);
        }

        try {
            TokenRes tokenRes = userService.login(loginUserReq);
            return new BaseResponse<>(tokenRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
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
            return new BaseResponse<>(NOT_EXIST_USER);
        }
    }


//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public BaseResponse<SignupUserReq> getMyUserInfo() {
//        return new BaseResponse<>(userService.getMyUserWithAuthorities());
//    }
//
//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public BaseResponse<SignupUserReq> getUserInfo(@PathVariable String username) {
//        return new BaseResponse<>(userService.getUserWithAuthorities(username));
//    }


    @GetMapping("/check/nickname")
    public BaseResponse<String> checkNickName(@Param("nickname") String nickName) {
        String result="";
        System.out.println(userService.checkNickName(nickName));
        System.out.println(nickName);
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
        System.out.println(userService.checkUserId(userId));
        System.out.println(userId);
        if(userService.checkUserId(userId)){
            return new BaseResponse<>(USERS_EXISTS_ID);
        }
        else{
            result="사용 가능합니다.";
        }
        return new BaseResponse<>(result);

    }



    @ResponseBody
    @GetMapping("/check/sendSMS")
    public BaseResponse<GetSMSRes> sendSMS(@RequestParam(value="to")String to) throws CoolsmsException {
        int result = userService.phoneNumberCheck(to);
        GetSMSRes getSMSRes = new GetSMSRes(result);
        return new BaseResponse<>(getSMSRes);
    }

    @ResponseBody
    @PostMapping("/find")
    public BaseResponse<FindUserIdRes> findUserId(@RequestBody FindUserIdReq findUserIdReq) {
        try {
            FindUserIdRes findUserIdRes = new FindUserIdRes(userService.findUserId(findUserIdReq));
            return new BaseResponse<>(findUserIdRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }





}