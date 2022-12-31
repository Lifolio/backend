package com.example.lifolio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRes {
    private Long userId; //아이디 == username
    private String accessToken;
}
