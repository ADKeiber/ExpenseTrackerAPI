package com.adk.expensetracker.dto;

import com.adk.expensetracker.model.User;
import lombok.Data;

/**
 * DTO that contains the information of a new user
 */
@Data
public class RegisterDTO {
    private String email;
    private String username;
    private String password;

    public User mapToUser(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
