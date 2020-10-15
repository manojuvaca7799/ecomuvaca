package com.website.website.dao;

import com.website.website.controller.BaseController;
import com.website.website.model.Stock;
import com.website.website.model.User;
import com.website.website.exceptions.NotFoundException;
import com.website.website.vo.UserDetailsModifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserDao extends BaseController implements IUserDao  {

    private final MongoTemplate mongoTemplate;
    private static HashMap<String, Object> localCache = new HashMap<String, Object>();

    @Autowired
    public UserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    public IGenericDao genericDao;
    @Override
    public User findById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }



    @Override
    public Object addCategoryAccessToUser(String userId, String categoryId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        Update update = new Update().addToSet("categoryAccess.",categoryId);
        mongoTemplate.updateFirst(query, update, User.class);
        return null;
    }

    @Override
    public User getUserById(String userId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }



    @Override
    public Object deleteCategoryAccess(String categoryId) {
        Query query = new Query();
        String userId = getLoggedInUserId();
        query.addCriteria(Criteria.where("id").is(userId));
        Update update = new Update().pull("categoryAccess.",categoryId);
        mongoTemplate.updateFirst(query, update, User.class);
        return null;
    }

}

