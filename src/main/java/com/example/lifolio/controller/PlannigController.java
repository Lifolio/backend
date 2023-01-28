package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.PlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.lifolio.base.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/planning")
public class PlannigController {
    private final PlanningService planningService;


//    @GetMapping("/{userId}")

    @PostMapping("/goalOfYear/{userId}")
    public BaseResponse<String> setGoalOfYear(@AuthenticationPrincipal User user, @RequestBody PlanningReq.PostGoalOfYearReq postGoalOfYearReq){
            Long userId=user.getId();
            if(postGoalOfYearReq.getTitle()==null){
                return new BaseResponse<>(NOT_POST_TITLE);
            }
            if(postGoalOfYearReq.getDate()==null){
                return new BaseResponse<>(NOT_POST_DATE);
            }
            planningService.setGoalOfYear(userId,postGoalOfYearReq);
            return new BaseResponse<>("생성 완료.");
    }

    @GetMapping("/goalOfYear/{userId}")
    public BaseResponse<List<PlanningRes.GetGoalOfYearRes>> getGoalOfYear(@AuthenticationPrincipal User user){
            Long userId=user.getId();
            List<PlanningRes.GetGoalOfYearRes> getGoalOfYearResList = planningService.getGoalsByUserId(userId);
            return new BaseResponse<>(getGoalOfYearResList);
    }

    @ResponseBody
    @PatchMapping("/goalOfYear/{userId}/{planningYearId}")
    public BaseResponse<String> updateGoalOfYear(@AuthenticationPrincipal User user,@PathVariable("planningYearId") Long planningYearId,@RequestBody PlanningReq.UpdateGoalOfYearReq updateGoalOfYearReq){
            Long userId=user.getId();
            if(updateGoalOfYearReq.getTitle()==null){
                return new BaseResponse<>(NOT_POST_TITLE);
            }
            if(updateGoalOfYearReq.getDate()==null){
                return new BaseResponse<>(NOT_POST_DATE);
            }
            planningService.updateGoalOfYear(userId,planningYearId,updateGoalOfYearReq);
            return new BaseResponse<>("수정 성공.");
    }

    @ResponseBody
    @PatchMapping("/goalOfYearSuccess/{userId}/{planningYearId}")
    public BaseResponse<String> updateGoalOfYearSuccess(@AuthenticationPrincipal User user,@PathVariable("planningYearId") Long planningYearId){

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
    public BaseResponse<String> deleteGoalOfYear (@AuthenticationPrincipal User user,@PathVariable("planningYearId") Long planningYearId) {
            Long userId=user.getId();
            planningService.deleteGoalOfYear(planningYearId);
            return new BaseResponse<>("삭제 성공.");

    }

    @ResponseBody
    @PostMapping("/day")
    public BaseResponse<String> setTodo(@AuthenticationPrincipal User user,@RequestBody PlanningReq.PostPlanningReq postPlanningReq){
        if(postPlanningReq.getDate()==null){
            return new BaseResponse<>(NOT_EXIST_DATE);
        }
        if(postPlanningReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        String result="생성 성공";
            Long userId=user.getId();

            planningService.setTodo(userId,postPlanningReq);
            return new BaseResponse<>(result);


    }

    @ResponseBody
    @PostMapping("/week")
    public BaseResponse<String> setWeekTodo(@AuthenticationPrincipal User user, @RequestBody PlanningReq.PostPlanningReq postPlanningReq){
        String result="생성 성공";
        if(postPlanningReq.getDate()==null){
            return new BaseResponse<>(NOT_EXIST_DATE);
        }
        if(postPlanningReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
            Long userId=user.getId();

            planningService.setTodoWeek(userId,postPlanningReq);
            return new BaseResponse<>(result);


    }

    @ResponseBody
    @PostMapping("/month")
    public BaseResponse<String> setWeekMonth(@AuthenticationPrincipal User user, @RequestBody PlanningReq.PostPlanningReq postPlanningReq){
        String result="생성 성공";
        if(postPlanningReq.getDate()==null){
            return new BaseResponse<>(NOT_EXIST_DATE);
        }
        if(postPlanningReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
            Long userId = user.getId();

            planningService.setTodoMonth(userId, postPlanningReq);
            return new BaseResponse<>(result);

    }

    @ResponseBody
    @PatchMapping("/day/{planningId}")
    public BaseResponse<String> updateTodo(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId){

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
    public BaseResponse<String> updateTodoWeek(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId){

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
    public BaseResponse<String> updateTodoMonth(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId){

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
    public BaseResponse<String> deleteTodo(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId){
        if(!planningService.existsPlanning(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        planningService.deletePlanning(planningId);

        return new BaseResponse<>("삭제 성공");
    }

    @ResponseBody
    @DeleteMapping("/week/{planningId}")
    public BaseResponse<String> deleteTodoWeek(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId){
        if(!planningService.existsPlanningWeek(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        planningService.deletePlanningWeek(planningId);

        return new BaseResponse<>("삭제 성공");
    }

    @ResponseBody
    @DeleteMapping("/month/{planningId}")
    public BaseResponse<String> deleteTodoMonth(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId){
        if(!planningService.existsPlanningMonth(planningId)){
            return new BaseResponse<>(NOT_EXIST_PLANNING);
        }

        planningService.deletePlanningMonth(planningId);

        return new BaseResponse<>("삭제 성공");
    }

    @ResponseBody
    @GetMapping("/day")
    public BaseResponse<List<PlanningRes.GetPlanning>> getTodoList(@AuthenticationPrincipal User user, @RequestParam("date") String date ,@RequestParam(value = "range",defaultValue = "1")int range){
            Long userId=user.getId();
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

    }
    @PatchMapping("/day/info/{planningId}")
    public BaseResponse<String> patchPlan(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId, @RequestBody PlanningReq.PostPlanningReq postPlanningReq){
            Long userId=user.getId();
            if(!planningService.existsPlanning(planningId)){
                return new BaseResponse<>(NOT_EXIST_PLANNING);
            }
            planningService.patchPlan(userId,postPlanningReq,planningId);
            return new BaseResponse<>("수정 성공");
    }

    @PatchMapping("/week/info/{planningId}")
    public BaseResponse<String> patchPlanWeek(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId, @RequestBody PlanningReq.PostPlanningInfoReq postPlanningReq){
            Long userId=user.getId();
            if(!planningService.existsPlanning(planningId)){
                return new BaseResponse<>(NOT_EXIST_PLANNING);
            }
            planningService.patchPlanWeek(userId,postPlanningReq,planningId);
            return new BaseResponse<>("수정 성공");
    }

    @PatchMapping("/month/info/{planningId}")
    public BaseResponse<String> patchPlanMonth(@AuthenticationPrincipal User user, @PathVariable("planningId") Long planningId, @RequestBody PlanningReq.PostPlanningInfoReq postPlanningReq){
            Long userId=user.getId();
            if(!planningService.existsPlanning(planningId)){
                return new BaseResponse<>(NOT_EXIST_PLANNING);
            }
            planningService.patchPlanMonth(userId,postPlanningReq,planningId);
            return new BaseResponse<>("수정 성공");

    }

}
