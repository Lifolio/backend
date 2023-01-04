package com.example.lifolio.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetHomeRes {
    private TopInfo topInfo;
    private List<GraphLifolio> graphLifolio;
    private List<CustomUserLifolioRes> customLifolio;
}
