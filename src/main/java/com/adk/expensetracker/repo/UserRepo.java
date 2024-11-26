package com.adk.expensetracker.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.adk.expensetracker.model.User;

public interface UserRepo extends MongoRepository<User, String> {	
	User findByUsernameText(String username);
}