package com.example.KYC.security;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
public class CustomUserDetails {
    private String username;
    private String password;

    private Collection<SimpleGrantedAuthority> authorities;

}