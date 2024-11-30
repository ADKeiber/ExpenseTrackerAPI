package com.adk.expensetracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Database object containing information about an Expense's Category
 */
@Data
@Document
public class Category {
	
	@Id
	private String id;
	private String name;
	
}
