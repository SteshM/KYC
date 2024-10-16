package com.example.KYC.dtos.RequestDto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletDTO {
    private List prefix;
    private String phone_number;
    private String currency;
    private String customer_names;
    private String host;
    private String channel_key;
    private String channel;
    private String client_id;
    private String geolocation;
    private String user_agent;
    private String user_agent_version;
    private String txntimestamp;

}