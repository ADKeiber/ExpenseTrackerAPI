package com.adk.expensetracker.repo;

import com.adk.expensetracker.model.Category;
import com.adk.expensetracker.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * MongoDB repository holding User roles
 */
public interface RoleRepo extends MongoRepository<Role, String> {

    /**
     * Finds a role by its value field
     * @param value {@link String} the value of the role
     * @return {@link Optional} of {@link Role} of the Role where the value is equal to the passed in value
     */
    Optional<Role> findByValue(String value);
}
