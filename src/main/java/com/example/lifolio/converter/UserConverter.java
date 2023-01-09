package com.example.lifolio.converter;


import com.example.lifolio.entity.Authority;
import com.example.lifolio.entity.User;

import java.util.Collections;

public class UserConverter {

    public static User postUser(String id, String social, String name) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        return User.builder().
                username(id).
                name(name).
                nickname(id).
                social(social).
                authorities(Collections.singleton(authority))
                .activated(true).
                build();

    }

}
