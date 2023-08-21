package com.project.voting.sms.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class SmsCertificationRequestDTO {
    String phoneNumber;
    String code;
}
