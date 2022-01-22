package com.brunopbrito31.apilivros.models.tools;

import java.math.BigDecimal;

public class Auxiliar {

    public static void verifyPositiveNonNullNumberIntegrity(BigDecimal number){
        if(number.compareTo(BigDecimal.valueOf(0)) < 0){
            throw new IllegalArgumentException("Invalid Value!");
        }   
    }

    public static void verifyPositiveNumberIntegrity(BigDecimal number){
        if(number.compareTo(BigDecimal.valueOf(0)) <= 0){
            throw new IllegalArgumentException("Invalid Value!");
        }   
    }
    
}
