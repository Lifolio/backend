package com.example.lifolio.dto.user;

import com.example.lifolio.entity.Category;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UserRes {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class GetMyRes{
        private String name;
        private int lifolioCnt;
        private List<BestCategory> bestCategory;
        private List<Archive> archive;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class BestCategory{
        private String color;
        private String category;
        private String url;
        private String title;
        private int star;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Archive{
        private Long folioId;
        private String color;
        private String category;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Calender{
        private LocalDate date;
        private String color;
        private Long folioId;
        private String url;
        private String title;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Profile{
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class GenerateToken{
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class AlarmList {

        private Integer AllAlarm;

        private Integer myAlarm;

        private Integer weekAlarm;

        private Integer badgeAlarm;

        private Integer planningAlarm;

        private Integer todoAlarm;

        private Integer goalAlarm;

        private Integer socialAlarm;

        private Integer uploadAlarm;

        private Integer interestAlarm;

        private Integer likeAlarm;

        private Integer marketingAlarm;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenRes {
        private Long userId; //user 인덱스
        private String accessToken;
        private String refreshToken;
        private String name; //실제 유저 이름
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FindUserIdRes {
        private String username;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GetSMSRes {
        private int number;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class KakaoLoginRes {
        private long userId;
        private String email;
        private String nickname;
        private String type;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PasswordRes {
        private String newPassword;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorityRes {
        private String authorityName;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class DailyCalender{
        private Date date; //2023-01-11
        private String category;
        private String title;
        private Integer star;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class GetMyFolioDetailRes{  //MyFolio 한 개 기록의 세부 내용 전부 보기
        String myFolioTitle;
        List<MyFolioImgList> myFolioImgList;
        String category; //대분류
        String content;
        String latitude; //위도
        String longitude; //경도
        List<MyFolioWithList> myFolioWithList;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class MyFolioWithList{
        String withName;  //마이폴리오 같이 한 사람 이름
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class MyFolioImgList{
        String url;
    }



}
