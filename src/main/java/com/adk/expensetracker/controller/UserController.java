package com.adk.expensetracker.controller;

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

import com.adk.expensetracker.model.User;
import com.adk.expensetracker.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		System.out.println(user.toString());
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable String userId) {
		System.out.println(userId);
		return new ResponseEntity<>(userService.readUser(userId), HttpStatus.OK);
	}
	
	@GetMapping("/validate")
	public ResponseEntity<Object> validateuser(@RequestBody User user) {
		return new ResponseEntity<>(userService.validateUser(user), HttpStatus.OK);
	}
	
	@PostMapping("/updatePassword")
	public ResponseEntity<Object> updateUserPassword(@RequestBody User user) {
		System.out.println(user.toString());
		return new ResponseEntity<>(userService.updateUserPassword(user), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Object> deleteUserById(@PathVariable String userId) {
		return new ResponseEntity<>(userService.deleteUserById(userId), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteUserById(@RequestBody User user) {
		return new ResponseEntity<>(userService.deleteUser(user), HttpStatus.OK);
	}
	
}
