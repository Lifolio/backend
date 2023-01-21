package com.example.lifolio.dto.category;

import lombok.*;

public class SubCategoryReq {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class UpdateSubCategoryReq {
        private Long categoryId;
        private String title;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class AddSubCategoryReq {
        private Long categoryId;
        private String title;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class MoveSubCategoryReq {
        private Long categoryId;
        private Long colorId;
        private String title;
    }
}
