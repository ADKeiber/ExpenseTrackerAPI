package com.adk.expensetracker.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Expense {
	
	@Id
	private String id;
	private String shortDescription;
	private String description;
	private Double amount;
	private LocalDate date;
	@DBRef
	private Category category;
	@DBRef
	private User user;
}
