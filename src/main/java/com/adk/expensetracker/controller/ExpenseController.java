package com.adk.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.adk.expensetracker.service.ExpenseService;

@RestController("/expense")
public class ExpenseController {
	
	@Autowired
	ExpenseService expenseService;

}
