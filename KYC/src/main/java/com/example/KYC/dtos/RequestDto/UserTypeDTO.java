package com.example.KYC.dtos.RequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserTypeDTO {
    @NotNull(message = "userTYpe cannot be null")
    private String userType;

}
