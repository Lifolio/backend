package com.example.lifolio.service;

import com.example.lifolio.converter.CustomLifolioConvertor;
import com.example.lifolio.dto.home.*;
import com.example.lifolio.entity.*;
import com.example.lifolio.entity.CustomLifolio;
import com.example.lifolio.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final BadgeRepository badgeRepository;

    public HomeRes.GetHomeRes getHomeRes(Long userId) {
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();

        //올해 기준 홈
        int year = now.getYear();

        CustomLifolioColor customLifolioColor = customLifolioColorRepository.findByUserId(userId);
        Optional<GoalOfYear> goalOfYear = goalOfYearRepository.findTop1ByUserIdAndYearOrderByCreatedAtDesc(userId, year);

        HomeRes.TopInfo topInfo = new HomeRes.TopInfo();

        //TopInfo null 값 예외처리
        if (customLifolioColor == null && !goalOfYear.isPresent()) {
            topInfo = new HomeRes.TopInfo(1, "목표 없음");
        } else if (customLifolioColor == null) {
            topInfo = new HomeRes.TopInfo(1, goalOfYear.get().getGoal());
        } else if (!goalOfYear.isPresent()) {
            topInfo = new HomeRes.TopInfo(customLifolioColor.getColorStatus(), "목표 없음");
        } else {
            topInfo = new HomeRes.TopInfo(customLifolioColor.getColorStatus(), goalOfYear.get().getGoal());
        }

        //MainLifolio 조회
        List<MyFolioRepository.GraphLifolio> mainLifolioResult = myFolioRepository.getMainFolio(userId, year);
        List<HomeRes.GraphLifolio> graphLifolio = new ArrayList<>();
        mainLifolioResult.forEach(
                myFolio -> {
                    graphLifolio.add(new HomeRes.GraphLifolio(
                            myFolio.getMonth(),
                            myFolio.getStar()
                    ));
                }
        );

        //CustomLifolio 조회
        List<CustomLifolioRepository.CustomUserLifolio> customUserLifolioResult = customLifolioRepository.getCustomFolio(userId);
        List<HomeRes.CustomUserLifolioRes> customResult = new ArrayList<>();
        customUserLifolioResult.forEach(
                custom -> {
                    customResult.add(new HomeRes.CustomUserLifolioRes(
                            custom.getCustomId(),
                            custom.getConcept(),
                            custom.getEmoji(),
                            custom.getCustomName()));
                }
        );
        return new HomeRes.GetHomeRes(topInfo, graphLifolio, customResult);


    }

    public List<HomeRes.GetGraphRes> getGraphLifolio(Long userId, Long customId) {
        List<MyFolioRepository.CustomGraphLifolio> graphLifolioResult = myFolioRepository.getGraphLifolio(userId, customId);

        List<HomeRes.GetGraphRes> graphResult = new ArrayList<>();

        graphLifolioResult.forEach(
                myFolio -> {
                    graphResult.add(new HomeRes.GetGraphRes(
                            myFolio.getDay(),
                            myFolio.getStar()
                    ));
                }
        );
        return graphResult;
    }

    public List<HomeRes.GetCustomRes> getCustomLifolio(Long userId, Long customId) {
        List<MyFolioRepository.CustomInfoLifolio> customLifolioResult = myFolioRepository.getCustomLifolio(userId, customId);
        List<HomeRes.GetCustomRes> customResult = new ArrayList<>();

        CustomLifolio customLifolio=customLifolioRepository.getOne(customId);

        customLifolioResult.forEach(
                custom -> {
                    customResult.add(new HomeRes.GetCustomRes(
                            custom.getFolioId(),
                            custom.getTitle(),
                            custom.getUrl()
                    ));
                }
        );
        return customResult;
    }

    @SneakyThrows
    public void setCustomLifolio(Long userId, Long customId, HomeReq.CustomUpdateReq customUpdateReq){ //커스텀폴리오 수정
        //CustomLifolio customLifolio = customLifolioRepository.getCustomFolioCategory(customId);
        CustomLifolio customLifolio=customLifolioRepository.getOne(customId);
        customLifolio.updateCustomLifolio(customUpdateReq.getCustomName(),customUpdateReq.getCategory(),
                customUpdateReq.getConcept(),customUpdateReq.getEmoji());
        customLifolioRepository.save(customLifolio);
    }


    public HomeRes.PostGoalRes setGoalOfYear(HomeReq.PostGoalReq postGoalReq){
        LocalDate now = LocalDate.now();
        int year = now.getYear(); //일단은 현재 시간에만 설정할 수 있도록 설정

        User user = userService.findNowLoginUser();

        GoalOfYear toSaveGoalOfYear = GoalOfYear.builder()
                .userId(user.getId())
                .goal(postGoalReq.getGoal())
                .year(year)
                .build();

        goalOfYearRepository.save(toSaveGoalOfYear);

        return new HomeRes.PostGoalRes(toSaveGoalOfYear.getGoal());
    }



    public List<HomeRes.GetGoalRes> getGoalsByUserId(Long userId) {
        List<GoalOfYear> goalList = goalOfYearRepository.findAllByUserIdOrderByYearAsc(userId);
        List<HomeRes.GetGoalRes> getGoalResList = new ArrayList<>();

        if(goalList.isEmpty()) return null;

        for(GoalOfYear g: goalList){
            LocalDate date = g.getCreatedAt().toLocalDate();
            String createDate = date.getMonthValue() + ". " + date.getDayOfMonth();

            HomeRes.GetGoalRes getGoalRes = new HomeRes.GetGoalRes(g.getYear(), g.getGoal(), createDate);
            getGoalResList.add(getGoalRes);
        }

        return getGoalResList;
    }



    public List<HomeRes.GetBadgeRes> getBadgeByUserId(Long userId) {
        List<BadgeRepository.BadgeSuccess> badgeList = badgeRepository.getBadgeByUserId(userId);
        List<HomeRes.GetBadgeRes> getBadgeResList = new ArrayList<>();

        badgeList.forEach(
                custom -> {
                    getBadgeResList.add(new HomeRes.GetBadgeRes(
                            custom.getUrl(),
                            custom.getTitle(),
                            custom.getSuccess()
                    ));
                }
        );
        return getBadgeResList;
    }

    public void postCustomFolio(Long userId, HomeReq.CustomUpdateReq customUpdateReq) {
        CustomLifolio customLifolio= CustomLifolioConvertor.PostCustomLifolio(userId,customUpdateReq);
        customLifolioRepository.save(customLifolio);
    }

    public int countCustomLifolio(Long userId) {
        return customLifolioRepository.countByUserId(userId);
    }
}