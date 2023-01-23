package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.user.*;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.RedisService;
import com.example.lifolio.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static com.example.lifolio.base.BaseResponseStatus.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider jwtProvider;
    private final RedisService redisService;

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public BaseResponse<UserRes.TokenRes> login(@Valid @RequestBody UserReq.LoginUserReq loginUserReq){
        if(loginUserReq.getUsername()==null){
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }
        if(loginUserReq.getPassword()==null){
            return new BaseResponse<>(USERS_EMPTY_USER_PASSWORD);
        }

        try {
            UserRes.TokenRes tokenRes = userService.login(loginUserReq);
            return new BaseResponse<>(tokenRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/signup")
    public BaseResponse<UserRes.TokenRes> signup(@Valid @RequestBody UserReq.SignupUserReq signupUserReq) throws BaseException {
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
    public BaseResponse<UserRes.PasswordRes> setNewPassword(UserReq.PasswordReq passwordReq){
        //이름, 아이디
        UserRes.PasswordRes passwordRes = userService.setNewPassword(passwordReq);
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
    public BaseResponse<UserRes.GetSMSRes> sendSMS(@RequestParam(value="to")String to) throws CoolsmsException {
        int result = userService.phoneNumberCheck(to);
        UserRes.GetSMSRes getSMSRes = new UserRes.GetSMSRes(result);
        return new BaseResponse<>(getSMSRes);
    }

    @ResponseBody
    @PostMapping("/find")
    public BaseResponse<UserRes.FindUserIdRes> findUserId(@RequestBody UserReq.FindUserIdReq findUserIdReq) {
        try {
            UserRes.FindUserIdRes findUserIdRes = new UserRes.FindUserIdRes(userService.findUserId(findUserIdReq));
            return new BaseResponse<>(findUserIdRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/re_token")
    public BaseResponse<UserRes.TokenRes> reIssueToken(@RequestBody UserReq.PostReIssueReq postReIssueReq){

        String redisRT= redisService.getValues(String.valueOf(postReIssueReq.getUserId()));

        if(redisRT==null){
            return new BaseResponse<>(INVALID_REFRESH_TOKEN);

        }
        if(!redisRT.equals(postReIssueReq.getRefreshToken())){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        UserRes.TokenRes tokenRes=userService.reIssueToken(postReIssueReq.getUserId());

        return new BaseResponse<>(tokenRes);

    }


    // 토큰이 유효하다는 가정 하
    // 만약 토큰이 만료되었다면 재발급 요청
    @ResponseBody
    @GetMapping("/logOut/{userId}")
    public BaseResponse<String> logOut(@PathVariable("userId") Long userId){
        try {

            //탈취된 토큰인지 검증
            Long userIdByJwt = jwtProvider.getUserIdx();

            //userId와 접근한 유저가 같은지 확인
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            //헤더에서 토큰 가져오기
            String accessToken = jwtProvider.getJwt();
            //jwt 에서 로그아웃 처리 & 오류처리 &
            jwtProvider.logOut(userId,accessToken);
            String result="로그아웃 성공";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }

    }


    @ResponseBody
    @GetMapping("/fcm")
    public BaseResponse<String> updateFcmToken(@RequestParam("token") String token){
        try {

            //탈취된 토큰인지 검증
            Long userId = jwtProvider.getUserIdx();

            userService.updateFcmToken(userId,token);
            //헤더에서 토큰 가져오기
            //jwt 에서 로그아웃 처리 & 오류처리 &
            String result="fcm 토큰 저장 성공";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }

    }




}