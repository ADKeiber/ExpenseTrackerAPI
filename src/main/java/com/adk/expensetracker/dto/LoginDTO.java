package com.adk.expensetracker.dto;

import com.adk.expensetracker.model.User;
import lombok.Data;

/**
 * DTO that contains the information of login credentials
 */
@Data
public class LoginDTO {
    private String username;
    private String password;
}
