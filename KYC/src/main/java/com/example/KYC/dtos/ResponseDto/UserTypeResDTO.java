package com.example.KYC.dtos.ResponseDto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTypeResDTO {
    private long userTypeId;
    private String userType;
}