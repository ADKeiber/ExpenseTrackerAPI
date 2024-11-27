package com.adk.expensetracker.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.adk.expensetracker.model.Role;
import com.adk.expensetracker.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

	public Role createRole(Role r){
		Optional<Role> returnedRole = roleRepo.findByValue(r.getValue());
        return returnedRole.orElseGet(() -> roleRepo.save(r));
    }

	@Override
	public User createUser(User user, List<String> roles) {
		
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

		user.setRoles(userRoles);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
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
		if( user.getUsername() == null || user.getUsername().isBlank())
			throw new FieldBlankException(User.class, "username", String.class.toString());
		if( user.getPassword() == null || user.getPassword().isBlank())
			throw new FieldBlankException(User.class, "password", String.class.toString());
		
		User foundUser = userRepo.findByUsername(user.getUsername()).get();
		
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

	@Override //This will be updated to return a JWT later also update to throw an exception rather than return false
	public Boolean validateUser(User user) {
		
		if(user.getUsername() == null || user.getUsername().isBlank())
			throw new FieldBlankException(User.class, "username", String.class.toString());
		if(user.getPassword() == null || user.getPassword().isBlank())
			throw new FieldBlankException(User.class, "password", String.class.toString());
		User returnedUser = userRepo.findByUsername(user.getUsername()).get();
		
		//Verifies user exists then verifies the password (after being decoded) is the same as passed in password
		if(returnedUser == null){
			return false;
		} else if(!passwordEncoder.matches(user.getPassword(), returnedUser.getPassword())) {
			return false;
		}
		return true;
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
