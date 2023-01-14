package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.planning.PlanningReq;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.PlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
