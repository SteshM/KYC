package com.example.KYC.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {
    public static String username(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}