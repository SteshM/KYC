package com.example.KYC.dtos.RequestDto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SmsRequestDTO {
    private String to;
    private String message;
    private String from;
    private String transactionID;
    private String clientid;
    private String password;
    private String username;
}