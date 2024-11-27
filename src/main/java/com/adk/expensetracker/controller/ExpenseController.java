package com.adk.expensetracker.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

@RestController
@RequestMapping("/expense")
public class ExpenseController {
	
	@Autowired
	ExpenseService expenseService;

	@PostMapping("/create/{userId}")
	public ResponseEntity<Object> createExpense(@PathVariable String userId, @RequestBody Expense expense) {
		System.out.println(expense.toString());
		return new ResponseEntity<>(expenseService.createExpense(userId, expense), HttpStatus.OK);
	}
	
	@GetMapping("/getByExpenseId/{expenseId}")
	public ResponseEntity<Object> getExpenseByExpenseId(@PathVariable String expenseId) {
		System.out.println(expenseId);
		return new ResponseEntity<>(expenseService.readExpense(expenseId), HttpStatus.OK);
	}
	
	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<Object> getExpenseByUserId(@PathVariable String userId) {
		System.out.println(userId);
		return new ResponseEntity<>(expenseService.readExpensesForUser(userId), HttpStatus.OK);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<Object> getExpenseForUserByCategory(@PathVariable String userId) {
		System.out.println(userId);
		return new ResponseEntity<>(expenseService.readExpensesForUser(userId), HttpStatus.OK);
	}
	
	@GetMapping("/getPastWeek/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinLastWeek(@PathVariable String userId) {
		return new ResponseEntity<>(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusDays(7), LocalDateTime.now(), userId), HttpStatus.OK);
	}
	
	@GetMapping("/getPastMonth/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinPastMonth(@PathVariable String userId) {
		return new ResponseEntity<>(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusMonths(1), LocalDateTime.now(), userId), HttpStatus.OK);
	}
	
	@GetMapping("/getPastThreeMonths/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinPastThreeMonths(@PathVariable String userId) {
		return new ResponseEntity<>(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusMonths(3), LocalDateTime.now(), userId), HttpStatus.OK);
	}
	
	@GetMapping("/get/{userId}/{startDate}/{endDate}")
	public ResponseEntity<Object> getExpenseByUserIdWithinCustomRange(@PathVariable String userId, @PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
		return new ResponseEntity<>(expenseService.readExpensesWithDateRange(startDate, endDate, userId), HttpStatus.OK);
	}
	
	@PostMapping("/update/{expenseId}")
	public ResponseEntity<Object> updateExpense(@PathVariable String expenseId, @RequestBody Expense expense) {
		System.out.println(expense.toString());
		return new ResponseEntity<>(expenseService.updateExpense(expenseId, expense), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{expenseId}")
	public ResponseEntity<Object> deleteExpense(@PathVariable String expenseId) {
		return new ResponseEntity<>(expenseService.deleteExpense(expenseId), HttpStatus.OK);
	}
}
