package com.website.website.repository;

import com.website.website.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    User findByUsername(String name);

}
