package com.adk.expensetracker.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.adk.expensetracker.model.User;

public interface UserRepo extends MongoRepository<User, String> {

}
