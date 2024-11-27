package com.adk.expensetracker.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adk.expensetracker.model.Category;

public interface CategoryRepo extends MongoRepository<Category, String>{
	Optional<Category> findByName(String name);
}
