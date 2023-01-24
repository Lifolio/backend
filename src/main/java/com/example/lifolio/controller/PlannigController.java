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

import static com.example.lifolio.base.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/planning")
public class PlannigController {
    private final TokenProvider tokenProvider;
    private final PlanningService planningService;


//    @GetMapping("/{userId}")

    @PostMapping("/goalOfYear/{userId}")
    public BaseResponse<String> setGoalOfYear(@RequestBody PlanningReq.PostGoalOfYearReq postGoalOfYearReq){
        try {
            Long userId=tokenProvider.getUserIdx();
            if(postGoalOfYearReq.getTitle()==null){
                return new BaseResponse<>(NOT_POST_TITLE);
            }
            if(postGoalOfYearReq.getDate()==null){
                return new BaseResponse<>(NOT_POST_DATE);
            }
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
            if(updateGoalOfYearReq.getTitle()==null){
                return new BaseResponse<>(NOT_POST_TITLE);
            }
            if(updateGoalOfYearReq.getDate()==null){
                return new BaseResponse<>(NOT_POST_DATE);
            }
            planningService.updateGoalOfYear(userId,planningYearId,updateGoalOfYearReq);
            return new BaseResponse<>("수정 성공.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/goalOfYearSuccess/{userId}/{planningYearId}")
    public BaseResponse<String> updateGoalOfYearSuccess(@PathVariable("planningYearId") Long planningYearId){

        int success=planningService.getPlanningYearSuccess(planningYearId);

        if(success==0) {
            planningService.checkSuccessByYear(planningYearId);
            return new BaseResponse<>("check");
        }
        planningService.unCheckSuccessByYear(planningYearId);

        return new BaseResponse<>("unCheck");

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
        if(postPlanningReq.getDate()==null){
            return new BaseResponse<>(NOT_EXIST_DATE);
        }
        if(postPlanningReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        String result="생성 성공";
        try {
            Long userId=tokenProvider.getUserIdx();

            planningService.setTodo(userId,postPlanningReq);
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @ResponseBody
    @PostMapping("/week")
    public BaseResponse<String> setWeekTodo(@RequestBody PlanningReq.PostPlanningReq postPlanningReq){
        String result="생성 성공";
        if(postPlanningReq.getDate()==null){
            return new BaseResponse<>(NOT_EXIST_DATE);
        }
        if(postPlanningReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        try {
            Long userId=tokenProvider.getUserIdx();

            planningService.setTodoWeek(userId,postPlanningReq);
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @ResponseBody
    @PostMapping("/month")
    public BaseResponse<String> setWeekMonth(@RequestBody PlanningReq.PostPlanningReq postPlanningReq){
        String result="생성 성공";
        if(postPlanningReq.getDate()==null){
            return new BaseResponse<>(NOT_EXIST_DATE);
        }
        if(postPlanningReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        try {
            Long userId=tokenProvider.getUserIdx();

            planningService.setTodoMonth(userId,postPlanningReq);
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
    @PatchMapping("/week/{planningId}")
    public BaseResponse<String> updateTodoWeek(@PathVariable("planningId") Long planningId){

        if(!planningService.existsPlanningWeek(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        int success=planningService.getPlanningWeekSuccess(planningId);

        if(success==0) {
            planningService.checkWeekSuccess(planningId);
            return new BaseResponse<>("check");
        }
        planningService.unCheckWeekSuccess(planningId);

        return new BaseResponse<>("unCheck");

    }

    @ResponseBody
    @PatchMapping("/month/{planningId}")
    public BaseResponse<String> updateTodoMonth(@PathVariable("planningId") Long planningId){

        if(!planningService.existsPlanningMonth(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        int success=planningService.getPlanningMonthSuccess(planningId);

        if(success==0) {
            planningService.checkMonthSuccess(planningId);
            return new BaseResponse<>("check");
        }
        planningService.unCheckMonthSuccess(planningId);

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
    @DeleteMapping("/week/{planningId}")
    public BaseResponse<String> deleteTodoWeek(@PathVariable("planningId") Long planningId){
        if(!planningService.existsPlanningWeek(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        planningService.deletePlanningWeek(planningId);

        return new BaseResponse<>("삭제 성공");
    }

    @ResponseBody
    @DeleteMapping("/month/{planningId}")
    public BaseResponse<String> deleteTodoMonth(@PathVariable("planningId") Long planningId){
        if(!planningService.existsPlanningMonth(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        planningService.deletePlanningMonth(planningId);

        return new BaseResponse<>("삭제 성공");
    }

    @ResponseBody
    @GetMapping("/day")
    public BaseResponse<List<PlanningRes.GetPlanning>> getTodoList(@RequestParam("date") String date ,@RequestParam(value = "range",defaultValue = "1")int range){
        try {
            Long userId=tokenProvider.getUserIdx();
            List<PlanningRes.GetPlanning> todoList = null;

            if(range==1) {
                todoList = planningService.getTodoList(date,userId);
            }
            else if(range==2){
                todoList= planningService.getTodoListThisWeek(date,userId);
            }
            else if(range==3) {
                todoList = planningService.getTodoListThisMonth(date,userId);
            }
            return new BaseResponse<>(todoList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
    @PatchMapping("/day/info/{planningId}")
    public BaseResponse<String> patchPlan(@PathVariable("planningId") Long planningId, @RequestBody PlanningReq.PostPlanningReq postPlanningReq){
        try{
            Long userId=tokenProvider.getUserIdx();
            planningService.patchPlan(userId,postPlanningReq,planningId);
            return new BaseResponse<>("수정 성공");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/week/info/{planningId}")
    public BaseResponse<String> patchPlanWeek(@PathVariable("planningId") Long planningId, @RequestBody PlanningReq.PostPlanningInfoReq postPlanningReq){
        try{
            Long userId=tokenProvider.getUserIdx();
            planningService.patchPlanWeek(userId,postPlanningReq,planningId);
            return new BaseResponse<>("수정 성공");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/month/info/{planningId}")
    public BaseResponse<String> patchPlanMonth(@PathVariable("planningId") Long planningId, @RequestBody PlanningReq.PostPlanningInfoReq postPlanningReq){
        try{
            Long userId=tokenProvider.getUserIdx();
            planningService.patchPlanMonth(userId,postPlanningReq,planningId);
            return new BaseResponse<>("수정 성공");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
