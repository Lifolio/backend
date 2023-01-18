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
        private Long categoryId;
        private Long colorId;
        private String title;
    }
}
