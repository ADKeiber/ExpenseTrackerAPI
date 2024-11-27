package com.adk.expensetracker.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.adk.expensetracker.model.User;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
}