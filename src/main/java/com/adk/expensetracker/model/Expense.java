package com.adk.expensetracker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import com.adk.expensetracker.errorhandling.FieldBlankException;

/**
 * Database object containing information about an Expense
 */
@Data
@Document
public class Expense {
	
	@Id
	private String id;
	private String shortDescription;
	private String fullDescription;
	private Double amount;
	private LocalDateTime date;
	@DocumentReference(lookup = "{ 'name' : ?#{#target} }")
	private Category category;
	@DBRef
	private User user;
	
	public void checkRequiredFields() {
		if( shortDescription == null || shortDescription.isBlank())
			throw new FieldBlankException(Expense.class, "short description", String.class.toGenericString());
		if( fullDescription == null || fullDescription.isBlank())
			throw new FieldBlankException(Expense.class, "full description", String.class.toGenericString());
		if( amount == null || amount == 0.00)
			throw new FieldBlankException(Expense.class, "amount", Double.class.toGenericString());
		if( date == null)
			throw new FieldBlankException(Expense.class, "date", LocalDate.class.toGenericString());
		if( user == null)
			throw new FieldBlankException(Expense.class, "User", User.class.toGenericString());
		if( user.getId() == null || user.getId().isBlank())
			throw new FieldBlankException(Expense.class, "user.id", String.class.toGenericString());
	}
}
