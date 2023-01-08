package com.example.lifolio.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserReq {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class SocialReq{
        private String social;
        private String accessToken;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class PostSocialReq {
    }
}
