package com.adk.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO that contains the information of a user
 */
@Data
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private List<String> roles;
}
