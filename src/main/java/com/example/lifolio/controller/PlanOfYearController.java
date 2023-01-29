package com.example.lifolio.controller;

import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.PlanOfYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/planOfYear")
public class PlanOfYearController {

    private final PlanOfYearService planOfYearService;

    private final TokenProvider tokenProvider;

    /*

    @PostMapping("/{userId}")
    public BaseResponse<String> setPlanOfYear(@RequestBody PlanOfYearReq.PostPlanOfYearReq postPlanOfYearReq) {

    }

    @GetMapping("/{userId}")
    public BaseResponse<List<PlanOfYearRes.GetPlanOfYearRes>> getPlanOfYear() {

    }




    @ResponseBody
    @PatchMapping("/updatePlanOfYear/{userId}/{PlanOfYearId}")
    public BaseResponse<String> updatePlanOfYear(@PathVariable("PlanOfYearId") Long PlanOfYearId) {

    }

     */

    @ResponseBody
    @DeleteMapping("/{userId}/{PlanOfYearId}")
    public BaseResponse<String> deletePlanOfYear(@AuthenticationPrincipal User user, @PathVariable("PlanOfYearId") Long PlanOfYearId) {
        Long userId = user.getId();
        planOfYearService.deletePlanOfYear(PlanOfYearId);
        return new BaseResponse<>("삭제 성공");

    }

}
