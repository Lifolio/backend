package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.planning.PlanningYearReq;
import com.example.lifolio.dto.planning.PlanningYearRes;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.PlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/planning")
public class PlannigController {
    private final TokenProvider tokenProvider;

    private final PlanningService planningService;

    @PostMapping("/goalOfYear/{userId}")
    public BaseResponse<String> setGoalOfYear(@RequestBody PlanningYearReq.PostGoalOfYearReq postGoalOfYearReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            planningService.setGoalOfYear(userId,postGoalOfYearReq);
            return new BaseResponse<>("생성 완료.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/goalOfYear/{userId}")
    public BaseResponse<List<PlanningYearRes.GetGoalOfYearRes>> getGoalOfYear(){
        try {
            Long userId=tokenProvider.getUserIdx();
            List<PlanningYearRes.GetGoalOfYearRes> getGoalOfYearResList = planningService.getGoalsByUserId(userId);
            return new BaseResponse<>(getGoalOfYearResList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/goalOfYear/{userId}/{planningYearId}")
    public BaseResponse<String> updateGoalOfYear(@PathVariable("planningYearId") Long planningYearId,@RequestBody PlanningYearReq.UpdateGoalOfYearReq updateGoalOfYearReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            planningService.updateGoalOfYear(userId,planningYearId,updateGoalOfYearReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/goalOfYearSuccess/{userId}/{planningYearId}")
    public BaseResponse<String> updateGoalOfYearSuccess(@PathVariable("planningYearId") Long planningYearId,@RequestBody PlanningYearReq.UpdateGoalOfYearSuccessReq updateGoalOfYearSuccessReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            planningService.updateGoalOfYearSuccess(planningYearId,updateGoalOfYearSuccessReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @DeleteMapping("/goalOfYear/{userId}/{planningYearId}")
    public BaseResponse<String> deleteGoalOfYear (@PathVariable("planningYearId") Long planningYearId) {
        try {
            Long userId=tokenProvider.getUserIdx();
            planningService.deleteGoalOfYear(planningYearId);
            return new BaseResponse<>("삭제 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
}
