package com.example.lifolio.dto.category;


import lombok.*;

public class CategoryReq {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class UpdateCategoryReq {
        private Long userId;
        private Long colorId;
        private String title;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class AddCategoryReq {
        private Long colorId;
        private String title;
//        private String branch;
//        private String level;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class CategoryToSubReq {
        private Long id;
        private String title;
    }
}
