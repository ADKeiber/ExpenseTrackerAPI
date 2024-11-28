package com.adk.expensetracker.service;

import java.time.LocalDateTime;
import java.util.List;

import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.model.Expense;

public interface IExpenseService {
	Expense createExpense(String userId, Expense expense);
	Expense readExpense(String expenseId);
	List<Expense> readExpensesForUser(String userId);
	List<Expense> readExpenseForUserByCategory(String userId, String categoryName);
	List<Expense> readExpensesWithDateRange(LocalDateTime startDate, LocalDateTime endDate, String userId);
	Expense updateExpense(String expenseId, Expense expense);
	Expense deleteExpense(String expenseId);
	Category checkAndAddCategory(Category category);
}
 