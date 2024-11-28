package com.adk.expensetracker.dto;

import com.adk.expensetracker.model.User;
import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;

    public User mapToUser(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
