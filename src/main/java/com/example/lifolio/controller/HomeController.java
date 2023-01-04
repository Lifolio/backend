package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.home.*;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.HomeService;
import com.example.lifolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.lifolio.base.BaseResponseStatus.INVALID_USER_JWT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final HomeService homeService;
    private final TokenProvider jwtProvider;

    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<GetHomeRes> home(@PathVariable("userId") Long userId){
        try {
            Long idByJwt= jwtProvider.getUserIdx();
            if(userId!=idByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetHomeRes getHomeRes = homeService.getHomeRes(userId);
            return new BaseResponse<>(getHomeRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/graph/{userId}/{customId}")
    public BaseResponse<List<GetGraphRes>> getGraphCustomFolio(@PathVariable("userId") Long userId, @PathVariable("customId")Long customId){
        try {
            Long idByJwt= jwtProvider.getUserIdx();
            if(userId!=idByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetGraphRes> graphInfo = homeService.getGraphLifolio(userId,customId);
            return new BaseResponse<>(graphInfo);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/custom/{userId}/{customId}")
    public BaseResponse<List<GetCustomRes>> getCustomFolio(@PathVariable("userId") Long userId, @PathVariable("customId")Long customId){
        try {
            Long idByJwt= jwtProvider.getUserIdx();
            if(userId!=idByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetCustomRes> customInfo = homeService.getCustomLifolio(userId,customId);
            return new BaseResponse<>(customInfo);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }




}
