package com.adk.expensetracker.service;

import java.time.LocalDateTime;
import java.util.List;

import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.model.Expense;

/**
 * Interface containing all the methods needed to interact with Expenses
 */
public interface IExpenseService {

	/**
	 * Creates a new expense
	 * @param userId {@link String} the username of the expense
	 * @param expense {@link Expense} the new expense to save
	 * @return {@link Expense} the saved expense
	 */
	Expense createExpense(String userId, Expense expense);

	/**
	 * Reads/retrieves an expense by its id
	 * @param expenseId {@link String} id of the String
	 * @return {@link Expense} the found expense
	 */
	Expense readExpense(String expenseId);

	/**
	 * Reads/retrieves expenses by its user id
	 * @param userId {@link String} id of the user attached to the expense
	 * @return {@link List} of {@link Expense} where expense's user id is equal to passed in user id
	 */
	List<Expense> readExpensesForUser(String userId);

	/**
	 * Reads/retrieves expenses by its user id and category name
	 * @param userId {@link String} id of the user attached to the expense
	 * @param categoryName {@link String} category name attached to the expense
	 * @return {@link List} of {@link Expense} where expense's user id is equal to passed in user id and expense's
	 * 		category.name is equal to passed in category name
	 */
	List<Expense> readExpenseForUserByCategory(String userId, String categoryName);

	/**
	 * Reads/retrieves expenses by its user id and its date is between start and end date
	 * @param startDate {@link LocalDateTime} the beginning range of acceptable dates on an expense
	 * @param endDate {@link LocalDateTime} the end range of acceptable dates on an expense
	 * @param userId {@link String} id of the user attached to the expense
	 * @return {@link List} of {@link Expense} where expense's user id is equal to passed in user id and expense's
	 *  		date is between the start and end dates
	 */
	List<Expense> readExpensesWithDateRange(LocalDateTime startDate, LocalDateTime endDate, String userId);

	/**
	 * Updates and expense by expense id
	 * @param expenseId {@link String} the expense id
	 * @param expense {@link Expense} the information that the existing expense is updated to
	 * @return {@link Expense} the updated expense
	 */
	Expense updateExpense(String expenseId, Expense expense);

	/**
	 * Deletes and expense by its id
	 * @param expenseId {@link String} the expense id to be deleted
	 * @return {@link Expense} the deleted expense
	 */
	Expense deleteExpense(String expenseId);

	/**
	 * Checks to see if a category exists and if it doesn't it adds it
	 * @param category {@link Category} the category to be checked
	 * @return {@link Category} the category saved in the repo
	 */
	Category checkAndAddCategory(Category category);
}
 