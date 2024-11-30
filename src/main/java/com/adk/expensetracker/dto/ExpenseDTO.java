package com.adk.expensetracker.dto;

import com.adk.expensetracker.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO that contains the information of an Expense
 */
@Data
@AllArgsConstructor
public class ExpenseDTO {
    private String id;
    private String shortDescription;
    private String fullDescription;
    private Double amount;
    private LocalDateTime date;
    private Category category;
    private String userId;
}
