package com.adk.expensetracker.repo;

import com.adk.expensetracker.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.adk.expensetracker.model.User;

import java.util.Optional;

/**
 * MongoDB repository holding user information
 */
public interface UserRepo extends MongoRepository<User, String> {

	/**
	 * Finds a user by its username
	 * @param username {@link String} the username of the user
	 * @return {@link Optional} of {@link Category} of the user where the username is equal to the passed in value
	 */
	Optional<User> findByUsername(String username);

	/**
	 * Determines if a user exist with passed in username
	 * @param username {@link String} the username of the user
	 * @return {@code true} if user is found with passed in username, {@code false} otherwise
	 */
	Boolean existsByUsername(String username);
}