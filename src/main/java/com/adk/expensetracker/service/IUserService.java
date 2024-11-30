package com.adk.expensetracker.service;

import com.adk.expensetracker.dto.AuthResponseDTO;
import com.adk.expensetracker.dto.LoginDTO;
import com.adk.expensetracker.dto.RegisterDTO;
import com.adk.expensetracker.model.User;

import java.util.List;

/**
 * Interface containing all methods needed to interace with a user repository
 */
public interface IUserService {

	/**
	 * Crates a new user
	 * @param user {@link RegisterDTO} the information required to create a user
	 * @param roles {@link List} of {@link String} containing role names
	 * @return {@link User} the newly created user
	 */
	public User createUser(RegisterDTO user, List<String> roles);

	/**
	 * Read/retrieves a user by its user id
	 * @param userId {@link String} id of the user
	 * @return {@link User} that contains the user id
	 */
	public User readUser(String userId);

	/**
	 * Updates a user with the given ID with the passed in information
	 * @param userId {@link String} id of the user
	 * @param user {@link RegisterDTO} the information to update the user information to
	 * @return {@link User} the updated user
	 */
	public User updateUser(String userId, RegisterDTO user);

	/**
	 * Deletes a user by its id
	 * @param userId {@link String} id of the user
	 * @return {@link User} the deleted user
	 */
	public User deleteUserById(String userId);

	/**
	 * Validates a user
	 * @param user {@link LoginDTO} the login information for a user
	 * @return {@link AuthResponseDTO} of the jwt token
	 */
	public AuthResponseDTO validateUser(LoginDTO user);
}
