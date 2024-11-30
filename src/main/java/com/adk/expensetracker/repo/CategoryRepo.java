package com.adk.expensetracker.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adk.expensetracker.model.Category;

/**
 * MongoDB repository holding Expense Categories
 */
public interface CategoryRepo extends MongoRepository<Category, String>{

	/**
	 * Finds a category by its name
	 * @param name {@link String} the name of the category
	 * @return {@link Optional} of {@link Category} of the category where the name is equal to the passed in value
	 */
	Optional<Category> findByName(String name);
}
