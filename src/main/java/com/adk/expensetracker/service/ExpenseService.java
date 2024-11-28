package com.adk.expensetracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.expensetracker.errorhandling.EntityNotFoundException;
import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.model.Expense;
import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.CategoryRepo;
import com.adk.expensetracker.repo.ExpenseRepo;

@Service
public class ExpenseService implements IExpenseService {
	
	@Autowired
	ExpenseRepo expenseRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryRepo categoryRepo;

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

	@Override
	public Expense readExpense(String expenseId) {
		Optional<Expense> returnedResponse = expenseRepo.findById(expenseId);
		if( returnedResponse.isEmpty() )
			throw new EntityNotFoundException(Expense.class, "id", expenseId);
		return returnedResponse.get();
	}
	
	@Override
	public List<Expense> readExpensesForUser(String userId) {
		User returnedUser = userService.readUser(userId);
		List<Expense> returnedExpenses = expenseRepo.findByUserId(userId);
		if(returnedExpenses == null || returnedExpenses.isEmpty())
			throw new EntityNotFoundException(Expense.class, "user.id", userId);
		return returnedExpenses;
	}


	@Override //This can be updated to have the initial search for expenses taking in the dates instead of filtering
	public List<Expense> readExpensesWithDateRange(LocalDateTime startDate, LocalDateTime endDate, String userId) {
		List<Expense> unfilteredExpenses = readExpensesForUser(userId);
        return unfilteredExpenses.stream().filter(expense ->
                        expense.getDate().isAfter(startDate.withHour(0).withMinute(0).withSecond(0)) && expense.getDate().isBefore(endDate)
                        ).collect(Collectors.toList());
	}

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
	
	@Override
	public Expense deleteExpense(String expenseId) {
		Expense returnedResponse = readExpense(expenseId);
		expenseRepo.deleteById(expenseId);
		return returnedResponse;
	}

	@Override//This can be updated to have the initial search for expenses taking in the category and userId instead of filtering
	public List<Expense> readExpenseForUserByCategory(String userId, Category category) {
		userService.readUser(userId);
		List<Expense> unfilteredExpenses = expenseRepo.findByUserId(userId);
        return unfilteredExpenses.stream().filter(expense -> expense.getCategory() == category).collect(Collectors.toList());
	}
	
	@Override
	public Category checkAndAddCategory(Category category) {
		Optional<Category> foundCategory = categoryRepo.findByName(category.getName());
        return foundCategory.orElseGet(() -> categoryRepo.save(category));
    }
}
