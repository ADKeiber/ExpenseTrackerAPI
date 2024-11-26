package com.adk.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.UserRepo;
import com.adk.expensetracker.service.UserService;

@RestController
public class MainController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/addUser")
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		System.out.println(user.toString());
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
	}
}
