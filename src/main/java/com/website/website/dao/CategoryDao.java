<<<<<<< HEAD
package com.website.website.dao;


import com.website.website.controller.BaseController;
import com.website.website.exceptions.NotFoundException;
import com.website.website.model.*;

import org.springframework.data.domain.Sort;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class CategoryDao extends BaseController implements ICategoryDao {
    private final MongoTemplate mongoTemplate;
    private static HashMap<String, Object> localCache = new HashMap<String, Object>();

    @Autowired
    public CategoryDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    private IUserDao userDao;

    @Override
    public List<Category> getUserCategoriesById(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(categoryIds));
        return mongoTemplate.find(query, Category.class);
    }

    @Override
    public List<Items> getUserItemsByCategoryIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(categoryIds));
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<Stock> getUserStockByItemsIds(List<String> itemsIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("itemsId").in(itemsIds));
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<Items> getUserItemsBySubCategoryIds(List<String> subCategoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").in(subCategoryIds));
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<SubCategory> getUserSubCategoriesById(List<String> CategoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(CategoryIds));
        return mongoTemplate.find(query, SubCategory.class);
    }

    @Override
    public List<SubCategory> getSubCategorysByCategoryId(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.find(query, SubCategory.class);
    }

    @Override
    public List<Items> getItemsByCategoryId(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.find(query, Items.class);
    }




    @Override
    public SubCategory getSubCategoryById(String subCategoryId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(subCategoryId));
        return mongoTemplate.findOne(query, SubCategory.class);
    }

    @Override
    public Items getItemsById(String itemsId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(itemsId));
        return mongoTemplate.findOne(query, Items.class);
    }

    @Override
    public Object modifyCategory(String categoryId, Category category) {
       Query query = new Query();
       query.addCriteria(Criteria.where("id").is(categoryId));

        Update update = new Update().set("name",category.getName());
        mongoTemplate.updateFirst(query, update, Category.class);

        return null;
    }

    @Override
    public Object modifySubCategory(String subCategoryId, SubCategory subCategory) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(subCategoryId));

        Update update = new Update().set("name",subCategory.getName());
        mongoTemplate.updateFirst(query, update, SubCategory.class);

        return null;
    }

    @Override
    public Object modifyItems(String itemsId, Items items) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(itemsId));

        Update update = new Update().set("name",items.getName());
        mongoTemplate.updateFirst(query, update, Items.class);

        return null;
    }

    @Override
    public Object deleteCategory(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        mongoTemplate.findAndRemove(query,Category.class);
        return null;
    }

    @Override
    public Object deleteSubCategory(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        mongoTemplate.findAndRemove(query,SubCategory.class);
        return null;
    }

    @Override
    public Object deleteItems(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        mongoTemplate.findAndRemove(query,Items.class);
        return null;
    }

    @Override
    public Category getCategory(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        return mongoTemplate.findOne(query,Category.class);

    }

    @Override
    public SubCategory getSubCategory(String categoryId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        return mongoTemplate.findOne(query,SubCategory.class);

    }

    @Override
    public Object getItems(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        return mongoTemplate.find(query,Items.class);

    }

    @Override
    public List<Items> getItemsBySubCategoryId(String subCategoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").is(subCategoryId));
        return mongoTemplate.find(query,Items.class);
    }

    @Override
    public List<SubCategory> getSubCategoryByCategoryId(String categoryId) {
         Query query = new Query();
         query.addCriteria(Criteria.where("categoryId").is(categoryId));
         return mongoTemplate.find(query,SubCategory.class);
    }

    @Override
    public List<Category> getCategoryByCategoryId(String categoryId) {
       Query query = new Query();
       query.addCriteria(Criteria.where("id").is(categoryId));
       return mongoTemplate.find(query,Category.class);
    }

    @Override
    public long deleteCategoryByCategoryId(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        return mongoTemplate.remove(query,Category.class).getDeletedCount();
    }

    @Override
    public long deleteSubCategoryBySubCategoryId(String subCategoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(subCategoryId));
        return mongoTemplate.remove(query,SubCategory.class).getDeletedCount();
    }

    @Override
    public long deleteItemsByItemsId(String itemsId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(itemsId));
        return mongoTemplate.remove(query,Items.class).getDeletedCount();
    }

    @Override
    public Object searchCategory(String name) {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Criteria c1 = Criteria.where("id").in(l);
        Criteria c2 = Criteria.where("name").regex(name);
        Criteria c  = new Criteria().andOperator(c1,c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Category.class);
    }

    @Override
    public Object searchSubCategory(String name) {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Criteria c1 = Criteria.where("categoryId").in(l);
        Criteria c2 = Criteria.where("name").regex(name);
        Criteria c  = new Criteria().andOperator(c1, c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,SubCategory.class);
    }

    @Override
    public Object searchItems(String name) {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Criteria c1 = Criteria.where("categoryId").in(l);
        Criteria c2 = Criteria.where("name").regex(name);
        Criteria c  = new Criteria().andOperator(c1, c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Items.class);
    }

    @Override
    public Object getItemsForUser() {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(l));
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<Stock> getStock(String itemsId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("itemsId").is(itemsId));
        return mongoTemplate.find(query, Stock.class);

    }

    @Override
    public List<Category> getAllCategories() {
        Query query = new Query();
        return mongoTemplate.find(query, Category.class);
    }

    @Override
    public List<Items> getAllItems() {
        Query query = new Query();
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<Stock> getAllStocks() {
        Query query = new Query();
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<SubCategory> getAllSubCategory() {
        Query query = new Query();
        return mongoTemplate.find(query, SubCategory.class);
    }


    @Override
    public List<Stock> getStockByCategoryId(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        return mongoTemplate.find(query, Stock.class);

    }

    @Override
    public List<Stock> getStockBySubCategoryId(String subCategoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").is(subCategoryId));
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<Stock> findStockByCategoryIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(categoryIds));
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<Stock> findStockBySubCategoryIdAndItemsId(List<String> subCategoryIds, List<String> itemsIds)
    {
       Criteria c1 = Criteria.where("subCategoryId").in(subCategoryIds);
       Criteria c2 = Criteria.where("itemsId").in(itemsIds);
       Criteria c = new Criteria().orOperator(c1,c2);
       Query query = new Query();
       query.addCriteria(c);
       return mongoTemplate.find(query,Stock.class);
    }

    @Override
    public List<Stock> findStockBySubCategoryIdOrItemsId(List<String> subCategoryIds, List<String> itemsIds) {
        Criteria c1 = Criteria.where("subCategoryId").in(subCategoryIds);
        Criteria c2 = Criteria.where("itemsId").in(itemsIds);
        Criteria c = new Criteria().andOperator(c1,c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Stock.class);
    }

    @Override
    public Object getStockByCategoryIdThruSubAndItems(String categoryId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        mongoTemplate.find(query,Category.class);

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("categoryId").is(categoryId));
        List<SubCategory> l = mongoTemplate.find(query1,SubCategory.class);
        System.out.println(l);
        for(SubCategory s:l)
        {
             String sub  = s.getId();
            System.out.println(sub);


        }
           return null;
    }

    @Override
    public List<Items> getItemsBySubCategoryIds(List<String> subCategoryIds,String categoryId) {
        Criteria c1 = Criteria.where("subCategoryId").in(subCategoryIds);
        Criteria c2 = Criteria.where("categoryId").is(categoryId);
        Criteria c= new Criteria().andOperator(c1,c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Items.class);
    }

    @Override
    public SubCategory getSubCategoryId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.findOne(query, SubCategory.class);
    }

    @Override
    public Items getItemBySubCategoryId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("SubCategoryId").is(id));
        return mongoTemplate.findOne(query, Items.class);
    }

    @Override
    public Items getItemByCategoryId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.findOne(query, Items.class);
    }
    
    @Override
    public List<Category> getCategoriesByListOfIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(categoryIds));
        return mongoTemplate.find(query, Category.class);
    }
    
    @Override
    public List<Stock> getStockByCategoryIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(categoryIds));
        return mongoTemplate.find(query, Stock.class);
    }

	@Override
	public List<SubCategory> getSubCategoriesByListOfIds(List<String> subCategoryIds) {
		Query query = new Query();
        query.addCriteria(Criteria.where("id").in(subCategoryIds));
        return mongoTemplate.find(query, SubCategory.class);
	}

	@Override
	public List<Stock> getStockByListofSubCategoryIds(List<String> subCategoryIds) {
		Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").in(subCategoryIds));
        return mongoTemplate.find(query, Stock.class);
	}



}
=======
package com.website.website.dao;


