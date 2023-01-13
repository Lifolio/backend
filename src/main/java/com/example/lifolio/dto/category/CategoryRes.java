package com.example.lifolio.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CategoryRes {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CategoryList{
        private String category;
    }
}
