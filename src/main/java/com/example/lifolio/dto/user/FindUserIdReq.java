package com.example.lifolio.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindUserIdReq {
    private String name;
    private String phone;
}
