package com.example.lifolio.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class HomeRes {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GraphLifolio {
        private int month;
        private int star;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GetHomeRes {
        private TopInfo topInfo;
        private List<GraphLifolio> graphLifolio;
        private List<CustomUserLifolioRes> customLifolio;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GetGraphRes {
        private String day;
        private int star;
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class GetGoalRes {
        private int year;
        private String goal;
        private LocalDate createDate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GetCustomRes {
        private Long folioId;
        private String title;
        private String url;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GetBadgeRes {
        private String url;
        private String title;
        private int success;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomUserLifolioRes {
        private Long customId;
        private int concept;
        private String emoji;
        private String customName;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostGoalRes {
        private String goal;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopInfo {
        private int colorStatus;
        private String goal;
    }

    public static class GetTitleLifolioRes {
    }
}
