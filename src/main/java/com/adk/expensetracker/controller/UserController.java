package com.adk.expensetracker.controller;

import com.adk.expensetracker.dto.LoginDTO;
import com.adk.expensetracker.dto.RegisterDTO;
import com.adk.expensetracker.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adk.expensetracker.service.UserService;

import java.util.Arrays;

/**
 * API Endpoints that are used to create, read, update, and delete users
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Creates a new User
	 * @param user {@link RegisterDTO} the user to create
	 * @return {@link ResponseEntity} containing UserDTO of the newly created user if no api errors are thrown
	 */
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody RegisterDTO user) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.createUser(user, Arrays.asList(new String[]{"USER"}))), HttpStatus.OK);
	}

	/**
	 * Gets a user by its id
	 * @param userId {@link String} id of the user
	 * @return {@link ResponseEntity} containing UserDTO that has the user id if no api errors are thrown
	 */
	@GetMapping("/get/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.readUser(userId)), HttpStatus.OK);
	}

	/**
	 * Attempts to log in a user and receive a JWT token
	 * @param user {@link LoginDTO} user log in information
	 * @return {@link ResponseEntity} containing {@link com.adk.expensetracker.dto.AuthResponseDTO} of the authenticated user if no api errors are thrown
	 */
	@PostMapping("/login")
	public ResponseEntity<Object> validateUser(@RequestBody LoginDTO user) {
		return new ResponseEntity<>(userService.validateUser(user), HttpStatus.OK);
	}

	/**
	 * Updates a user's password
	 * @param userId {@link String} id of the user
	 * @param user {@link RegisterDTO} information of the updated User
	 * @return {@link ResponseEntity} containing UserDTO of the updated user if no api errors are thrown
	 */
	@PostMapping("/update/{userId}")
	public ResponseEntity<Object> updateUserPassword(@PathVariable String userId, @RequestBody RegisterDTO user) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.updateUser(userId, user)), HttpStatus.OK);
	}

	/**
	 * Deletes a user
	 * @param userId {@link String} id of the user
	 * @return {@link ResponseEntity} containing UserDTO of deleted user if no api errors are thrown
	 */
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Object> deleteUserById(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.deleteUserById(userId)), HttpStatus.OK);
	}

	/**
	 * Makes a user into an admin
	 * @param userId {@link String} id of the user
	 * {@link ResponseEntity} containing UserDTO of the user who was made into an admin if no api errors are thrown
	 */
	@PostMapping("/makeAdmin/{userId}")
	public ResponseEntity<Object> makeUserAdmin(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.makeUserAdmin(userId)), HttpStatus.OK);
	}
}
