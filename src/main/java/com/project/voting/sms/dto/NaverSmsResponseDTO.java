package com.project.voting.sms.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NaverSmsResponseDTO {
    String requestId;
    LocalDateTime requestTime;
    String statusCode;
    String statusName;
    String verificationCode;

    @Builder
    public NaverSmsResponseDTO(String verificationCode){
        this.verificationCode = verificationCode;
    }
}
