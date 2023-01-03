package com.example.lifolio.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoLoginRes {
    private long userId;
    private String nickname;
    private String profileImgUrl;
    private String type;
}
