package com.adk.expensetracker.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document // this is like a table name
public class User {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String username;
	private String password;
	@DBRef
	private List<Role> roles = new ArrayList<>();
}
