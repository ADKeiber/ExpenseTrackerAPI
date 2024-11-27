package com.adk.expensetracker.repo;

import com.adk.expensetracker.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role, String> {
    Optional<Role> findByValue(String value);
}
