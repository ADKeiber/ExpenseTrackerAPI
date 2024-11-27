package com.adk.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.expensetracker.errorhandling.EntityNotFoundException;
import com.adk.expensetracker.model.Expense;
import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.ExpenseRepo;
import com.adk.expensetracker.repo.UserRepo;

@Service
public class ExpenseService implements IExpenseService {
	
	@Autowired
	ExpenseRepo expenseRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public Expense createExpense(Expense expense) {
		expense.checkRequiredFields();
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
		
		Optional<User> returnedUser = userRepo.findById(userId);
		if( returnedUser.isEmpty() )
			throw new EntityNotFoundException(User.class, "id", userId);
		
		List<Expense> returnedExpenses = expenseRepo.findByUserId(userId);
		if(returnedExpenses == null || returnedExpenses.size() == 0)
			throw new EntityNotFoundException(Expense.class, "user.id", userId);
		
		return returnedExpenses;
	}

	@Override //This can be updated to have the initial search for expenses taking in the dates instead of filtering
	public List<Expense> readExpensesWithDateRange(LocalDate startDate, LocalDate endDate, String userId) {
		List<Expense> unfilteredExpenses = readExpensesForUser(userId);
		List<Expense> filteredExpenses = unfilteredExpenses.stream().filter(expense -> 
						(expense.getDate().isAfter(startDate) && expense.getDate().isBefore(endDate)) || 
						expense.getDate().isEqual(startDate) || expense.getDate().isEqual(endDate)
						).collect(Collectors.toList());
		return filteredExpenses;
	}

	@Override
	public Expense deleteExpense(String expenseId) {
		Expense returnedResposne = readExpense(expenseId);
		expenseRepo.deleteById(expenseId);
		return returnedResposne;
	}

	@Override
	public Expense updateExpense(String expenseId, Expense expense) {
		
		expense.checkRequiredFields();
		
		Expense foundExpense = readExpense(expenseId);
		
		expense.setId(foundExpense.getId());
		Expense savedExpense = expenseRepo.save(expense);
		
		return savedExpense;
	}

}
