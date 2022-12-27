package com.example.lifolio.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserReq {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
