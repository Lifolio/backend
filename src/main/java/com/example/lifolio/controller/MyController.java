package com.example.lifolio.controller;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/my")
public class MyController {
    private final TokenProvider jwtProvider;
    private final MyService myService;

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
}
