package com.example.lifolio.dto;

import com.example.lifolio.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupUserReq {

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String nickname;


    //이건 requestBody 입력할 때, 입력할 필요 없음
    private Set<AuthorityRes> authorityResSet;

    public static SignupUserReq from(User user) {
        if(user == null) return null;

        return SignupUserReq.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityResSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityRes.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

}
