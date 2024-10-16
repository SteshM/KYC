package com.example.KYC.utils;


import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class RandomGenerator {

    static Random random = new Random();
    static String choices = "0123456789";
    public static String generateChars(int length){
        StringBuilder chars= new StringBuilder();
        for(int i = 0; i< length; i++){
            chars.append(choices.charAt(random.nextInt(choices.length())));
        }
        return chars.toString();
    }

}

