package com.adk.expensetracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Database object containing information about a User's role
 */
@Data
@Document
public class Role {
	
	@Id
	private String id;
	private String value;
}
