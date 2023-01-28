package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.home.*;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.HomeService;
import com.example.lifolio.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.lifolio.base.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final HomeService homeService;

    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<HomeRes.GetHomeRes> home(@AuthenticationPrincipal User user, @PathVariable("userId") Long userId){
            Long userIdx= user.getId();
            if(userId!=userIdx){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            HomeRes.GetHomeRes getHomeRes = homeService.getHomeRes(userIdx);
            return new BaseResponse<>(getHomeRes);
    }

    @ResponseBody
    @GetMapping("/graph/{userId}/{customId}")
    public BaseResponse<List<HomeRes.GetGraphRes>> getGraphCustomFolio(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @PathVariable("customId")Long customId){
            Long idByJwt= user.getId();
            if(userId!=idByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<HomeRes.GetGraphRes> graphInfo = homeService.getGraphLifolio(userId,customId);
            return new BaseResponse<>(graphInfo);
    }

    @ResponseBody
    @PostMapping("/custom")
    public BaseResponse<String> postCustomFolio(@AuthenticationPrincipal User user,@RequestBody HomeReq.CustomUpdateReq customUpdateReq){
        if(homeService.countCustomLifolio(user.getId())>3){
            return new BaseResponse<>(DONT_POST_CUSTOMLIFOLIO);
        }
        Long userId=user.getId();
        homeService.postCustomFolio(userId,customUpdateReq);
        return new BaseResponse<>("생성 성공");
    }

    @ResponseBody
    @GetMapping("/custom/{userId}/{customId}")
    public BaseResponse<List<HomeRes.GetCustomRes>> getCustomFolio(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @PathVariable("customId")Long customId){
            Long idByJwt= user.getId();
            if(userId!=idByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<HomeRes.GetCustomRes> customInfo = homeService.getCustomLifolio(userId,customId);
            return new BaseResponse<>(customInfo);
    }

    @ResponseBody
    @PatchMapping("/custom/{userId}/{customId}")
    public BaseResponse<String> updateCustomFolio(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @PathVariable("customId") Long customId,@RequestBody HomeReq.CustomUpdateReq customUpdateReq){
            Long idByJwt= user.getId();
            if(userId!=idByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            homeService.setCustomLifolio(userId,customId,customUpdateReq);
            return new BaseResponse<>("수정 성공.");
    }



    @ApiOperation(value = "올해의 나 추가", notes = "올해의 나 추가")
    @PostMapping("/goal/{userId}")
    public BaseResponse<HomeRes.PostGoalRes> setGoalOfYear(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @RequestBody HomeReq.PostGoalReq postGoalReq){

        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        return new BaseResponse<>(homeService.setGoalOfYear(postGoalReq));
    }


    @ApiOperation(value = "올해의 나 전부 조회(userId=유저인덱스)", notes = "올해의 나 전부 조회")
    @GetMapping("/goal/{userId}")
    public BaseResponse<List<HomeRes.GetGoalRes>> setGoalOfYear(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId){
        List<HomeRes.GetGoalRes> getGoalResList = homeService.getGoalsByUserId(userId);

        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        if(getGoalResList != null){
            return new BaseResponse<>(getGoalResList);
        } else{
            return new BaseResponse<>(EMPTY_GOAL_OF_YEAR);
        }
    }

    @ResponseBody
    @GetMapping("/badge/{userId}")
    public BaseResponse<List<HomeRes.GetBadgeRes>> getBadge(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId){
        List<HomeRes.GetBadgeRes> getBadgeResList = homeService.getBadgeByUserId(userId);

        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        if(getBadgeResList != null){
            return new BaseResponse<>(getBadgeResList);
        } else{
            return new BaseResponse<>(EMPTY_GOAL_OF_YEAR);
        }
    }



}
