package com.adk.expensetracker.util;

import com.adk.expensetracker.dto.UserDTO;
import com.adk.expensetracker.model.User;

import java.util.LinkedList;
import java.util.List;

public class DTOMapper {

    public static UserDTO mapToUserDTO(User user){
        List<String> roleName = new LinkedList<>();
        user.getRoles().forEach(role -> roleName.add(role.getValue()));
        return new UserDTO(user.getId(), user.getUsername(), roleName);
    }
}
