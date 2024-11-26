package com.adk.expensetracker.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adk.expensetracker.errorhandling.EntityNotFoundException;
import com.adk.expensetracker.errorhandling.FieldBlankException;
import com.adk.expensetracker.errorhandling.UsernameAlreadyExistsException;
import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class UserService implements IUserService {
	
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	

	@Override
	public User createUser(User user) {
		
		if( user.getUsername() == null || user.getUsername().isBlank())
			throw new FieldBlankException(User.class, "username", String.class.toString());
		if( user.getPassword() == null || user.getPassword().isBlank())
			throw new FieldBlankException(User.class, "password", String.class.toString());
		
		User userWithUsername = userRepo.findByUsername(user.getUsername());
		if(userWithUsername != null)
			throw new UsernameAlreadyExistsException(user.getUsername());
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User returnedUser = userRepo.save(user);
		
		return returnedUser;
	}

	@Override
	public User readUser(String userId) {
		Optional<User> user = userRepo.findById(userId);
		
		//Verifies user Exists
		if(user.isEmpty())
			throw new EntityNotFoundException(User.class, "ID", userId);
		
		return user.get();
	}

	@Override
	public User updateUserPassword(User user) {
		//Checks required fields
//		if( user.getId() == null || user.getId().isBlank())
//			throw new FieldBlankException(User.class, "id", String.class.toString());
		if( user.getUsername() == null || user.getUsername().isBlank())
			throw new FieldBlankException(User.class, "username", String.class.toString());
		if( user.getPassword() == null || user.getPassword().isBlank())
			throw new FieldBlankException(User.class, "password", String.class.toString());
		
		User foundUser = userRepo.findByUsername(user.getUsername());
		
		//Verifies user Exists
		if(foundUser == null)
			throw new EntityNotFoundException(User.class, "id", user.getId());
		foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userRepo.save(foundUser);
		return savedUser;
	}

	@Override
	public User deleteUserById(String userId) {
		
		Optional<User> foundUser = userRepo.findById(userId);
		
		//Verifies user Exists
		if(foundUser.isEmpty())
			throw new EntityNotFoundException(User.class, "id", userId);
		
		userRepo.deleteById(userId);
		
		return foundUser.get();
	}

	@Override
	public User deleteUser(User user) {
		//Verifies an id was passed in
		if( user.getId() == null || user.getId().isBlank())
			throw new FieldBlankException(User.class, "id", String.class.toString());
		
		Optional<User> foundUser = userRepo.findById(user.getId());
		
		//Verifies user Exists
		if(foundUser.isEmpty())
			throw new EntityNotFoundException(User.class, "id", user.getId());
		
		userRepo.delete(user);
		
		return user;
	}

}
