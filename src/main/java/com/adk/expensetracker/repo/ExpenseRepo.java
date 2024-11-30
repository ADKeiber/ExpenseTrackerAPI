package com.adk.expensetracker.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adk.expensetracker.model.Expense;
import org.springframework.data.mongodb.repository.Query;

/**
 * MongoDB repository holding Expense's Category
 */
public interface ExpenseRepo extends MongoRepository<Expense, String> {

	/**
	 * Finds a list of expenses by its user id
	 * @param userId {@link String} the id of the user who owns the expense
	 * @return {@link List} of {@link Expense} where the user id is equal to the passed in value
	 */
	List<Expense> findByUserId(String userId);

	/**
	 * Finds a list of expenses by its User's id and its category
	 * @param userId {@link String} the user id associated with an expense
	 * @param categoryName {@link String} the category name associated with an expense
	 * @return {@link List} of {@link Expense} where the user id  and category is equal to the passed in values
	 */
	@Query(value = "{ 'user.id': ?0,  'category' : ?1 }")
	List<Expense> findByUserIdAndCategoryName(String userId, String categoryName);

	/**
	 * Finds a list of expenses by its User's id and date is in between a start and end date
	 * @param userId {@link String} the user id associated with the expenses
	 * @param startDate {@link LocalDateTime} the date used to signify the start of the dates for expenses
	 * @param endDate {@link LocalDateTime} the date used to signify the end of the dates for expenses
	 * @return {@link List} of {@link Expense} where the user Id  is equal to the passed in value and date is
	 * 			between the passed in dates
	 */
	@Query(value = "{ 'user.id': ?0,  'date' : { $gt :  ?1, $lt : ?2} }")
	List<Expense> findByUserIdAndBetweenTwoDates(String userId, LocalDateTime startDate, LocalDateTime endDate);
}