package com.example.KYC.dtos.ResponseDto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    private long userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String nationalId;
    private String gender;
    private Date dateOfBirth;
    private String nationality;
    private String userType;

}