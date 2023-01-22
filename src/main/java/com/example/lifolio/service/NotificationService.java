package com.example.lifolio.service;

import com.example.lifolio.dto.alarm.RequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private FirebaseCloudMessageService firebaseMessageService;

    //TODO OOO님 오늘 이룰 목표 N개중 M개 체크하지 못했어요 !     
    @Scheduled(cron = "0 0 18 * * *")
    public List<RequestDTO> getNotificationList(){
        return null;
    }

}
