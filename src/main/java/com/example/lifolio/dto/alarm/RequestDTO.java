package com.example.lifolio.dto.alarm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO {
    private String targetToken;
    private String title;
    private String body;
}
