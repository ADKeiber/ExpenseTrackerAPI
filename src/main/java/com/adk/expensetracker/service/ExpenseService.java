package com.adk.expensetracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.expensetracker.errorhandling.EntityNotFoundException;
import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.model.Expense;
import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.CategoryRepo;
import com.adk.expensetracker.repo.ExpenseRepo;

/**
 * Implementation of {@link IExpenseService}
 */
@Service
public class ExpenseService implements IExpenseService {
	
	@Autowired
	ExpenseRepo expenseRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryRepo categoryRepo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Expense createExpense(String userId, Expense expense) {
		User returnedUser = userService.readUser(userId);
		expense.setUser(returnedUser);
		expense.checkRequiredFields();
		if(expense.getCategory() != null) {
			Category category = checkAndAddCategory(expense.getCategory());
			expense.setCategory(category);
		}
		return expenseRepo.save(expense);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Expense readExpense(String expenseId) {
		Optional<Expense> returnedResponse = expenseRepo.findById(expenseId);
		if( returnedResponse.isEmpty() )
			throw new EntityNotFoundException(Expense.class, "id", expenseId);
		return returnedResponse.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Expense> readExpensesForUser(String userId) {
		userService.readUser(userId);
		List<Expense> returnedExpenses = expenseRepo.findByUserId(userId);
		if(returnedExpenses == null || returnedExpenses.isEmpty())
			throw new EntityNotFoundException(Expense.class, "user.id", userId);
		return returnedExpenses;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Expense> readExpensesWithDateRange(LocalDateTime startDate, LocalDateTime endDate, String userId) {
		userService.readUser(userId);
        return expenseRepo.findByUserIdAndBetweenTwoDates(userId, startDate, endDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Expense updateExpense(String expenseId, Expense expense) {
		expense.checkRequiredFields();
		Expense foundExpense = readExpense(expenseId);
		expense.setId(foundExpense.getId());
		if(expense.getCategory() != null) {
			Category category = checkAndAddCategory(expense.getCategory());
			expense.setCategory(category);
		}
        return expenseRepo.save(expense);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Expense deleteExpense(String expenseId) {
		Expense returnedResponse = readExpense(expenseId);
		expenseRepo.deleteById(expenseId);
		return returnedResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Expense> readExpenseForUserByCategory(String userId, String categoryName) {
		userService.readUser(userId);
		return expenseRepo.findByUserIdAndCategoryName(userId, categoryName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Category checkAndAddCategory(Category category) {
		Optional<Category> foundCategory = categoryRepo.findByName(category.getName());
        return foundCategory.orElseGet(() -> categoryRepo.save(category));
    }
}
