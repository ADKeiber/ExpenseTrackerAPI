package com.adk.expensetracker.dto;

import com.adk.expensetracker.model.User;
import lombok.Data;

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