import com.website.website.controller.BaseController;
import com.website.website.exceptions.NotFoundException;
import com.website.website.model.*;

import org.springframework.data.domain.Sort;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class CategoryDao extends BaseController implements ICategoryDao {
    private final MongoTemplate mongoTemplate;
    private static HashMap<String, Object> localCache = new HashMap<String, Object>();

    @Autowired
    public CategoryDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    private IUserDao userDao;

    @Override
    public List<Category> getUserCategoriesById(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(categoryIds));
        return mongoTemplate.find(query, Category.class);
    }

    @Override
    public List<Items> getUserItemsByCategoryIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(categoryIds));
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<Stock> getUserStockByItemsIds(List<String> itemsIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("itemsId").in(itemsIds));
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<Items> getUserItemsBySubCategoryIds(List<String> subCategoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").in(subCategoryIds));
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<SubCategory> getUserSubCategoriesById(List<String> CategoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(CategoryIds));
        return mongoTemplate.find(query, SubCategory.class);
    }

    @Override
    public List<SubCategory> getSubCategorysByCategoryId(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.find(query, SubCategory.class);
    }

    @Override
    public List<Items> getItemsByCategoryId(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.find(query, Items.class);
    }




    @Override
    public SubCategory getSubCategoryById(String subCategoryId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(subCategoryId));
        return mongoTemplate.findOne(query, SubCategory.class);
    }

    @Override
    public Items getItemsById(String itemsId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(itemsId));
        return mongoTemplate.findOne(query, Items.class);
    }

    @Override
    public Object modifyCategory(String categoryId, Category category) {
       Query query = new Query();
       query.addCriteria(Criteria.where("id").is(categoryId));

        Update update = new Update().set("name",category.getName());
        mongoTemplate.updateFirst(query, update, Category.class);

        return null;
    }

    @Override
    public Object modifySubCategory(String subCategoryId, SubCategory subCategory) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(subCategoryId));

        Update update = new Update().set("name",subCategory.getName());
        mongoTemplate.updateFirst(query, update, SubCategory.class);

        return null;
    }

    @Override
    public Object modifyItems(String itemsId, Items items) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(itemsId));

        Update update = new Update().set("name",items.getName());
        mongoTemplate.updateFirst(query, update, Items.class);

        return null;
    }

    @Override
    public Object deleteCategory(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        mongoTemplate.findAndRemove(query,Category.class);
        return null;
    }

    @Override
    public Object deleteSubCategory(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        mongoTemplate.findAndRemove(query,SubCategory.class);
        return null;
    }

    @Override
    public Object deleteItems(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        mongoTemplate.findAndRemove(query,Items.class);
        return null;
    }

    @Override
    public Category getCategory(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        return mongoTemplate.findOne(query,Category.class);

    }

    @Override
    public SubCategory getSubCategory(String categoryId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        return mongoTemplate.findOne(query,SubCategory.class);

    }

    @Override
    public Object getItems(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        return mongoTemplate.find(query,Items.class);

    }

    @Override
    public List<Items> getItemsBySubCategoryId(String subCategoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").is(subCategoryId));
        return mongoTemplate.find(query,Items.class);
    }

    @Override
    public List<SubCategory> getSubCategoryByCategoryId(String categoryId) {
         Query query = new Query();
         query.addCriteria(Criteria.where("categoryId").is(categoryId));
         return mongoTemplate.find(query,SubCategory.class);
    }

    @Override
    public List<Category> getCategoryByCategoryId(String categoryId) {
       Query query = new Query();
       query.addCriteria(Criteria.where("id").is(categoryId));
       return mongoTemplate.find(query,Category.class);
    }

    @Override
    public long deleteCategoryByCategoryId(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        return mongoTemplate.remove(query,Category.class).getDeletedCount();
    }

    @Override
    public long deleteSubCategoryBySubCategoryId(String subCategoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(subCategoryId));
        return mongoTemplate.remove(query,SubCategory.class).getDeletedCount();
    }

    @Override
    public long deleteItemsByItemsId(String itemsId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(itemsId));
        return mongoTemplate.remove(query,Items.class).getDeletedCount();
    }

    @Override
    public Object searchCategory(String name) {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Criteria c1 = Criteria.where("id").in(l);
        Criteria c2 = Criteria.where("name").regex(name);
        Criteria c  = new Criteria().andOperator(c1,c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Category.class);
    }

    @Override
    public Object searchSubCategory(String name) {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Criteria c1 = Criteria.where("categoryId").in(l);
        Criteria c2 = Criteria.where("name").regex(name);
        Criteria c  = new Criteria().andOperator(c1, c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,SubCategory.class);
    }

    @Override
    public Object searchItems(String name) {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Criteria c1 = Criteria.where("categoryId").in(l);
        Criteria c2 = Criteria.where("name").regex(name);
        Criteria c  = new Criteria().andOperator(c1, c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Items.class);
    }

    @Override
    public Object getItemsForUser() {
        String userId = getLoggedInUserId();
        User u = userDao.findById(userId);
        List<String> l = u.getCategoryAccess();
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(l));
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<Stock> getStock(String itemsId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("itemsId").is(itemsId));
        return mongoTemplate.find(query, Stock.class);

    }

    @Override
    public List<Category> getAllCategories() {
        Query query = new Query();
        return mongoTemplate.find(query, Category.class);
    }

    @Override
    public List<Items> getAllItems() {
        Query query = new Query();
        return mongoTemplate.find(query, Items.class);
    }

    @Override
    public List<Stock> getAllStocks() {
        Query query = new Query();
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<SubCategory> getAllSubCategory() {
        Query query = new Query();
        return mongoTemplate.find(query, SubCategory.class);
    }


    @Override
    public List<Stock> getStockByCategoryId(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(categoryId));
        return mongoTemplate.find(query, Stock.class);

    }

    @Override
    public List<Stock> getStockBySubCategoryId(String subCategoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").is(subCategoryId));
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<Stock> findStockByCategoryIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(categoryIds));
        return mongoTemplate.find(query, Stock.class);
    }

    @Override
    public List<Stock> findStockBySubCategoryIdAndItemsId(List<String> subCategoryIds, List<String> itemsIds)
    {
       Criteria c1 = Criteria.where("subCategoryId").in(subCategoryIds);
       Criteria c2 = Criteria.where("itemsId").in(itemsIds);
       Criteria c = new Criteria().orOperator(c1,c2);
       Query query = new Query();
       query.addCriteria(c);
       return mongoTemplate.find(query,Stock.class);
    }

    @Override
    public List<Stock> findStockBySubCategoryIdOrItemsId(List<String> subCategoryIds, List<String> itemsIds) {
        Criteria c1 = Criteria.where("subCategoryId").in(subCategoryIds);
        Criteria c2 = Criteria.where("itemsId").in(itemsIds);
        Criteria c = new Criteria().andOperator(c1,c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Stock.class);
    }

    @Override
    public Object getStockByCategoryIdThruSubAndItems(String categoryId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryId));
        mongoTemplate.find(query,Category.class);

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("categoryId").is(categoryId));
        List<SubCategory> l = mongoTemplate.find(query1,SubCategory.class);
        System.out.println(l);
        for(SubCategory s:l)
        {
             String sub  = s.getId();
            System.out.println(sub);


        }
           return null;
    }

    @Override
    public List<Items> getItemsBySubCategoryIds(List<String> subCategoryIds,String categoryId) {
        Criteria c1 = Criteria.where("subCategoryId").in(subCategoryIds);
        Criteria c2 = Criteria.where("categoryId").is(categoryId);
        Criteria c= new Criteria().andOperator(c1,c2);
        Query query = new Query();
        query.addCriteria(c);
        return mongoTemplate.find(query,Items.class);
    }

    @Override
    public SubCategory getSubCategoryId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.findOne(query, SubCategory.class);
    }

    @Override
    public Items getItemBySubCategoryId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("SubCategoryId").is(id));
        return mongoTemplate.findOne(query, Items.class);
    }

    @Override
    public Items getItemByCategoryId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").is(id));
        return mongoTemplate.findOne(query, Items.class);
    }
    
    @Override
    public List<Category> getCategoriesByListOfIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(categoryIds));
        return mongoTemplate.find(query, Category.class);
    }
    
    @Override
    public List<Stock> getStockByCategoryIds(List<String> categoryIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryId").in(categoryIds));
        return mongoTemplate.find(query, Stock.class);
    }

	@Override
	public List<SubCategory> getSubCategoriesByListOfIds(List<String> subCategoryIds) {
		Query query = new Query();
        query.addCriteria(Criteria.where("id").in(subCategoryIds));
        return mongoTemplate.find(query, SubCategory.class);
	}

	@Override
	public List<Stock> getStockByListofSubCategoryIds(List<String> subCategoryIds) {
		Query query = new Query();
        query.addCriteria(Criteria.where("subCategoryId").in(subCategoryIds));
        return mongoTemplate.find(query, Stock.class);
	}



}
>>>>>>> f1c1a785cf4369bf43e10cc721370ebfdb6f83fd
