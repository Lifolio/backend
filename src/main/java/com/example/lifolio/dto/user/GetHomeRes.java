package com.example.lifolio.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetHomeRes {
    private TopInfo topInfo;
    private List<MainLifolio> mainLifolio;
    private List<CustomUserLifolioRes> customLifolio;
}
