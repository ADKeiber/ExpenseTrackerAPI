package com.adk.expensetracker.service;

import java.time.LocalDateTime;
import java.util.List;

import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.model.Expense;

public interface IExpenseService {
	public Expense createExpense(String userId, Expense expense);
	public Expense readExpense(String expenseId);
	public List<Expense> readExpensesForUser(String userId);
	public List<Expense> readExpenseForUserByCategory(String userId, Category category); //TODO implement
	public List<Expense> readExpensesWithDateRange(LocalDateTime startDate, LocalDateTime endDate, String userId);
	public Expense updateExpense(String expenseId, Expense expense);
	public Expense deleteExpense(String expenseId);
	public Category checkAndAddCategory(Category category);
}
 