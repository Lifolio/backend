package com.example.lifolio.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetBadgeRes {
    private String url;
    private String title;
    private int success;
}
