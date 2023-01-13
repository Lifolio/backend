package com.example.lifolio.dto.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class MyRes {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ViewCategory{
        private Long folioId;
        private String url;
        private String title;
        private int archiveCheck;
        private LocalDate date;
        private String category;
        private String colorName;
        private int star;
    }
}
