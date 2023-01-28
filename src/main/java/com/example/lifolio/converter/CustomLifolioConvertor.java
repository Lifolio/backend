package com.example.lifolio.converter;

import com.example.lifolio.dto.home.HomeReq;
import com.example.lifolio.entity.CustomLifolio;

public class CustomLifolioConvertor {
    public static CustomLifolio PostCustomLifolio(Long userId, HomeReq.CustomUpdateReq customUpdateReq) {
        return CustomLifolio.builder()
                .categoryId(customUpdateReq.getCategory())
                .title(customUpdateReq.getCustomName())
                .emoji(customUpdateReq.getEmoji())
                .concept(customUpdateReq.getConcept())
                .userId(userId).build();
    }
}
