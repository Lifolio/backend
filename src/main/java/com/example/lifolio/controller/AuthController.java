package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.user.UserReq;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/kakao")
    public BaseResponse<String> getAccessTokenKakao(@RequestParam String code) throws BaseException {
        String accessToken=authService.getKakaoAccessToken(code);
        System.out.println(accessToken);
        return new BaseResponse<>(accessToken);
    }

    @ResponseBody
    @PostMapping("/kakao/logIn")
    public BaseResponse<UserRes.TokenRes> kakaoLogin(@RequestBody UserReq.SocialReq socialReq) throws BaseException {
        try {
            UserRes.TokenRes tokenRes = authService.logInKakaoUser(socialReq);
            return new BaseResponse<>(tokenRes);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/naver")
    public BaseResponse<String> getAccessTokenNaver(@RequestParam String code) throws BaseException {
        String accessToken=authService.getNaverAccessToken(code);
        System.out.println(accessToken);
        return new BaseResponse<>(accessToken);
    }

    @ResponseBody
    @PostMapping("/naver/logIn")
    public BaseResponse<UserRes.TokenRes> naverCallback(@RequestBody UserReq.SocialReq socialReq) throws BaseException {
        try {
            UserRes.TokenRes tokenRes = authService.logInNaverUser(socialReq);
            return new BaseResponse<>(tokenRes);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
