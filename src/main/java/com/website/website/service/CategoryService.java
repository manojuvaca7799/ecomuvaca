<<<<<<< HEAD
package com.website.website.service;

import com.website.website.controller.BaseController;
import com.website.website.dao.ICategoryDao;
import com.website.website.dao.IGenericDao;
import com.website.website.dao.IUserDao;
import com.website.website.model.*;
import com.website.website.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service

public class CategoryService extends BaseController implements ICategoryService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CategoryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private IGenericDao genericDao;

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public List<Category> getUserCategories(String userId) {
        String userId1 = getLoggedInUserId();
        User u = userDao.findById(userId1);
        List<String> l = u.getCategoryAccess();
        return categoryDao.getUserCategoriesById(l);

    }

    @Override
    public SubCategory getSubCategory(String subCategoryId) {
        return categoryDao.getSubCategoryById(subCategoryId);
    }

    @Override
    public Items getItems(String itemsId) {
        return categoryDao.getItemsById(itemsId);
    }

    @Override
    public Object modifyCategory(String categoryId, Category category) {
        return categoryDao.modifyCategory(categoryId, category);
    }

    @Override
    public Object modifySubCategory(String subCategoryId, SubCategory subCategory) {
        return categoryDao.modifySubCategory(subCategoryId, subCategory);
    }

    @Override
    public Object modifyItems(String itemsId, Items items) {
        return categoryDao.modifyItems(itemsId, items);
    }

    @Override
    public Object deleteCategory(String categoryId) {
        categoryDao.deleteCategory(categoryId);
        categoryDao.deleteSubCategory(categoryId);
        userDao.deleteCategoryAccess(categoryId);
        categoryDao.deleteItems(categoryId);
        return null;
    }

    @Override
    public Object getCategoryById(String categoryId) {

        return
                Arrays.asList(
                        categoryDao.getCategory(categoryId),
                        categoryDao.getSubCategory(categoryId),
                        categoryDao.getItems(categoryId));

    }

    @Override
    public List<Items> getItemsBySubCategoryId(String subCategoryId) {
        return categoryDao.getItemsBySubCategoryId(subCategoryId);
    }

    @Override
    public List<SubCategory> getSubCategoryByCategoryId(String categoryId) {
        return categoryDao.getSubCategoryByCategoryId(categoryId);
    }

    @Override
    public List<Category> getCategoryByCategoryId(String categoryId) {
        return categoryDao.getCategoryByCategoryId(categoryId);
    }

    @Override
    public long deleteCategoryByCategoryId(String categoryId) {
        return categoryDao.deleteCategoryByCategoryId(categoryId);
    }

    @Override
    public long deleteSubCategoryBySubCategoryId(String subCategoryId) {
        return categoryDao.deleteSubCategoryBySubCategoryId(subCategoryId);
    }

    @Override
    public long deleteItemsByItemsId(String itemsId) {
        return categoryDao.deleteItemsByItemsId(itemsId);
    }


    @Override
    public Object addCategory(String userId, Category category) {
        String name = category.getName();
        Category cat = new Category();
        cat.setName(name);
        Category saved = (Category) genericDao.save(cat);
        Object o = saved.getId();
        String s = o.toString();
        userDao.addCategoryAccessToUser(userId, s);
        return null;

    }

    @Override
    public SubCategory addSubCategory(SubCategory subCategory, String categoryId) {
        String name = subCategory.getName();
        SubCategory subCat = new SubCategory();
        subCat.setCategoryId(new String(categoryId));
        subCat.setName(name);
        SubCategory saved = (SubCategory) genericDao.save(subCat);
        return null;
    }

    @Override
    public Items addItems(Items items, String categoryId, String subcategoryId) {
        String name = items.getName();
        Items item = new Items();
        item.setCategoryId(new String(categoryId));
        item.setSubCategoryId(new String(subcategoryId));
        item.setName(name);
        Items saved = (Items) genericDao.save(item);
        return null;
    }

    @Override
    public Object searchCatSubCatItems(String name) {
        return Arrays.asList(categoryDao.searchCategory(name),
                categoryDao.searchSubCategory(name),
                categoryDao.searchItems(name));
    }

    @Override
    public Object getAll() {
        return Arrays.asList(categoryDao.getAllCategories(),
                categoryDao.getAllSubCategory(),
                categoryDao.getAllItems(),
                categoryDao.getAllStocks());
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public List<Items> getAllItems() {
        return categoryDao.getAllItems();
    }

    @Override
    public List<Stock> getAllStocks() {
        return categoryDao.getAllStocks();
    }

    @Override
    public List<SubCategory> getAllSubCategory() {
        return categoryDao.getAllSubCategory();
    }

    @Override
    public List<Stock> getStockByCategoryId(String categoryId) {
        return categoryDao.getStockByCategoryId(categoryId);
    }

    @Override
    public List<Stock> getStockBySubCategoryId(String subCategoryId) {
        return categoryDao.getStockBySubCategoryId(subCategoryId);
    }

    @Override
    public List<Stock> findStockByCategoryIds(List<String> categoryIds) {
        return categoryDao.findStockByCategoryIds(categoryIds);
    }

    @Override
    public List<Stock> findStockBySubCategoryIdAndItemsId(List<String> subCategoryIds, List<String> itemsIds, String categoryId)
    {
        List<String> sub = subCategoryIds;
        List<String> item = itemsIds;
        if(sub.size()==1&&item.size()==1)
        {
            return categoryDao.findStockBySubCategoryIdOrItemsId(subCategoryIds,itemsIds);
        }
        List<Stock> l= categoryDao.findStockBySubCategoryIdAndItemsId(subCategoryIds, itemsIds);
         System.out.println(l.size());
        if(l.isEmpty())
        {
            return categoryDao.getStockByCategoryId(categoryId);
        }
        else
        {
            return l;
        }
    }

    @Override
    public List<Items> getItemsBySubCategoryId(List<String> subCategoryIds, String categoryId) {
        return categoryDao.getItemsBySubCategoryIds(subCategoryIds, categoryId);
    }



    @Override
    public Object searchOnlyItems(String name) {
        return categoryDao.searchItems(name);
    }


    @Override
    public Object getSubCatAndItems(String categoryId) {
        return Arrays.asList(categoryDao.getSubCategory(categoryId), categoryDao.getItems(categoryId));
    }

    @Override
    public Object getItemsForUser() {
        return categoryDao.getItemsForUser();
    }

    @Override
    public Object addStock(String itemsId,String categoryId, Stock stock,String subCategoryId) {
        String name = stock.getName();
        String gender = stock.getGender();
        List<String> size = stock.getSize();
        double price = stock.getPrice();
        String color = stock.getColor();
        String image = stock.getImage();

        Stock stock1 = new Stock();
        stock1.setItemsId(new String(itemsId));
        stock1.setName(name);
        stock1.setImage(image);
        stock1.setGender(gender);
        stock1.setSize(size);
        stock1.setPrice(price);
        stock1.setColor(color);
        stock1.setCategoryId(new String(categoryId));
        stock1.setSubCategoryId(new String(subCategoryId));
        Stock saved = (Stock) genericDao.save(stock1);
        return null;
    }

    @Override
    public List<Stock> getStock(String itemsId) {
        return categoryDao.getStock(itemsId);
    }

    @Override
    public Object uploadPhoto(String image) {
        Stock stock = new Stock();
        String image1 = stock.getImage();
        Stock stock1 = new Stock();
        stock.setImage(image1);
        Stock saved = (Stock) genericDao.save(stock1);
        return null;
    }

	@Override
	public Object getCategoryAndStockByListOfCategoryIds(List<String> categoryIds,List<String> subCategoryIds) {

        String userId1 = getLoggedInUserId();
        User u = userDao.findById(userId1);
        List<String> categoryIdsList = u.getCategoryAccess(); //category ids

        List<Category> categoryList = categoryDao.getUserCategoriesById(categoryIdsList);
        List<SubCategory> subCategoryList = categoryDao.getUserSubCategoriesById(categoryIdsList);

        List<String> subCategoriesIds = new ArrayList<>();

        for (int l = 0; l < subCategoryList.size(); l++)
        {
            subCategoriesIds.add(subCategoryList.get(l).getId());
        }

        List<Stock> StockListBySubCategoryIds = categoryDao.getStockByListofSubCategoryIds(subCategoriesIds);
        List<Stock> stockList = categoryDao.getStockByCategoryIds(categoryIdsList);




        List<String> categoryNames = new ArrayList<>();
        List<String> SubCategoryNames = new ArrayList<>();
        List<String> stockNames = new ArrayList<>();

        List<String> stockNamesBySubCategoryIds = new ArrayList<>();

        for (int i = 0;i<categoryList.size();i++)
        {
            categoryNames.add(categoryList.get(i).getName());
        }
        for(int j=0;j<stockList.size();j++)
        {
            stockNames.add(stockList.get(j).getName());
        }
        for(int k=0;k<subCategoryList.size();k++)
        {
            SubCategoryNames.add(subCategoryList.get(k).getName());
        }
        for(int l=0;l<StockListBySubCategoryIds.size();l++)
        {
            stockNamesBySubCategoryIds.add(StockListBySubCategoryIds.get(l).getName());
        }

        if(categoryIds.size()==0 && subCategoryIds.size()==0)
        {
            return Arrays.asList(categoryList,stockList);
        }
        if(categoryIds.size()==0 && subCategoryIds.size()!=0)
        {
            return Arrays.asList(categoryList,stockList);
        }
        if(categoryIds.size()!=0 && subCategoryIds.size()!=0)
        {
            return StockListBySubCategoryIds;
        }
        return null;

}




}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//    @Override
//    public String addPhoto(String name, MultipartFile file) throws IOException {
//        Stock stock = new Stock();
//        stock.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
//        stock = photoRepository.insert(stock);
//        return stock.getId();
//
//    }
//
//    @Override
//    public Stock getPhoto(String id) {
//        return photoRepository.findById(id).get();
//    }



