package com.example.lifolio.dto;


import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PasswordReq {
    private String name; //이름
    private String username; //아이디
    private String newPassword; //새로 저장할 비밀번호
}
