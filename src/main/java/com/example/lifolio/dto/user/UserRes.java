package com.example.lifolio.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
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
}