=======
package com.website.website.service;

import com.website.website.controller.BaseController;
import com.website.website.dao.ICategoryDao;
import com.website.website.dao.IGenericDao;
import com.website.website.dao.IUserDao;
import com.website.website.model.*;
import com.website.website.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service

public class CategoryService extends BaseController implements ICategoryService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CategoryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private IGenericDao genericDao;

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public List<Category> getUserCategories(String userId) {
        String userId1 = getLoggedInUserId();
        User u = userDao.findById(userId1);
        List<String> l = u.getCategoryAccess();
        return categoryDao.getUserCategoriesById(l);

    }

    @Override
    public SubCategory getSubCategory(String subCategoryId) {
        return categoryDao.getSubCategoryById(subCategoryId);
    }

    @Override
    public Items getItems(String itemsId) {
        return categoryDao.getItemsById(itemsId);
    }

    @Override
    public Object modifyCategory(String categoryId, Category category) {
        return categoryDao.modifyCategory(categoryId, category);
    }

    @Override
    public Object modifySubCategory(String subCategoryId, SubCategory subCategory) {
        return categoryDao.modifySubCategory(subCategoryId, subCategory);
    }

    @Override
    public Object modifyItems(String itemsId, Items items) {
        return categoryDao.modifyItems(itemsId, items);
    }

    @Override
    public Object deleteCategory(String categoryId) {
        categoryDao.deleteCategory(categoryId);
        categoryDao.deleteSubCategory(categoryId);
        userDao.deleteCategoryAccess(categoryId);
        categoryDao.deleteItems(categoryId);
        return null;
    }

    @Override
    public Object getCategoryById(String categoryId) {

        return
                Arrays.asList(
                        categoryDao.getCategory(categoryId),
                        categoryDao.getSubCategory(categoryId),
                        categoryDao.getItems(categoryId));

    }

    @Override
    public List<Items> getItemsBySubCategoryId(String subCategoryId) {
        return categoryDao.getItemsBySubCategoryId(subCategoryId);
    }

    @Override
    public List<SubCategory> getSubCategoryByCategoryId(String categoryId) {
        return categoryDao.getSubCategoryByCategoryId(categoryId);
    }

    @Override
    public List<Category> getCategoryByCategoryId(String categoryId) {
        return categoryDao.getCategoryByCategoryId(categoryId);
    }

    @Override
    public long deleteCategoryByCategoryId(String categoryId) {
        return categoryDao.deleteCategoryByCategoryId(categoryId);
    }

    @Override
    public long deleteSubCategoryBySubCategoryId(String subCategoryId) {
        return categoryDao.deleteSubCategoryBySubCategoryId(subCategoryId);
    }

    @Override
    public long deleteItemsByItemsId(String itemsId) {
        return categoryDao.deleteItemsByItemsId(itemsId);
    }


    @Override
    public Object addCategory(String userId, Category category) {
        String name = category.getName();
        Category cat = new Category();
        cat.setName(name);
        Category saved = (Category) genericDao.save(cat);
        Object o = saved.getId();
        String s = o.toString();
        userDao.addCategoryAccessToUser(userId, s);
        return null;

    }

    @Override
    public SubCategory addSubCategory(SubCategory subCategory, String categoryId) {
        String name = subCategory.getName();
        SubCategory subCat = new SubCategory();
        subCat.setCategoryId(new String(categoryId));
        subCat.setName(name);
        SubCategory saved = (SubCategory) genericDao.save(subCat);
        return null;
    }

    @Override
    public Items addItems(Items items, String categoryId, String subcategoryId) {
        String name = items.getName();
        Items item = new Items();
        item.setCategoryId(new String(categoryId));
        item.setSubCategoryId(new String(subcategoryId));
        item.setName(name);
        Items saved = (Items) genericDao.save(item);
        return null;
    }

    @Override
    public Object searchCatSubCatItems(String name) {
        return Arrays.asList(categoryDao.searchCategory(name),
                categoryDao.searchSubCategory(name),
                categoryDao.searchItems(name));
    }

    @Override
    public Object getAll() {
        return Arrays.asList(categoryDao.getAllCategories(),
                categoryDao.getAllSubCategory(),
                categoryDao.getAllItems(),
                categoryDao.getAllStocks());
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public List<Items> getAllItems() {
        return categoryDao.getAllItems();
    }

    @Override
    public List<Stock> getAllStocks() {
        return categoryDao.getAllStocks();
    }

    @Override
    public List<SubCategory> getAllSubCategory() {
        return categoryDao.getAllSubCategory();
    }

    @Override
    public List<Stock> getStockByCategoryId(String categoryId) {
        return categoryDao.getStockByCategoryId(categoryId);
    }

    @Override
    public List<Stock> getStockBySubCategoryId(String subCategoryId) {
        return categoryDao.getStockBySubCategoryId(subCategoryId);
    }

    @Override
    public List<Stock> findStockByCategoryIds(List<String> categoryIds) {
        return categoryDao.findStockByCategoryIds(categoryIds);
    }

    @Override
    public List<Stock> findStockBySubCategoryIdAndItemsId(List<String> subCategoryIds, List<String> itemsIds, String categoryId)
    {
        List<String> sub = subCategoryIds;
        List<String> item = itemsIds;
        if(sub.size()==1&&item.size()==1)
        {
            return categoryDao.findStockBySubCategoryIdOrItemsId(subCategoryIds,itemsIds);
        }
        List<Stock> l= categoryDao.findStockBySubCategoryIdAndItemsId(subCategoryIds, itemsIds);
         System.out.println(l.size());
        if(l.isEmpty())
        {
            return categoryDao.getStockByCategoryId(categoryId);
        }
        else
        {
            return l;
        }
    }

    @Override
    public List<Items> getItemsBySubCategoryId(List<String> subCategoryIds, String categoryId) {
        return categoryDao.getItemsBySubCategoryIds(subCategoryIds, categoryId);
    }



    @Override
    public Object searchOnlyItems(String name) {
        return categoryDao.searchItems(name);
    }


    @Override
    public Object getSubCatAndItems(String categoryId) {
        return Arrays.asList(categoryDao.getSubCategory(categoryId), categoryDao.getItems(categoryId));
    }

    @Override
    public Object getItemsForUser() {
        return categoryDao.getItemsForUser();
    }

    @Override
    public Object addStock(String itemsId,String categoryId, Stock stock,String subCategoryId) {
        String name = stock.getName();
        String gender = stock.getGender();
        List<String> size = stock.getSize();
        double price = stock.getPrice();
        String color = stock.getColor();
        String image = stock.getImage();

        Stock stock1 = new Stock();
        stock1.setItemsId(new String(itemsId));
        stock1.setName(name);
        stock1.setImage(image);
        stock1.setGender(gender);
        stock1.setSize(size);
        stock1.setPrice(price);
        stock1.setColor(color);
        stock1.setCategoryId(new String(categoryId));
        stock1.setSubCategoryId(new String(subCategoryId));
        Stock saved = (Stock) genericDao.save(stock1);
        return null;
    }

    @Override
    public List<Stock> getStock(String itemsId) {
        return categoryDao.getStock(itemsId);
    }

    @Override
    public Object uploadPhoto(String image) {
        Stock stock = new Stock();
        String image1 = stock.getImage();
        Stock stock1 = new Stock();
        stock.setImage(image1);
        Stock saved = (Stock) genericDao.save(stock1);
        return null;
    }

	@Override
	public Object getCategoryAndStockByListOfCategoryIds(List<String> categoryIds,List<String> subCategoryIds) {

      String userId1 = getLoggedInUserId();
        User u = userDao.findById(userId1);
        List<String> categoryIdsList = u.getCategoryAccess(); //category ids

        List<Category> categoryList = categoryDao.getUserCategoriesById(categoryIdsList);
        List<SubCategory> subCategoryList = categoryDao.getUserSubCategoriesById(categoryIdsList);

        List<String> subCategoriesIds = new ArrayList<>();

        for (int l = 0; l < subCategoryList.size(); l++)
        {
            subCategoriesIds.add(subCategoryList.get(l).getId());
        }

        List<Stock> StockListBySubCategoryIds = categoryDao.getStockByListofSubCategoryIds(subCategoriesIds);
        List<Stock> stockList = categoryDao.getStockByCategoryIds(categoryIdsList);




        List<String> categoryNames = new ArrayList<>();
        List<String> SubCategoryNames = new ArrayList<>();
        List<String> stockNames = new ArrayList<>();

        List<String> stockNamesBySubCategoryIds = new ArrayList<>();

        for (int i = 0;i<categoryList.size();i++)
        {
            categoryNames.add(categoryList.get(i).getName());
        }
        for(int j=0;j<stockList.size();j++)
        {
            stockNames.add(stockList.get(j).getName());
        }
        for(int k=0;k<subCategoryList.size();k++)
        {
            SubCategoryNames.add(subCategoryList.get(k).getName());
        }
        for(int l=0;l<StockListBySubCategoryIds.size();l++)
<<<<<<< HEAD
=======
        {
            stockNamesBySubCategoryIds.add(StockListBySubCategoryIds.get(l).getName());
        }

        if(categoryIds.isEmpty()&& subCategoryIds.isEmpty())
>>>>>>> ccfafcffafe2b56a41dbe3b8d9d51ddf6ecca613
        {
            stockNamesBySubCategoryIds.add(StockListBySubCategoryIds.get(l).getName());
        }

        if(categoryIds.size()==0 && subCategoryIds.size()==0)
        {
            return Arrays.asList(categoryList,stockList);
        }
        if(categoryIds.size()==0 && subCategoryIds.size()!=0)
        {
            return Arrays.asList(categoryList,stockList);
        }
        if(categoryIds.size()!=0 && subCategoryIds.size()!=0)
        {
            return stockNamesBySubCategoryIds;
        }
        return null;
<<<<<<< HEAD

=======
>>>>>>> ccfafcffafe2b56a41dbe3b8d9d51ddf6ecca613
}

	@Override
	public Object getSubCategoryAndStockByListOfSubCategoryIds(List<String> SubCategoryIds) {
		List<SubCategory> SubCategoryList = categoryDao.getSubCategoriesByListOfIds(SubCategoryIds);
		List<Stock> stockList = categoryDao.getStockByListofSubCategoryIds(SubCategoryIds);
		//return Arrays.asList(SubCategoryList,stockList);
		
		List<String> SubCategoryNames = new ArrayList<>();
		List<String> stockNames = new ArrayList<>();
		//return Arrays.asList(categoryList,stockList);
		for(int i =0;i<SubCategoryList.size();i++)
		{
			SubCategoryNames.add(SubCategoryList.get(i).getName());
		}
		
		for(int j=0;j<stockList.size();j++)
		{
			stockNames.add(stockList.get(j).getName());
		}
		
		return Arrays.asList(SubCategoryNames,stockNames);
		
	}


}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//    @Override
//    public String addPhoto(String name, MultipartFile file) throws IOException {
//        Stock stock = new Stock();
//        stock.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
//        stock = photoRepository.insert(stock);
//        return stock.getId();
//
//    }
//
//    @Override
//    public Stock getPhoto(String id) {
//        return photoRepository.findById(id).get();
//    }



>>>>>>> f1c1a785cf4369bf43e10cc721370ebfdb6f83fd
