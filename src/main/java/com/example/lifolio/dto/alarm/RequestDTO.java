package com.example.lifolio.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestDTO {
    private String targetToken;
    private String title;
    private String body;
}
