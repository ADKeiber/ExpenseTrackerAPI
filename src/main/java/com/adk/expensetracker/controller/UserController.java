package com.adk.expensetracker.controller;

import com.adk.expensetracker.dto.AuthResponseDTO;
import com.adk.expensetracker.dto.LoginDTO;
import com.adk.expensetracker.dto.RegisterDTO;
import com.adk.expensetracker.dto.UserDTO;
import com.adk.expensetracker.model.Role;
import com.adk.expensetracker.repo.RoleRepo;
import com.adk.expensetracker.repo.UserRepo;
import com.adk.expensetracker.security.JWTGenerator;
import com.adk.expensetracker.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adk.expensetracker.model.User;
import com.adk.expensetracker.service.UserService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody RegisterDTO user) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.createUser(user, Arrays.asList(new String[]{"USER"}))), HttpStatus.OK);
	}

	@GetMapping("/get/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.readUser(userId)), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> validateUser(@RequestBody LoginDTO user) {
		return new ResponseEntity<>(userService.validateUser(user), HttpStatus.OK);
	}
	
	@PostMapping("/update/{userId}")
	public ResponseEntity<Object> updateUserPassword(@PathVariable String userId, @RequestBody RegisterDTO user) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.updateUser(userId, user)), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Object> deleteUserById(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.deleteUserById(userId)), HttpStatus.OK);
	}
}
