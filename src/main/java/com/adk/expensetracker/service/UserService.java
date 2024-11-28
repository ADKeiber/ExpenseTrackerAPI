package com.adk.expensetracker.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.adk.expensetracker.dto.AuthResponseDTO;
import com.adk.expensetracker.dto.LoginDTO;
import com.adk.expensetracker.dto.RegisterDTO;
import com.adk.expensetracker.model.Role;
import com.adk.expensetracker.repo.RoleRepo;
import com.adk.expensetracker.security.JWTGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserService implements IUserService, UserDetailsService {
	
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepo roleRepo;
	private final AuthenticationManager authenticationManager;
	private final JWTGenerator jwtGenerator;

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

		roles.forEach( role -> {
			userRoles.add(roleRepo.findByValue(role).get());
		});
		User mappedRegisterDTO = user.mapToUser();
		mappedRegisterDTO.setRoles(userRoles);

		mappedRegisterDTO.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(mappedRegisterDTO);
	}

	@Override
	public User readUser(String userId) {
		Optional<User> user = userRepo.findById(userId);
		if(user.isEmpty())
			throw new EntityNotFoundException(User.class, "ID", userId);
		return user.get();
	}


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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getValue())).collect(Collectors.toList());
	}
}
