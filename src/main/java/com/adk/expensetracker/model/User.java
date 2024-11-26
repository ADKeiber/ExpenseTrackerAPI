package com.adk.expensetracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document // this is like a table name
public class User {
	
	@Id
	private String id;
	private String username;
	private String password;
	
}
