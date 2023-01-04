package com.example.lifolio.dto.home;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomUpdateReq {
    private String emoji; //이모지
    private String customName; //커스텀폴리오 이름
    private Long category; //카테고리
    private int concept; //뷰 컨셉
}
