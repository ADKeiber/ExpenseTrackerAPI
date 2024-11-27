package com.adk.expensetracker.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adk.expensetracker.model.Expense;

public interface ExpenseRepo extends MongoRepository<Expense, String> {	
	List<Expense> findByUserId(String userId);
}