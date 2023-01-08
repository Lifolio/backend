package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.user.TokenRes;
import com.example.lifolio.dto.user.UserReq;
import com.example.lifolio.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/kakao")
    public BaseResponse<String> getAccessToken(@RequestParam String code) throws BaseException {
        String accessToken=authService.getKakaoAccessToken(code);
        System.out.println(accessToken);
        return new BaseResponse<>(accessToken);
    }

    @ResponseBody
    @PostMapping("/kakao/logIn")
    public BaseResponse<TokenRes> kakaoCallback(@RequestBody UserReq.SocialReq socialReq) throws BaseException {
        try {
            TokenRes tokenRes = authService.logInKakaoUser(socialReq);
            return new BaseResponse<>(tokenRes);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
