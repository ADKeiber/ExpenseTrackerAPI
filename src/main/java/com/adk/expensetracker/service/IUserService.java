package com.adk.expensetracker.service;

import com.adk.expensetracker.dto.AuthResponseDTO;
import com.adk.expensetracker.dto.LoginDTO;
import com.adk.expensetracker.dto.RegisterDTO;
import com.adk.expensetracker.model.User;

import java.util.List;

public interface IUserService {
	public User createUser(RegisterDTO user, List<String> roles);
	public User readUser(String userId);
	public User updateUser(String userId, RegisterDTO user);
	public User deleteUserById(String userId);
	public AuthResponseDTO validateUser(LoginDTO user);
}
