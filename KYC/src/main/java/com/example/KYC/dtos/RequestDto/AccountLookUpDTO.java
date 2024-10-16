package com.example.KYC.dtos.RequestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountLookUpDTO {
    @NotNull(message = "phoneNo cannot be null!")
    @Size(max = 10,message = "The phone number should not exceed 10 characters")
    private String phoneNo;

}
