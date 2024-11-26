package com.adk.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.UserRepo;

@RestController
public class MainController {
	
	@Autowired
	UserRepo userRepo;
	
	@PostMapping("/addUser")
	public void addUser(@RequestBody User user) {
		System.out.println(user.toString());
		userRepo.save(user);
	}
}
