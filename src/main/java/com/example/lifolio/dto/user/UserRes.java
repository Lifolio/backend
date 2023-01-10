package com.example.lifolio.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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



}
