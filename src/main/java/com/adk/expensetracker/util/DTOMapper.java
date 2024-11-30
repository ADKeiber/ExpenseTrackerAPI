package com.adk.expensetracker.util;

import com.adk.expensetracker.dto.ExpenseDTO;
import com.adk.expensetracker.dto.UserDTO;
import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.model.Expense;
import com.adk.expensetracker.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class used to Map Data base objects to their DTO counterpart
 */
public class DTOMapper {

    /**
     * Maps a User to a UserDTO
     * @param user {@link User} user to map
     * @return {@link UserDTO} the mapped User
     */
    public static UserDTO mapToUserDTO(User user){
        List<String> roleName = new LinkedList<>();
        user.getRoles().forEach(role -> roleName.add(role.getValue()));
        return new UserDTO(user.getId(), user.getUsername(), roleName);
    }

    /**
     * Maps an {@link Expense} to a {@link ExpenseDTO}
     * @param expense {@link Expense} expense to map
     * @return {@link ExpenseDTO} the mapped expense
     */
    public static ExpenseDTO mapToExpenseDTO(Expense expense){
        return new ExpenseDTO(expense.getId(), expense.getShortDescription(), expense.getFullDescription(),
                expense.getAmount(), expense.getDate(), expense.getCategory(), expense.getUser().getId());
    }

    /**
     * Maps a list of expenses to a list of Expense DTOs
     * @param expenses {@link List} of {@link Expense} expenses to map
     * @return {@link List} of {@link ExpenseDTO} the mapped expenses
     */
    public static List<ExpenseDTO> mapToExpenseDTO(List<Expense> expenses){
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        expenses.forEach( expense -> expenseDTOS.add(mapToExpenseDTO(expense)));
        return expenseDTOS;
    }
}
