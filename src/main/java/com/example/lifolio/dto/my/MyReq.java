package com.example.lifolio.dto.my;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PostMyLifolioReq {
        private String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate start_date;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate end_date;

        private List<String> img;
        private Long category_id;

        private String content;

        private BigDecimal latitude;

        private BigDecimal longitude;
        private Integer star;

    }
}
