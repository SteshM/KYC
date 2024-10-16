package com.example.KYC.dtos.RequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyOtpDTO {
    @NotNull(message = "otp field is required")
    private String otp;
}

