package com.example.KYC.services;

import com.example.KYC.utils.RandomGenerator;
import org.springframework.stereotype.Service;

@Service
public class FTPService {
    //generate a random number
    public String generateFtp(){
        String ftp = RandomGenerator.generateChars(4);
        return ftp;
    }



}