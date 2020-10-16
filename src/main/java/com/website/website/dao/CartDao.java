package com.website.website.dao;

import com.mongodb.client.result.UpdateResult;
import com.website.website.controller.BaseController;
import com.website.website.model.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CartDao extends BaseController implements ICartDao {

    private final MongoTemplate mongoTemplate;
    private static HashMap<String, Object> localCache = new HashMap<String, Object>();

    @Autowired
    public CartDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Stock findByStockId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Stock.class);
    }

    @Override
    public Cart findByCartID(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Cart.class);
    }

    @Override
    public Cart findByStockIdInCart(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stockId").is(id));
        return mongoTemplate.findOne(query, Cart.class);
    }

    @Override
    public Object updateQuantityCount(String id, Long count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stockId").is(id));
        Update update = new Update().set("quantity", count);
        UpdateResult ur = mongoTemplate.updateFirst(query, update, Cart.class);
        return findByStockIdInCart(id);
    }

    @Override
    public Object updateQuantityCountBasedOnCartId(String id, Long count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update().set("quantity", count);
        UpdateResult ur = mongoTemplate.updateFirst(query, update, Cart.class);
        return findByStockIdInCart(id);
    }



    @Override
    public List<Cart> getAllCartStock() {
        Query query = new Query();
        return  mongoTemplate.find(query, Cart.class);

    }

    @Override
    public Cart findCartItemStockId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stockId").is(id));
        return mongoTemplate.findOne(query, Cart.class);
    }


    @Override
    public Object deleteCartItems(String id) {
        Query query = new Query();
        Criteria c = Criteria.where("id").is(id);
        query.addCriteria(c);
        return mongoTemplate.remove(query, Cart.class).getDeletedCount();
    }

}

