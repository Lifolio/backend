package com.example.lifolio.converter;

import com.example.lifolio.entity.User;

public class UserConverter {

    public static User postUser(String id, String social, String name){
        return User.builder().username(id).name(name).nickname(id).social(social).build();
    }
}
