package com.website.website.repository;

import com.website.website.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Stock, String>
{

}

