package com.example.lifolio.dto.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MyReq {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class FilterCategory{
        private int order;
        private List<String> categoryList;
    }
}
