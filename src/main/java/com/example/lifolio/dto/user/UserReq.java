package com.example.lifolio.dto.user;

import com.example.lifolio.entity.User;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

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
    public static class PostReIssueReq {
        private Long userId;
        private String refreshToken;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupUserReq {

        private String username;

        //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String password;

        private String nickname;

        private String name;

        private String phone;


        //이건 requestBody 입력할 때, 입력할 필요 없음
        private Set<UserRes.AuthorityRes> authorityResSet;

        public static SignupUserReq from(User user) {
            if(user == null) return null;

            return SignupUserReq.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .nickname(user.getNickname())
                    .name(user.getName())
                    .name(user.getPhone())
                    .authorityResSet(user.getAuthorities().stream()
                            .map(authority -> UserRes.AuthorityRes.builder().authorityName(authority.getAuthorityName()).build())
                            .collect(Collectors.toSet()))
                    .build();
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FindUserIdReq {
        private String name;
        private String phone;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class KakaoLoginReq {
        private String accessToken;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginUserReq {

        private String username;

        private String password;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PasswordReq {
        //private String name; //이름
        private String username; //아이디
        private String newPassword; //새로 저장할 비밀번호
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LogInTokenReq {
        private String token;
    }
}
