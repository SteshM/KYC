package com.example.KYC.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Component
public class OtpGenerator {
    public static String generate6DigitsOtp(){
        SecureRandom r = new SecureRandom();
        int low = 100000;
        int high = 999999;
        int result = r.nextInt(high-low) + low;

        return String.valueOf(result);
    }
}
