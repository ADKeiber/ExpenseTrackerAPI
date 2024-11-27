package com.adk.expensetracker.service;

import java.time.LocalDate;
import java.util.List;

import com.adk.expensetracker.model.Expense;

public interface IExpenseService {
	public Expense createExpense(Expense expense);
	public Expense readExpense(String expenseId);
	public List<Expense> readExpensesForUser(String userId);
	public List<Expense> readExpensesWithDateRange(LocalDate startDate, LocalDate endDate, String userId);
	public Expense updateExpense(String expenseId, Expense expense);
	public Expense deleteExpense(String expenseId);
}
 