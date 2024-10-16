package com.example.KYC.dtos.RequestDto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class UserDTO {
    @NotNull(message = "fullName cannot be null")
    private String fullName;
    @NotNull(message = "email cannot be null")
    @Column(unique = true)
    private String email;
    @NotNull(message = "phoneNo cannot be null")
    @Size(max = 12 , message = "PhoneNo must not exceed 12 characters")
    @Pattern(regexp ="^254\\d{9}$",message = "Phone number should start with 254")
    private String phoneNumber;
    @NotNull(message = "nationalId cannot be null")
    @Column(unique = true)
    private String nationalId;
    @NotNull(message = "gender cannot be null")
    private String gender;
    @NotNull(message = "dateOfBirth cannot be null")
    private Date dateOfBirth;
    @NotNull(message = "nationality cannot be null")
    private String nationality;
    @NotNull(message = "userType cannot be null")
    private String userType;

}
