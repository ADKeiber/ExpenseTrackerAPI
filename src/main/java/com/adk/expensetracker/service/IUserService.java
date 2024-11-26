package com.adk.expensetracker.service;

import com.adk.expensetracker.model.User;

public interface IUserService {
	public User createUser(User user);
	public User readUser(String userId);
	public User updateUser(User user);
	public User deleteUserById(String userId);
	public User deleteUser(User user);
}
