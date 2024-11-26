package com.adk.expensetracker.errorhandling;

/**
 * Field Blank Exception used when passed in data is missing a required field
 */
public class IncorrectPasswordException extends RuntimeException {
	
	private static final long serialVersionUID = -8104227912438844281L;

	public IncorrectPasswordException(String username) {
        super("The username '" + username + "' exists but the password is incorrect!");
    }
}