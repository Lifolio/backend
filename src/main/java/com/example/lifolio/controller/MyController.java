package com.example.lifolio.controller;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.MyService;
import com.example.lifolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/my")
public class MyController {
    private final TokenProvider jwtProvider;
    private final MyService myService;
    private final UserService userService;

    @ResponseBody
    @GetMapping("")
    public BaseResponse<UserRes.GetMyRes> getMyLifolio(){
        try {
            Long userId=jwtProvider.getUserIdx();
            UserRes.GetMyRes getMyRes=myService.getMyLifolio(userId);
            return new BaseResponse<>(getMyRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @ResponseBody
    @GetMapping("/calender")
    public BaseResponse<List<UserRes.Calender>> getMyLifolioCalender(@RequestParam("date") String date){
        try{
            Long userId=jwtProvider.getUserIdx();
            List<UserRes.Calender> calender=myService.getCalender(userId,date);
            return new BaseResponse<>(calender);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }



    @GetMapping("/profile/simple") //#2-1 4번은 /profile/detail
    public BaseResponse<UserRes.Profile> getMyProfile(){
        Long userId= userService.findNowLoginUser().getId();
        UserRes.Profile profile = myService.getMyProfileSimple(userId);
        return new BaseResponse<>(profile);
    }



}
