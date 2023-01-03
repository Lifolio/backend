package com.example.lifolio.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserLifolioRes {
    private Long customId;
    private int concept;
    private String emoji;
    private String customName;
}
