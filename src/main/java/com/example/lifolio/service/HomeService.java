package com.example.lifolio.service;

import com.example.lifolio.dto.home.*;
import com.example.lifolio.dto.user.PasswordReq;
import com.example.lifolio.dto.user.PasswordRes;
import com.example.lifolio.entity.CustomLifolio;
import com.example.lifolio.entity.CustomLifolio;
import com.example.lifolio.entity.CustomLifolioColor;
import com.example.lifolio.entity.GoalOfYear;
import com.example.lifolio.entity.User;
import com.example.lifolio.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final UserRepository userRepository;
    private final GoalOfYearRepository goalOfYearRepository;
    private final CustomLifolioColorRepository customLifolioColorRepository;
    private final CustomLifolioRepository customLifolioRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyFolioRepository myFolioRepository;
    private final UserService userService;

    public GetHomeRes getHomeRes(Long userId) {
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();

        //올해 기준 홈
        int year = now.getYear();
        CustomLifolioColor customLifolioColor = customLifolioColorRepository.findByUserId(userId);
        GoalOfYear goalOfYear = goalOfYearRepository.findByUserIdAndYear(userId, year);

        TopInfo topInfo = new TopInfo();

        //TopInfo null 값 예외처리
        if (customLifolioColor == null && goalOfYear == null) {
            topInfo = new TopInfo(1, "목표 없음");
        } else if (customLifolioColor == null) {
            topInfo = new TopInfo(1, goalOfYear.getGoal());
        } else if (goalOfYear == null) {
            topInfo = new TopInfo(customLifolioColor.getColorStatus(), "목표 없음");
        } else {
            topInfo = new TopInfo(customLifolioColor.getColorStatus(), goalOfYear.getGoal());
        }

        //MainLifolio 조회
        List<MyFolioRepository.GraphLifolio> mainLifolioResult = myFolioRepository.getMainFolio(userId, year);
        List<GraphLifolio> graphLifolio = new ArrayList<>();
        mainLifolioResult.forEach(
                myFolio -> {
                    graphLifolio.add(new GraphLifolio(
                            myFolio.getMonth(),
                            myFolio.getStar()
                    ));
                }
        );

        //CustomLifolio 조회
        List<CustomLifolioRepository.CustomUserLifolio> customUserLifolioResult = customLifolioRepository.getCustomFolio(userId);

        List<CustomUserLifolioRes> customResult = new ArrayList<>();
        customUserLifolioResult.forEach(
                custom -> {
                    customResult.add(new CustomUserLifolioRes(
                            custom.getCustomId(),
                            custom.getConcept(),
                            custom.getEmoji(),
                            custom.getCustomName()));
                }
        );
        return new GetHomeRes(topInfo, graphLifolio, customResult);


    }

    public List<GetGraphRes> getGraphLifolio(Long userId, Long customId) {
        List<MyFolioRepository.CustomGraphLifolio> graphLifolioResult = myFolioRepository.getGraphLifolio(userId, customId);

        List<GetGraphRes> graphResult = new ArrayList<>();

        graphLifolioResult.forEach(
                myFolio -> {
                    graphResult.add(new GetGraphRes(
                            myFolio.getDay(),
                            myFolio.getStar()
                    ));
                }
        );
        return graphResult;
    }

    public List<GetCustomRes> getCustomLifolio(Long userId, Long customId) {
        List<MyFolioRepository.CustomInfoLifolio> customLifolioResult = myFolioRepository.getCustomLifolio(userId, customId);
        List<GetCustomRes> customResult = new ArrayList<>();

        CustomLifolio customLifolio=customLifolioRepository.getOne(customId);

        customLifolioResult.forEach(
                custom -> {
                    customResult.add(new GetCustomRes(
                            custom.getFolioId(),
                            custom.getTitle(),
                            custom.getUrl()
                    ));
                }
        );
        return customResult;
    }

    @SneakyThrows
    public void setCustomLifolio(Long userId, Long customId, CustomUpdateReq customUpdateReq){ //커스텀폴리오 수정
        //CustomLifolio customLifolio = customLifolioRepository.getCustomFolioCategory(customId);
        CustomLifolio customLifolio=customLifolioRepository.getOne(customId);
        customLifolio.updateCustomLifolio(customUpdateReq.getCustomName(),customUpdateReq.getCategory(),
                customUpdateReq.getConcept(),customUpdateReq.getEmoji());
        customLifolioRepository.save(customLifolio);
    }


    public PostGoalRes setGoalOfYear(PostGoalReq postGoalReq){
        LocalDate now = LocalDate.now();
        int year = now.getYear(); //일단은 현재 시간에만 설정할 수 있도록 설정

        User user = userService.findNowLoginUser();

        GoalOfYear toSaveGoalOfYear = GoalOfYear.builder()
                .userId(user.getId())
                .goal(postGoalReq.getGoal())
                .year(year)
                .build();

        goalOfYearRepository.save(toSaveGoalOfYear);

        return new PostGoalRes(toSaveGoalOfYear.getGoal());
    }



    public List<GetGoalRes> getGoalsByUserId(Long userId) {
        List<GoalOfYear> goalList = goalOfYearRepository.findAllByUserIdOrderByYearAsc(userId);
        List<GetGoalRes> getGoalResList = new ArrayList<>();

        if(goalList.isEmpty()) return null;

        for(GoalOfYear g: goalList){
            GetGoalRes getGoalRes = new GetGoalRes(g.getYear(), g.getGoal(), g.getCreatedAt().toLocalDate());
            getGoalResList.add(getGoalRes);
        }

        return getGoalResList;
    }



}