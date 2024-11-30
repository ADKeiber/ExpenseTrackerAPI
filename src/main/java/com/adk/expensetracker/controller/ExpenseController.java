package com.adk.expensetracker.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.adk.expensetracker.dto.ExpenseDTO;
import com.adk.expensetracker.dto.UserDTO;
import com.adk.expensetracker.errorhandling.ApiError;
import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.util.DTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adk.expensetracker.model.Expense;
import com.adk.expensetracker.service.ExpenseService;

/**
 * API Endpoints that are used to create, read, update, and delete expenses
 */
@RestController
@RequestMapping("/expense")
public class ExpenseController {
	
	@Autowired
	ExpenseService expenseService;

	/**
	 * Creates a new expense and add it to a user
	 * @param userId {@link String} the id of the user to add the expense to
	 * @param expense {@link Expense} the expense to add
	 * @return {@link ResponseEntity} containing an ExpenseDTO if no api errors are thrown
	 */
	@Operation(summary = "Create a new Expense", description = "Creates a new expense by taking in a JSON Expense Object in the request body and a user ID in the request parameters. If required fields are blank/null inside of the request body an API Error will be returned. " +
			"Required fields: shortDescription, fullDescription, amount, date", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ExpenseDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674b3a2f7f637a4fbadaadfa\",\n" +
							"    \"username\": \"test\",\n" +
							"    \"roles\": [\n" +
							"        \"USER\"\n" +
							"    ]\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
							+ "        \"status\": \"BAD_REQUEST\",\r\n"
							+ "        \"timestamp\": \"11-11-2024 02:25:54\",\r\n"
							+ "        \"message\": \"One of the Required fields was missing for the passed in entity!\",\r\n"
							+ "        \"debugMessage\": \"Expense was missing value of field 'shortDescription' which is of class java.lang.String\"\r\n"
							+ "    }\r\n" + "}") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@PostMapping("/create/{userId}")
	public ResponseEntity<Object> createExpense(@PathVariable String userId, @RequestBody Expense expense) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.createExpense(userId, expense)), HttpStatus.OK);
	}

	/**
	 * Gets an expense by the expense id
	 * @param expenseId {@link String} id of the expense
	 * @return {@link ResponseEntity} containing an ExpenseDTO with the given id if no api errors are thrown
	 */
	@Operation(summary = "Retrieve an Expense By its ID", description = "Retrieves an Expense By its ID", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ExpenseDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674841571b816b31462e9583\",\n" +
							"    \"shortDescription\": \"Bank Transfer4\",\n" +
							"    \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"    \"amount\": 10.5,\n" +
							"    \"date\": \"2024-09-10T22:56:43.703\",\n" +
							"    \"category\": {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"        \"name\": \"test\"\n" +
							"    },\n" +
							"    \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ No Expense exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"Expense was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@GetMapping("/getByExpenseId/{expenseId}")
	public ResponseEntity<Object> getExpenseByExpenseId(@PathVariable String expenseId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpense(expenseId)), HttpStatus.OK);
	}

	/**
	 * Gets an expense by the user id
	 * @param userId {@link String} the user id associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the user id if no api errors are thrown
	 */
	@Operation(summary = "Retrieve Expenses By its user ID", description = "Retrieves Expenses By its user ID", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpenseDTO.class)), examples = {
					@ExampleObject(value = "[\n" +
							"    {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0e\",\n" +
							"        \"shortDescription\": \"Bank Transfer1\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-09-10T22:56:43.703\",\n" +
							"        \"category\": null,\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    },\n" +
							"    {\n" +
							"        \"id\": \"6748368e9bf8ee5aaccf393a\",\n" +
							"        \"shortDescription\": \"Bank Transfer3\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-09-10T22:56:43.703\",\n" +
							"        \"category\": null,\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    }\n" +
							"]") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ No expenses found for that user ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"Expense was not found for parameters {user.id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<Object> getExpenseByUserId(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesForUser(userId)), HttpStatus.OK);
	}

	/**
	 * Gets a list of expenses by its user id and category
	 * @param userId {@link String} the id of the user associated with the expense
	 * @param categoryName {@link String} the name of the category
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and category name if no api errors are thrown
	 */
	@Operation(summary = "Retrieve Expenses By its user ID and category", description = "Retrieves Expenses By its user ID and category name", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpenseDTO.class)), examples = {
					@ExampleObject(value = "[\n" +
							"    {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0e\",\n" +
							"        \"shortDescription\": \"Bank Transfer1\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-09-10T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    },\n" +
							"    {\n" +
							"        \"id\": \"6748368e9bf8ee5aaccf393a\",\n" +
							"        \"shortDescription\": \"Bank Transfer3\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-09-10T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    }\n" +
							"]") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@GetMapping("/getByCategory/{userId}/{categoryName}")
	public ResponseEntity<Object> getExpenseForUserByCategory(@PathVariable String userId, @PathVariable String categoryName) {
		System.out.println(userId);
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpenseForUserByCategory(userId, categoryName)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user from the past week
	 * @param userId {@link String} id of the user associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the past week if no api errors are thrown
	 */
	@Operation(summary = "Get Expenses for a user within the last week", description = "Gets Expenses for a user within the last week", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpenseDTO.class)), examples = {
					@ExampleObject(value = "[\n" +
							"    {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0e\",\n" +
							"        \"shortDescription\": \"Bank Transfer1\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-11-30T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    },\n" +
							"    {\n" +
							"        \"id\": \"6748368e9bf8ee5aaccf393a\",\n" +
							"        \"shortDescription\": \"Bank Transfer3\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-11-28T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    }\n" +
							"]") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@GetMapping("/getPastWeek/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinLastWeek(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusDays(7), LocalDateTime.now(), userId)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user from the past month
	 * @param userId {@link String} id of the user associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the past month if no api errors are thrown
	 */
	@Operation(summary = "Get Expenses for a user within the last month", description = "Gets Expenses for a user within the last month", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpenseDTO.class)), examples = {
					@ExampleObject(value = "[\n" +
							"    {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0e\",\n" +
							"        \"shortDescription\": \"Bank Transfer1\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-11-30T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    },\n" +
							"    {\n" +
							"        \"id\": \"6748368e9bf8ee5aaccf393a\",\n" +
							"        \"shortDescription\": \"Bank Transfer3\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-11-08T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    }\n" +
							"]") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@GetMapping("/getPastMonth/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinPastMonth(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusMonths(1), LocalDateTime.now(), userId)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user from the past 3 months
	 * @param userId {@link String} id of the user associated with the expense
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the past 3 months if no api errors are thrown
	 */
	@Operation(summary = "Get Expenses for a user within the last 3 months", description = "Gets Expenses for a user within the last 3 months", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpenseDTO.class)), examples = {
					@ExampleObject(value = "[\n" +
							"    {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0e\",\n" +
							"        \"shortDescription\": \"Bank Transfer1\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-11-30T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    },\n" +
							"    {\n" +
							"        \"id\": \"6748368e9bf8ee5aaccf393a\",\n" +
							"        \"shortDescription\": \"Bank Transfer3\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-10-28T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    }\n" +
							"]") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@GetMapping("/getPastThreeMonths/{userId}")
	public ResponseEntity<Object> getExpenseByUserIdWithinPastThreeMonths(@PathVariable String userId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(LocalDate.now().atStartOfDay().minusMonths(3), LocalDateTime.now(), userId)), HttpStatus.OK);
	}

	/**
	 * Gets all expenses for a user between and including two dates
	 * @param userId {@link String} id of the user associated with the expense
	 * @param startDate {@link LocalDateTime} the start date for the range of dates
	 * @param endDate {@link LocalDateTime} the end date for the range of dates
	 * @return {@link ResponseEntity} containing a list of ExpenseDTOs with the given user id and is from within the two dates if no api errors are thrown
	 */
	@Operation(summary = "Get Expenses for a user within the date range", description = "Gets Expenses for a user within the specified date range where startDate is the left bound and endDate is the right bound", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpenseDTO.class)), examples = {
					@ExampleObject(value = "[\n" +
							"    {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0e\",\n" +
							"        \"shortDescription\": \"Bank Transfer1\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-11-30T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    },\n" +
							"    {\n" +
							"        \"id\": \"6748368e9bf8ee5aaccf393a\",\n" +
							"        \"shortDescription\": \"Bank Transfer3\",\n" +
							"        \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"        \"amount\": 10.5,\n" +
							"        \"date\": \"2024-11-28T22:56:43.703\",\n" +
							"        \"category\": {\n" +
							"            \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"            \"name\": \"test\"\n" +
							"        },\n" +
							"        \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"    }\n" +
							"]") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@GetMapping("/get/{userId}/{startDate}/{endDate}")
	public ResponseEntity<Object> getExpenseByUserIdWithinCustomRange(@PathVariable String userId, @PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.readExpensesWithDateRange(startDate, endDate, userId)), HttpStatus.OK);
	}

	/**
	 * Updates an expense
	 * @param expenseId {@link String} id of the expense
	 * @param expense {@link Expense} the information the expense will be updated to
	 * @return {@link ResponseEntity} containing an ExpenseDTO with the passed in expense information if no api errors are thrown
	 */
	@Operation(summary = "Update an expense", description = "Update an expense by taking in a JSON Expense Object in the request body and a user ID in the request parameters. If required fields are blank/null inside of the request body an API Error will be returned. " +
			"Required fields: shortDescription, fullDescription, amount, date", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ExpenseDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674b3a2f7f637a4fbadaadfa\",\n" +
							"    \"username\": \"test\",\n" +
							"    \"roles\": [\n" +
							"        \"USER\"\n" +
							"    ]\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
							+ "        \"status\": \"BAD_REQUEST\",\r\n"
							+ "        \"timestamp\": \"11-11-2024 02:25:54\",\r\n"
							+ "        \"message\": \"One of the Required fields was missing for the passed in entity!\",\r\n"
							+ "        \"debugMessage\": \"Expense was missing value of field 'shortDescription' which is of class java.lang.String\"\r\n"
							+ "    }\r\n" + "}") })),
			@ApiResponse(description = "Bad Request/ No User exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"User was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") }))})
	@PostMapping("/update/{expenseId}")
	public ResponseEntity<Object> updateExpense(@PathVariable String expenseId, @RequestBody Expense expense) {
		System.out.println(expense.toString());
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.updateExpense(expenseId, expense)), HttpStatus.OK);
	}

	/**
	 * Deletes an expense by its id
	 * @param expenseId {@link String} the id of the expense
	 * @return {@link ResponseEntity} containing an ExpenseDTO of the deleted expense if no api errors are thrown
	 */
	@Operation(summary = "Delete an Expense By its ID", description = "Deletes an Expense By its ID", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ExpenseDTO.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"id\": \"674841571b816b31462e9583\",\n" +
							"    \"shortDescription\": \"Bank Transfer4\",\n" +
							"    \"fullDescription\": \"Bank Transfer to account ending in 1111\",\n" +
							"    \"amount\": 10.5,\n" +
							"    \"date\": \"2024-09-10T22:56:43.703\",\n" +
							"    \"category\": {\n" +
							"        \"id\": \"67482a4bceea026ca6ef5f0d\",\n" +
							"        \"name\": \"test\"\n" +
							"    },\n" +
							"    \"userId\": \"6748299eceea026ca6ef5f0c\"\n" +
							"}") })),
			@ApiResponse(description = "Bad Request/ No Expense exists with that ID", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" +
							"    \"apierror\": {\n" +
							"        \"status\": \"NOT_FOUND\",\n" +
							"        \"timestamp\": \"30-11-2024 11:44:17\",\n" +
							"        \"message\": \"Expense was not found for parameters {id=674560cbf5f7ca5c0e6720a}\",\n" +
							"        \"debugMessage\": null\n" +
							"    }\n" +
							"}") })) })
	@DeleteMapping("/delete/{expenseId}")
	public ResponseEntity<Object> deleteExpense(@PathVariable String expenseId) {
		return new ResponseEntity<>(DTOMapper.mapToExpenseDTO(expenseService.deleteExpense(expenseId)), HttpStatus.OK);
	}

	/**
	 * Creates a new expense category if it doesn't already exist
	 * @param categoryName {@link String} the name of the category
	 * @return {@link String} the name of the saved category
	 */
	@Operation(summary = "Create a new Category", description = "Creates a new expense category", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = String.class), examples = {
					@ExampleObject(value = "Loans") })) })
	@PostMapping("/createCategory/{categoryName}")
	public ResponseEntity<Object> createExpenseCategory(@PathVariable String categoryName) {
		Category newCategory = new Category();
		newCategory.setName(categoryName);
		return new ResponseEntity<>(expenseService.checkAndAddCategory(newCategory).getName(), HttpStatus.OK);
	}
}
