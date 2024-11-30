package com.adk.expensetracker.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.adk.expensetracker.dto.AuthResponseDTO;
import com.adk.expensetracker.dto.LoginDTO;
import com.adk.expensetracker.dto.RegisterDTO;
import com.adk.expensetracker.model.Role;
import com.adk.expensetracker.repo.RoleRepo;
import com.adk.expensetracker.security.JWTGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adk.expensetracker.errorhandling.EntityNotFoundException;
import com.adk.expensetracker.errorhandling.FieldBlankException;
import com.adk.expensetracker.errorhandling.UsernameAlreadyExistsException;
import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link IUserService}
 */
@Service @RequiredArgsConstructor @Slf4j
public class UserService implements IUserService {
	
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepo roleRepo;
	private final AuthenticationManager authenticationManager;
	private final JWTGenerator jwtGenerator;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User createUser(RegisterDTO user, List<String> roles) {
		if( user.getEmail() == null || user.getEmail().isBlank())
			throw new FieldBlankException(User.class, "email", String.class.toString());
		if( user.getUsername() == null || user.getUsername().isBlank())
			throw new FieldBlankException(User.class, "username", String.class.toString());
		if( user.getPassword() == null || user.getPassword().isBlank())
			throw new FieldBlankException(User.class, "password", String.class.toString());

		if(userRepo.existsByUsername(user.getUsername()))
			throw new UsernameAlreadyExistsException(user.getUsername());
		List<Role> userRoles = new LinkedList<>();

		roles.forEach( role -> userRoles.add(roleRepo.findByValue(role).get()));
		User mappedRegisterDTO = user.mapToUser();
		mappedRegisterDTO.setRoles(userRoles);

		mappedRegisterDTO.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(mappedRegisterDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User readUser(String userId) {
		Optional<User> user = userRepo.findById(userId);
		if(user.isEmpty())
			throw new EntityNotFoundException(User.class, "ID", userId);
		return user.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User updateUser(String userId, RegisterDTO user) {
		//Checks required fields
		if( user.getEmail() == null || user.getEmail().isBlank())
			throw new FieldBlankException(User.class, "email", String.class.toString());
		if( user.getUsername() == null || user.getUsername().isBlank())
			throw new FieldBlankException(User.class, "username", String.class.toString());
		if( user.getPassword() == null || user.getPassword().isBlank())
			throw new FieldBlankException(User.class, "password", String.class.toString());
		Optional<User> foundUser = userRepo.findById(userId);
		//Verifies user Exists
		if(foundUser.isEmpty())
			throw new EntityNotFoundException(User.class, "id", userId);
		User retrievedUser = foundUser.get();
		retrievedUser.setUsername(user.getUsername());
		retrievedUser.setPassword(passwordEncoder.encode(user.getPassword()));
		retrievedUser.setEmail(user.getEmail());
        return userRepo.save(retrievedUser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User deleteUserById(String userId) {
		Optional<User> foundUser = userRepo.findById(userId);
		//Verifies user Exists
		if(foundUser.isEmpty())
			throw new EntityNotFoundException(User.class, "id", userId);
		userRepo.deleteById(userId);
		return foundUser.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthResponseDTO validateUser(LoginDTO user) {
		if(user.getUsername() == null || user.getUsername().isBlank())
			throw new FieldBlankException(User.class, "username", String.class.toString());
		if(user.getPassword() == null || user.getPassword().isBlank())
			throw new FieldBlankException(User.class, "password", String.class.toString());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new AuthResponseDTO(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User makeUserAdmin(String userId) {
		User returnedUser = readUser(userId);
		Optional<Role> returnedRole = roleRepo.findByValue("ADMIN");
		if(returnedRole.isEmpty())
			throw new EntityNotFoundException(Role.class, "value", "ADMIN");
		List<Role> roles = returnedUser.getRoles();
		roles.add(returnedRole.get());
		returnedUser.setRoles(roles);
		return userRepo.save(returnedUser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUserIdByUsername(String username) {
		Optional<User> user = userRepo.findByUsername(username);
		if(user.isEmpty())
			throw new EntityNotFoundException(User.class, "username", username);
		return user.get().getId();
	}
}
