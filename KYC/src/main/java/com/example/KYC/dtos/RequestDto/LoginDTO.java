package com.example.KYC.dtos.RequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
    @NotNull(message = "username is required")
    private String username;
    @NotNull(message = "password is mandatory!")
    private String password;
}

