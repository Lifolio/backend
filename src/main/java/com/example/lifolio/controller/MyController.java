package com.example.lifolio.controller;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.home.HomeRes;
import com.example.lifolio.dto.my.MyReq;
import com.example.lifolio.dto.my.MyRes;
import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.MyFolio;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.repository.MyFolioRepository;
import com.example.lifolio.service.MyService;
import com.example.lifolio.service.S3Service;
import com.example.lifolio.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.example.lifolio.base.BaseResponseStatus.*;
import static com.example.lifolio.base.BaseResponseStatus.NOT_POST_DATE;


@RequiredArgsConstructor
@RestController
@RequestMapping("/my")
public class MyController {
    private final TokenProvider tokenProvider;
    private final MyService myService;
    private final UserService userService;
    private final S3Service s3Service;

    @ApiOperation(value = "MyFolio 생성", notes = "MyFolio 생성")
    @PostMapping("")
    public BaseResponse<String> setMyLifolio(@AuthenticationPrincipal User user, @RequestPart("postMyLifolioReq") MyReq.PostMyLifolioReq postMyLifolioReq, @RequestPart("imgUrl") List<MultipartFile> multipartFiles) {

        Long userId = user.getId();
        if (postMyLifolioReq.getTitle() == null) {
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        if (postMyLifolioReq.getStart_date() == null || postMyLifolioReq.getEnd_date() == null) {
            return new BaseResponse<>(NOT_POST_DATE);
        }
        if (postMyLifolioReq.getContent() == null) {
            return new BaseResponse<>(NOT_POST_CONTENT);
        }
        List<String> imgPaths = s3Service.upload(multipartFiles);
        System.out.println("IMG 경로들 : " + imgPaths);
        myService.setMyLifolio(userId, imgPaths, postMyLifolioReq);
        return new BaseResponse<>("생성 완료.");

    }

    @ApiOperation(value = "MyFolio 한개 조회", notes = "MyFolio 한개 조회")
    @ResponseBody
    @GetMapping("")
    public BaseResponse<UserRes.GetMyRes> getMyLifolio(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        UserRes.GetMyRes getMyRes = myService.getMyLifolio(userId);
        return new BaseResponse<>(getMyRes);


    }

    @ApiOperation(value = "MyFolio 캘린더 조회", notes = "MyFolio 캘린더 조회")
    @ResponseBody
    @GetMapping("/calender")
    public BaseResponse<List<UserRes.Calender>> getMyLifolioCalender(@AuthenticationPrincipal User user, @RequestParam("date") String date) {

        Long userId = user.getId();
        List<UserRes.Calender> calender = myService.getCalender(userId, date);
        return new BaseResponse<>(calender);

    }


    @ApiOperation(value = "일간 캘린더 조회", notes = "일간 캘린더 조회")
    @GetMapping("/dailyCalender")
    public BaseResponse<List<UserRes.DailyCalender>> getMyLifolioDailyCalender(@AuthenticationPrincipal User user, @RequestParam("date") String date) throws ParseException {
        Long userId = userService.findNowLoginUser().getId();
        List<UserRes.DailyCalender> dailyCalenderList = myService.getDailyCalender(userId, date);

        if (dailyCalenderList.isEmpty()) {
            return new BaseResponse<>(EMPTY_MYFOLIO_AT_THAT_DATE);
        }

        return new BaseResponse<>(dailyCalenderList);
    }

    @ApiOperation(value = "#2-2 MyLfolio 메인 그래프 조회", notes = "#2-2 MyLfolio 메인 그래프 조회")
    @GetMapping("/graph")
    public BaseResponse<List<HomeRes.GraphLifolio>> getGraphLifolio(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<HomeRes.GraphLifolio> graphLifolio = myService.getGraphLifolio(userId);
        return new BaseResponse<>(graphLifolio);


    }

    @GetMapping("/category_list")
    public BaseResponse<List<String>> getCategoryList(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<String> categoryList = myService.getCategoryList(userId);
        return new BaseResponse<>(categoryList);

    }

    @PostMapping("/category")
    public BaseResponse<List<MyRes.ViewCategory>> getViewCategory(@AuthenticationPrincipal User user, @RequestBody MyReq.FilterCategory filterCategory, @RequestParam(required = false, defaultValue = "1") int page) {

        Long userId = user.getId();
        List<MyRes.ViewCategory> viewCategory = myService.getViewCategory(userId, filterCategory, page);
        return new BaseResponse<>(viewCategory);

    }


    @GetMapping("/profile/simple") //#2-1 4번은 /profile/detail
    public BaseResponse<UserRes.Profile> getMyProfile(@AuthenticationPrincipal User user) {
        Long userId = userService.findNowLoginUser().getId();
        UserRes.Profile profile = myService.getMyProfileSimple(userId);
        return new BaseResponse<>(profile);
    }


    @ApiOperation(value = "MyFolio 세부 내용 보기", notes = "MyFolio 세부 내용 보기")
    @GetMapping("/myfolio/{folioId}") //#2-1 4번은 /profile/detail
    public BaseResponse<UserRes.GetMyFolioDetailRes> getMyFolioDetailRes(@AuthenticationPrincipal User user, @PathVariable Long folioId) {
        //Long userId= userService.findNowLoginUser().getId();
        UserRes.GetMyFolioDetailRes getMyFolioDetailRes = myService.getMyLifolioDetail(folioId);

        if (getMyFolioDetailRes == null) {
            return new BaseResponse<>(NOT_EXIST_MYFOLIO_ID);
        }

        return new BaseResponse<>(getMyFolioDetailRes);
    }


}
