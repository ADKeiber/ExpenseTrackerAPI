package com.adk.expensetracker.service;

import com.adk.expensetracker.model.Role;
import com.adk.expensetracker.model.User;

import java.util.List;

public interface IUserService {
	public User createUser(User user, List<String> roles);
	public User readUser(String userId);
	public User updateUserPassword(User user);
	public User deleteUserById(String userId);
	public User deleteUser(User user);
	public Boolean validateUser(User user);
}
