package com.website.website.dao;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDao implements  IGenericDao{

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    public GenericDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Object save(Object o) {
        return mongoTemplate.save(o);
    }

    @Override
    public Object save(Object o, String collectionName)
    {
        return mongoTemplate.save(o, collectionName);
    }

    @Override
    public boolean delete(Object o)
    {
        DeleteResult r = mongoTemplate.remove(o);
        return r.getDeletedCount() > 0;
    }

    @Override
    public boolean delete(Object o, String collectionName) {
        DeleteResult r = mongoTemplate.remove(o, collectionName);
        return r.getDeletedCount() > 0;
    }
}
