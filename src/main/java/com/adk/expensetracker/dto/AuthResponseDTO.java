package com.adk.expensetracker.dto;

import lombok.Data;

/**
 *  DTO that Contains an JWT access token and its token type
 */
@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
