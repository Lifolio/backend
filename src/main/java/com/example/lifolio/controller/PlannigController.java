package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.PlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.lifolio.base.BaseResponseStatus.NOT_EXIST_PLANNING;
import static com.example.lifolio.base.BaseResponseStatus.NOT_POST_TITLE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/planning")
public class PlannigController {
    private final TokenProvider tokenProvider;

    private final PlanningService planningService;

    @PostMapping("/goalOfYear/{userId}")
    public BaseResponse<String> setGoalOfYear(@RequestBody PlanningReq.PostGoalOfYearReq postGoalOfYearReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            planningService.setGoalOfYear(userId,postGoalOfYearReq);
            return new BaseResponse<>("생성 완료.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/goalOfYear/{userId}")
    public BaseResponse<List<PlanningRes.GetGoalOfYearRes>> getGoalOfYear(){
        try {
            Long userId=tokenProvider.getUserIdx();
            List<PlanningRes.GetGoalOfYearRes> getGoalOfYearResList = planningService.getGoalsByUserId(userId);
            return new BaseResponse<>(getGoalOfYearResList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/goalOfYear/{userId}/{planningYearId}")
    public BaseResponse<String> updateGoalOfYear(@PathVariable("planningYearId") Long planningYearId,@RequestBody PlanningReq.UpdateGoalOfYearReq updateGoalOfYearReq){
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
    public BaseResponse<String> updateGoalOfYearSuccess(@PathVariable("planningYearId") Long planningYearId,@RequestBody PlanningReq.UpdateGoalOfYearSuccessReq updateGoalOfYearSuccessReq){
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

    @ResponseBody
    @PostMapping("/day")
    public BaseResponse<String> setTodo(@RequestBody PlanningReq.PostPlanningReq postPlanningReq){
        String result="생성 성공";
        try {
            Long userId=tokenProvider.getUserIdx();
            if(postPlanningReq.getTitle()==null){
                return new BaseResponse<>(NOT_POST_TITLE);
            }
            planningService.setTodo(userId,postPlanningReq);
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @ResponseBody
    @PatchMapping("/day/{planningId}")
    public BaseResponse<String> updateTodo(@PathVariable("planningId") Long planningId){

        if(!planningService.existsPlanning(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        int success=planningService.getPlanningSuccess(planningId);

        if(success==0) {
            planningService.checkSuccess(planningId);
            return new BaseResponse<>("check");
        }
        planningService.unCheckSuccess(planningId);

        return new BaseResponse<>("unCheck");

    }

    @ResponseBody
    @DeleteMapping("/day/{planningId}")
    public BaseResponse<String> deleteTodo(@PathVariable("planningId") Long planningId){
        if(!planningService.existsPlanning(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        planningService.deletePlanning(planningId);

        return new BaseResponse<>("삭제 성공");
    }

    @ResponseBody
    @GetMapping("/day")
    public BaseResponse<List<PlanningRes.GetPlanning>> getTodoList(@RequestParam(value = "range",defaultValue = "1")int range){
        try {
            Long userId=tokenProvider.getUserIdx();
            List<PlanningRes.GetPlanning> todoList = null;
            if(range==1) {
                todoList = planningService.getTodoList(userId);
            }
            else if(range==2){
                todoList=planningService.getTodoListThisWeek(userId);
            }
            else if(range==3){
                todoList=planningService.getTodoListThisMonth(userId);
            }

            return new BaseResponse<>(todoList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }


}
