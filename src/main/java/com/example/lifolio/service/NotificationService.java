package com.example.lifolio.service;

import com.example.lifolio.converter.AlarmConvertor;
import com.example.lifolio.converter.TimeConvertor;
import com.example.lifolio.dto.alarm.AlarmRes;
import com.example.lifolio.dto.alarm.RequestDTO;
import com.example.lifolio.dto.planning.PlanningRes;
import com.example.lifolio.repository.PlanningMonthRepository;
import com.example.lifolio.repository.PlanningRepository;
import com.example.lifolio.repository.PlanningWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.tomcat.jni.Time.now;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FirebaseCloudMessageService firebaseMessageService;

    private final PlanningRepository planningRepository;
    private final PlanningWeekRepository planningWeekRepository;
    private final PlanningMonthRepository planningMonthRepository;

    //TODO OOO님 오늘 이룰 목표 N개중 M개 체크하지 못했어요 !     
    @Scheduled(cron = "0 0 18 * * *")
    public void scheduledTodoAlarm() throws IOException {
        List<AlarmRes.PlanningUserList> getTodoUserList=getUserList();


        for (AlarmRes.PlanningUserList planningUserList : getTodoUserList) {
            notificationUser(planningUserList);
        }

    }

    @Transactional(rollbackFor= SQLException.class)
    public void notificationUser(AlarmRes.PlanningUserList planningUser) throws IOException {

        List<PlanningRepository.TodoList> todoListResult = planningRepository.getTodoList(planningUser.getUserId());
        List<AlarmRes.PlanningRes> todoSuccess = new ArrayList<>();

        todoListResult.forEach(
                custom -> {
                    todoSuccess.add(new AlarmRes.PlanningRes(
                            custom.getSuccess()));
                    });
        List<Integer> todoPlanning = todoSuccess.stream().map(e -> e.getSuccess()).collect(Collectors.toList());




        RequestDTO requestDTO = AlarmConvertor.RequestDto(planningUser, todoPlanning);

        firebaseMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());


    }

    @Transactional(rollbackFor= SQLException.class)
    public List<AlarmRes.PlanningUserList> getUserList() {
        List<PlanningRepository.UserIdList> userIdListResult=planningRepository.getUserIdList();
        List<AlarmRes.PlanningUserList> planningUserList=new ArrayList<>();
        userIdListResult.forEach(
                custom->{
                    planningUserList.add(new AlarmRes.PlanningUserList(
                            custom.getUserId(),
                            custom.getNickname(),
                            custom.getFcmToken()));
                }
        );

        return planningUserList;
    }


    //TODO 이번주 목표 알림
    @Scheduled(cron = "0 0 18 * * 6")
    public void scheduledTodoWeekAlarm() throws IOException {
        List<AlarmRes.PlanningUserList> getTodoUserList=getUserWeekList();

        for (AlarmRes.PlanningUserList planningUserList : getTodoUserList) {
            notificationWeekUser(planningUserList);
        }

    }

    private void notificationWeekUser(AlarmRes.PlanningUserList planningUser) throws IOException {
        LocalDate date = LocalDate.now();
        PlanningRes.TimeRes timeRes= TimeConvertor.getThisWeek(String.valueOf(date));

        List<PlanningWeekRepository.TodoList> todoListResult = planningWeekRepository.getTodoList(timeRes.getStartTime(),timeRes.getFinishTime(),planningUser.getUserId());
        List<AlarmRes.PlanningRes> todoSuccess = new ArrayList<>();

        todoListResult.forEach(
                custom -> {
                    todoSuccess.add(new AlarmRes.PlanningRes(
                            custom.getSuccess()));
                }
        );
        List<Integer> todoPlanning  = todoSuccess.stream().map(e -> e.getSuccess()).collect(Collectors.toList());

        RequestDTO requestDTO = AlarmConvertor.AlarmWeek(planningUser, todoPlanning);

        firebaseMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());

        }



    private List<AlarmRes.PlanningUserList> getUserWeekList() {
        LocalDate date = LocalDate.now();
        PlanningRes.TimeRes timeRes= TimeConvertor.getThisWeek(String.valueOf(date));

        List<PlanningWeekRepository.UserIdList> userIdListResult=planningWeekRepository.getUserIdList(timeRes.getStartTime(),timeRes.getFinishTime());
        List<AlarmRes.PlanningUserList> planningUserList=new ArrayList<>();

        userIdListResult.forEach(
                custom->{
                    planningUserList.add(new AlarmRes.PlanningUserList(
                            custom.getUserId(),
                            custom.getNickname(),
                            custom.getFcmToken()));
                }
        );

        return planningUserList;
    }

    //TODO 이번달 목표 알림
    @Scheduled(cron = "0 0 6 L * ?")
    public void scheduledTodoMonthAlarm() throws IOException {
        List<AlarmRes.PlanningUserList> getTodoUserList=getUserMonthList();

        for (AlarmRes.PlanningUserList planningUserList : getTodoUserList) {
            notificationMonthUser(planningUserList);
        }

    }

    private void notificationMonthUser(AlarmRes.PlanningUserList planningUser) throws IOException {
        LocalDate date = LocalDate.now();
        PlanningRes.TimeRes timeRes= TimeConvertor.getThisMonth(String.valueOf(date));

        List<PlanningMonthRepository.TodoList> todoListResult = planningMonthRepository.getTodoList(timeRes.getStartTime(),timeRes.getFinishTime(),planningUser.getUserId());
        List<AlarmRes.PlanningRes> todoSuccess = new ArrayList<>();

        todoListResult.forEach(
                custom -> {
                    todoSuccess.add(new AlarmRes.PlanningRes(
                            custom.getSuccess()));
                }
        );
        List<Integer> todoPlanning  = todoSuccess.stream().map(e -> e.getSuccess()).collect(Collectors.toList());

        RequestDTO requestDTO = AlarmConvertor.AlarmMonth(planningUser, todoPlanning);

        firebaseMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());

    }



    private List<AlarmRes.PlanningUserList> getUserMonthList() {
        LocalDate date = LocalDate.now();
        PlanningRes.TimeRes timeRes= TimeConvertor.getThisMonth(String.valueOf(date));

        List<PlanningMonthRepository.UserIdList> userIdListResult=planningMonthRepository.getUserIdList(timeRes.getStartTime(),timeRes.getFinishTime());
        List<AlarmRes.PlanningUserList> planningUserList=new ArrayList<>();

        userIdListResult.forEach(
                custom->{
                    planningUserList.add(new AlarmRes.PlanningUserList(
                            custom.getUserId(),
                            custom.getNickname(),
                            custom.getFcmToken()));
                }
        );

        return planningUserList;
    }

}
