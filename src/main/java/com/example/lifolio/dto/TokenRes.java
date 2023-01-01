package com.example.lifolio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRes {
    private Long userId; //user 인덱스
    private String accessToken;
}
