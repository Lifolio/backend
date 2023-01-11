package com.example.lifolio.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRes {
    private Long userId; //user 인덱스
    private String accessToken;
    private String refreshToken;
    private String name; //실제 유저 이름
}
