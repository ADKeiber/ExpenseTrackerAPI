package com.adk.expensetracker.util;

import com.adk.expensetracker.dto.ExpenseDTO;
import com.adk.expensetracker.dto.UserDTO;
import com.adk.expensetracker.model.Expense;
import com.adk.expensetracker.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DTOMapper {

    public static UserDTO mapToUserDTO(User user){
        List<String> roleName = new LinkedList<>();
        user.getRoles().forEach(role -> roleName.add(role.getValue()));
        return new UserDTO(user.getId(), user.getUsername(), roleName);
    }

    public static ExpenseDTO mapToExpenseDTO(Expense expense){
        return new ExpenseDTO(expense.getId(), expense.getShortDescription(), expense.getFullDescription(),
                expense.getAmount(), expense.getDate(), expense.getCategory(), expense.getUser().getId());
    }

    public static List<ExpenseDTO> mapToExpenseDTO(List<Expense> expenses){
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        expenses.forEach( expense -> expenseDTOS.add(mapToExpenseDTO(expense)));
        return expenseDTOS;
    }
}
