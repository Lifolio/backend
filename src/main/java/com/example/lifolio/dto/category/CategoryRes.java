package com.example.lifolio.dto.category;

import lombok.*;

import java.util.List;

public class CategoryRes {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CategoryList{
        private String category;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Category{
        private Long categoryId;
        private String categoryName;
        private String categoryColor;
        private List<SubCategory> subCategoryList;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class SubCategory {
        private Long subCategoryId;
        private String categoryName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CategoryIdTitle{
        private Long categoryId;
        private String categoryName;
    }


}
