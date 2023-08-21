package com.project.voting.sms.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
    private String smsConfirmNum;

    @Builder
    public SmsResponseDto (String smsConfirmNum){
        this.smsConfirmNum = smsConfirmNum;
    }
}