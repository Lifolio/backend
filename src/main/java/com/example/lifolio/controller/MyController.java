package com.example.lifolio.controller;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.home.HomeRes;
import com.example.lifolio.dto.my.MyReq;
import com.example.lifolio.dto.my.MyRes;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.MyFolio;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.repository.MyFolioRepository;
import com.example.lifolio.service.MyService;
import com.example.lifolio.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.example.lifolio.base.BaseResponseStatus.EMPTY_MYFOLIO_AT_THAT_DATE;
import static com.example.lifolio.base.BaseResponseStatus.NOT_EXIST_MYFOLIO_ID;


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


    @ApiOperation(value = "일간 캘린더 조회", notes = "일간 캘린더 조회")
    @GetMapping("/dailyCalender")
    public BaseResponse<List<UserRes.DailyCalender>> getMyLifolioDailyCalender(@RequestParam("date") String date) throws ParseException {
        Long userId= userService.findNowLoginUser().getId();
        List<UserRes.DailyCalender> dailyCalenderList = myService.getDailyCalender(userId, date);

        if(dailyCalenderList.isEmpty()){
            return new BaseResponse<>(EMPTY_MYFOLIO_AT_THAT_DATE);
        }

        return new BaseResponse<>(dailyCalenderList);
    }

    @GetMapping("/graph")
    public BaseResponse<List<HomeRes.GraphLifolio>> getGraphLifolio(){
        try {
            Long userId= jwtProvider.getUserIdx();
            List<HomeRes.GraphLifolio> graphLifolio=myService.getGraphLifolio(userId);
            return new BaseResponse<>(graphLifolio);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/category")
    public BaseResponse<List<MyRes.ViewCategory>> getViewCategory(@RequestBody MyReq.FilterCategory filterCategory, @RequestParam(required = false,defaultValue="1") int page){
        try{
            Long userId= jwtProvider.getUserIdx();
            List<MyRes.ViewCategory> viewCategory =myService.getViewCategory(userId,filterCategory,page);
            return new BaseResponse<>(viewCategory);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }



    @GetMapping("/profile/simple") //#2-1 4번은 /profile/detail
    public BaseResponse<UserRes.Profile> getMyProfile(){
        Long userId= userService.findNowLoginUser().getId();
        UserRes.Profile profile = myService.getMyProfileSimple(userId);
        return new BaseResponse<>(profile);
    }


    @ApiOperation(value = "MyFolio 세부 내용 보기", notes = "MyFolio 세부 내용 보기")
    @GetMapping("/myfolio/{folioId}") //#2-1 4번은 /profile/detail
    public BaseResponse<UserRes.GetMyFolioDetailRes> getMyFolioDetailRes(@PathVariable Long folioId){
        Long userId= userService.findNowLoginUser().getId();
        UserRes.GetMyFolioDetailRes getMyFolioDetailRes = myService.getMyLifolioDetail(folioId);

        if(getMyFolioDetailRes == null){
            return new BaseResponse<>(NOT_EXIST_MYFOLIO_ID);
        }

        return new BaseResponse<>(getMyFolioDetailRes);
    }





}
