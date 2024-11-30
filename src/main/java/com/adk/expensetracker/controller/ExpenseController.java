package com.adk.expensetracker.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adk.expensetracker.model.Expense;
import com.adk.expensetracker.service.ExpenseService;

/**
 * API Endpoints that are used to create, read, update, and delete expenses
 */
@RestController
@RequestMapping("/expense")
public class ExpenseController {
	
	@Autowired
	ExpenseService expenseService;

	/**
	 * Creates a new expense and add it to a user
	 * @param userId {@link String} the id of the user to add the expense to
	 * @param expense {@link Expense} the expense to add
	 * @return {@link ResponseEntity} containing an ExpenseDTO if no api errors are thrown
	 */
	@PostMapping("/create/{userId}")
	public ResponseEntity<Object> createExpense(@PathVariable String userId, @RequestBody Expense expense) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.createExpense(userId, expense)), HttpStatus.OK);
	}

	/**
	 * Gets an expense by the expense id
	 * @param expenseId {@link String} id of the expense
	 * @return {@link ResponseEntity} containing an ExpenseDTO with the given id if no api errors are thrown
	 */
	@GetMapping("/getByExpenseId/{expenseId}")
	public ResponseEntity<Object> getExpenseByExpenseId(@PathVariable String expenseId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpense(expenseId)), HttpStatus.OK);
	}

	/**
	 * Gets an expense by the user id
	 * @param userId {@link String} the user id associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the user id if no api errors are thrown
	 */
	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<Object> getExpenseByUserId(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesForUser(userId)), HttpStatus.OK);
	}

	/**
	 * Gets a list of expenses by its user id and category
	 * @param userId {@link String} the id of the user associated with the expense
	 * @param categoryName {@link String} the name of the category
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and category name if no api errors are thrown
	 */
	@GetMapping("/getByCategory/{userId}/{categoryName}")
	public ResponseEntity<Object> getExpenseForUserByCategory(@PathVariable String userId, @PathVariable String categoryName) {
		System.out.println(userId);
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpenseForUserByCategory(userId, categoryName)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user from the past week
	 * @param userId {@link String} id of the user associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the past week if no api errors are thrown
	 */
	@GetMapping("/getPastWeek/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinLastWeek(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusDays(7), LocalDateTime.now(), userId)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user from the past month
	 * @param userId {@link String} id of the user associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the past month if no api errors are thrown
	 */
	@GetMapping("/getPastMonth/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinPastMonth(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusMonths(1), LocalDateTime.now(), userId)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user from the past 3 months
	 * @param userId {@link String} id of the user associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the past 3 months if no api errors are thrown
	 */
	@GetMapping("/getPastThreeMonths/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinPastThreeMonths(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusMonths(3), LocalDateTime.now(), userId)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user between and including two dates
	 * @param userId {@link String} id of the user associated with the expense
	 * @param startDate {@link LocalDateTime} the start date for the range of dates
	 * @param endDate {@link LocalDateTime} the end date for the range of dates
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the two dates if no api errors are thrown
	 */
	@GetMapping("/get/{userId}/{startDate}/{endDate}")
	public ResponseEntity<Object> getExpenseByUserIdWithinCustomRange(@PathVariable String userId, @PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(startDate, endDate, userId)), HttpStatus.OK);
	}

	/**
	 * Updates an expense
	 * @param expenseId {@link String} id of the expense
	 * @param expense {@link Expense} the information the expense will be updated to
	 * @return {@link ResponseEntity} containing an ExpenseDTO with the passed in expense information if no api errors are thrown
	 */
	@PostMapping("/update/{expenseId}")
	public ResponseEntity<Object> updateExpense(@PathVariable String expenseId, @RequestBody Expense expense) {
		System.out.println(expense.toString());
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.updateExpense(expenseId, expense)), HttpStatus.OK);
	}

	/**
	 * Deletes an expense by its id
	 * @param expenseId {@link String} the id of the expense
	 * @return {@link ResponseEntity} containing an ExpenseDTO of the deleted expense if no api errors are thrown
	 */
	@DeleteMapping("/delete/{expenseId}")
	public ResponseEntity<Object> deleteExpense(@PathVariable String expenseId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.deleteExpense(expenseId)), HttpStatus.OK);
	}

	/**
	 * Creates a new expense category if it doesn't already exist
	 * @param categoryName {@link String} the name of the category
	 * @return {@link String} the name of the saved category if no api errors are thrown
	 */
	@PostMapping("/createCategory/{categoryName}")
	public ResponseEntity<Object> createExpenseCategory(@PathVariable String categoryName) {
		Category newCategory = new Category();
		newCategory.setName(categoryName);
		return new ResponseEntity<>(expenseService.checkAndAddCategory(newCategory).getName(), HttpStatus.OK);
	}
}
