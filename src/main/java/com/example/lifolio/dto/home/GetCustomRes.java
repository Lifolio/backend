package com.example.lifolio.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCustomRes {
    private Long folioId;
    private String title;
    private String url;
}
