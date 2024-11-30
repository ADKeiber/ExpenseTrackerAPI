package com.adk.expensetracker.controller;

import com.adk.expensetracker.dto.AuthResponseDTO;
import com.adk.expensetracker.dto.LoginDTO;
import com.adk.expensetracker.dto.RegisterDTO;
import com.adk.expensetracker.dto.UserDTO;
import com.adk.expensetracker.errorhandling.ApiError;
import com.adk.expensetracker.util.DTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	@Operation(summary = "Create a new User", description = "Creates a new user by taking in a JSON RegisterDTO Object in the request body. If required fields are blank/null inside of the request body an API Error will be returned. " +
			"Required fields: email, username, password ", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674b3a2f7f637a4fbadaadfa\",\n" +
							"    \"username\": \"test\",\n" +
							"    \"roles\": [\n" +
							"        \"USER\"\n" +
							"    ]\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
							+ "        \"status\": \"BAD_REQUEST\",\r\n"
							+ "        \"timestamp\": \"11-11-2024 02:25:54\",\r\n"
							+ "        \"message\": \"One of the Required fields was missing for the passed in entity!\",\r\n"
							+ "        \"debugMessage\": \"User was missing value of field 'username' which is of class java.lang.String\"\r\n"
							+ "    }\r\n" + "}") })),
			@ApiResponse(description = "Bad Request/ User with username already exists", responseCode = "409", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"CONFLICT\",\n" +
							"        \"timestamp\": \"30-11-2024 11:12:11\",\n" +
							"        \"message\": \"The username 'admin' exists already!\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody RegisterDTO user) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.createUser(user, Arrays.asList(new String[]{"USER"}))), HttpStatus.OK);
	}

	/**
	 * Gets a user by its id
	 * @param userId {@link String} id of the user
	 * @return {@link ResponseEntity} containing UserDTO that has the user id if no api errors are thrown
	 */
	@Operation(summary = "Get an existing User", description = "Gets an Existing user by taking in a JSON RegisterDTO Object in the request body.", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674b3a2f7f637a4fbadaadfa\",\n" +
							"    \"username\": \"test\",\n" +
							"    \"roles\": [\n" +
							"        \"USER\"\n" +
							"    ]\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ No User with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:26:56\",\n" +
							"        \"message\": \"User was not found for parameters {ID=67456091f5f7ca5c0e67209f}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@GetMapping("/get/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.readUser(userId)), HttpStatus.OK);
	}

	/**
	 * Attempts to log in a user and receive a JWT token
	 * @param user {@link LoginDTO} user log in information
	 * @return {@link ResponseEntity} containing {@link com.adk.expensetracker.dto.AuthResponseDTO} of the authenticated user if no api errors are thrown
	 */
	@Operation(summary = "Validates a User by logging in", description = "Validates an existing user and retrieves a JWT authentication token.", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = AuthResponseDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"accessToken\": \"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzMyOTgzOTg5LCJleHAiOjE3MzI5ODc1ODl9.Mo5lYvRR1qWUyMv15ZbWTzIHviGgVvCmPb6iQlozDCcQ9iiPBivOBkyDqr8HZmfxlZ2r_6oQK-mOpu-ZNo0fAg\",\n" +
							"    \"tokenType\": \"Bearer \"\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
							+ "        \"status\": \"BAD_REQUEST\",\r\n"
							+ "        \"timestamp\": \"11-11-2024 02:25:54\",\r\n"
							+ "        \"message\": \"One of the Required fields was missing for the passed in entity!\",\r\n"
							+ "        \"debugMessage\": \"User was missing value of field 'username' which is of class java.lang.String\"\r\n"
							+ "    }\r\n" + "}") })),})
	@PostMapping("/login")
	public ResponseEntity<Object> validateUser(@RequestBody LoginDTO user) {
		return new ResponseEntity<>(userService.validateUser(user), HttpStatus.OK);
	}

	/**
	 * Updates a user's account
	 * @param userId {@link String} id of the user
	 * @param user {@link RegisterDTO} information of the updated User
	 * @return {@link ResponseEntity} containing UserDTO of the updated user if no api errors are thrown
	 */
	@Operation(summary = "Update a User's account", description = "Updates an existing user by taking in a JSON RegisterDTO Object in the request body and user Id as a request parameter. If required fields are blank/null inside of the request body an API Error will be returned. " +
			"Required fields: email, username, password ", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674b3a2f7f637a4fbadaadfa\",\n" +
							"    \"username\": \"user123\",\n" +
							"    \"roles\": [\n" +
							"        \"USER\"\n" +
							"    ]\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"BAD_REQUEST\",\n" +
							"        \"timestamp\": \"30-11-2024 11:41:18\",\n" +
							"        \"message\": \"One of the Required fields was missing for the passed in entity!\",\n" +
							"        \"debugMessage\": \"User was missing value of field 'email' which is of class java.lang.String\"\n" +
							"    }\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:42:09\",\n" +
							"        \"message\": \"User was not found for parameters {id=674b3a2f7f637a4fbadaadf}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@PostMapping("/update/{userId}")
	public ResponseEntity<Object> updateUserPassword(@PathVariable String userId, @RequestBody RegisterDTO user) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.updateUser(userId, user)), HttpStatus.OK);
	}

	/**
	 * Deletes a user
	 * @param userId {@link String} id of the user
	 * @return {@link ResponseEntity} containing UserDTO of deleted user if no api errors are thrown
	 */
	@Operation(summary = "Deletes a user By its ID", description = "Deletes a user by the passed in User ID", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674b3a2f7f637a4fbadaadfa\",\n" +
							"    \"username\": \"user123\",\n" +
							"    \"roles\": [\n" +
							"        \"USER\"\n" +
							"    ]\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Object> deleteUserById(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.deleteUserById(userId)), HttpStatus.OK);
	}

	/**
	 * Makes a user into an admin
	 * @param userId {@link String} id of the user
	 * {@link ResponseEntity} containing UserDTO of the user who was made into an admin if no api errors are thrown
	 */
	@Operation(summary = "Grant Admin Authorization to a User", description = "Grants Admin Authorization to a User based on its user ID", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674b41c77f637a4fbadaadfb\",\n" +
							"    \"username\": \"test\",\n" +
							"    \"roles\": [\n" +
							"        \"USER\",\n" +
							"        \"ADMIN\"\n" +
							"    ]\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@PostMapping("/makeAdmin/{userId}")
	public ResponseEntity<Object> makeUserAdmin(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToUserDTO(userService.makeUserAdmin(userId)), HttpStatus.OK);
	}

	/**
	 * Retrieves a User ID by the user's username
	 * @param username {@link String} the username of the user
	 * @return {@link String} the user ID
	 */
	@Operation(summary = "Get an User's ID", description = "Get an existing User's Id by its username", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = String.class), examples = {
					@ExampleObject(value = "674b41c77f637a4fbadaadfb") })),
			@ApiResponse(description = "Bad Request/ No User with that Username", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:26:56\",\n" +
							"        \"message\": \"User was not found for parameters {username=testUser}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@GetMapping("/getByUsername/{username}")
	public ResponseEntity<Object> getUserIdByUsername(@PathVariable String username) {
		return new ResponseEntity<>(userService.getUserIdByUsername(username), HttpStatus.OK);
	}
}
