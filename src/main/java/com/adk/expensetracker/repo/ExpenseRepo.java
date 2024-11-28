package com.adk.expensetracker.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adk.expensetracker.model.Expense;
import org.springframework.data.mongodb.repository.Query;

public interface ExpenseRepo extends MongoRepository<Expense, String> {	
	List<Expense> findByUserId(String userId);
	@Query(value = "{ 'user.id': ?0,  'category' : ?1 }")
	List<Expense> findByUserIdAndCategoryName(String userId, String categoryName);
	@Query(value = "{ 'user.id': ?0,  'date' : { $gt :  ?1, $lt : ?2} }")
	List<Expense> findByUserIdAndBetweenTwoDates(String userId, LocalDateTime startDate, LocalDateTime endDate);
}