package com.website.website.service;

import com.website.website.controller.BaseController;
import com.website.website.dao.ICartDao;
import com.website.website.dao.ICategoryDao;
import com.website.website.dao.IGenericDao;
import com.website.website.dao.IUserDao;
import com.website.website.exceptions.InvalidInputException;
import com.website.website.jwt.JwtTokenUtil;
import com.website.website.model.*;
import com.website.website.repository.UserRepository;
import com.website.website.utils.BcryptHasher;
import com.website.website.vo.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;


import javax.security.auth.Subject;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class CartService extends BaseController implements ICartService {

    @Autowired
    private IGenericDao genericDao;

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public Object addStockToCart(String id) {
        Stock stock = cartDao.findByStockId(id);
        String name = stock.getName();
        double price = stock.getPrice();
        String image = stock.getImage();
        int randomNumber = stock.getRandomNumber();
        Cart cart1 = new Cart();
        cart1.setStockId(new String(id));
        cart1.setName(name);
        cart1.setImage(image);
        cart1.setPrice(price);
        cart1.setQuantity((long) 1);
        cart1.setRandomNumber(randomNumber);
        Cart saved = (Cart) genericDao.save(cart1);
        return saved;
    }

    @Override
    public Object UpdateQuantity(String id, Long count) {
        return cartDao.updateQuantityCount(id, count);
    }

    @Override
    public List<Cart> getAllCartStock() {
        return cartDao.getAllCartStock();
    }

    @Override
    public Object deleteCartItems(String id) {
        return cartDao.deleteCartItems(id);
    }

    @Override
    public Object getAllByUserLoggedIn() {

        String userId1 = getLoggedInUserId();
        User u = userDao.findById(userId1);
        List<String> categoryIdsList = u.getCategoryAccess(); //category ids

        List<Category> categoryList = categoryDao.getUserCategoriesById(categoryIdsList); // categoriesList
        List<SubCategory> subCategoryList = categoryDao.getUserSubCategoriesById(categoryIdsList);//subcategoryList

        List<String> subCategoriesIds = new ArrayList<>();

        for (int l = 0; l < subCategoryList.size(); l++)
        {
            subCategoriesIds.add(subCategoryList.get(l).getId());
        }
        List<Items> itemsList = categoryDao.getUserItemsBySubCategoryIds(subCategoriesIds); //itemsList

        List<Object> MainList = new ArrayList<>();

        for (int i = 0; i < categoryList.size(); i++)
        {
            List<Object> X = new ArrayList<>();
            X.add(categoryList.get(i));
            List<Object> Y = new ArrayList<>();

            for(int j= 0;j<subCategoryList.size();j++)
            {

                String SubCategoryCategoryId = subCategoryList.get(j).getCategoryId();
                String categoryId = categoryList.get(i).getId();

                Boolean b = (SubCategoryCategoryId.equals(categoryId));
                if(b) {
                    Y.add(subCategoryList.get(j));
                    System.out.println("added");
                }
                else continue;
                List<Object> Z = new ArrayList<>();

                for(int k =0;k<itemsList.size();k++)
                {
                    String subcategorySubCateId =  subCategoryList.get(j).getId();
                    String itemsSubCategoryId = itemsList.get(k).getSubCategoryId();
                    Boolean b1 = (subcategorySubCateId.equals(itemsSubCategoryId));
                    if(b1)
                    {
                        Z.add(itemsList.get(k));
                    }
                }
                Y.add(Z);
            }
            X.add(Y);
            MainList.add(X);
        }
        return MainList;
    }

    @Override
    public Object getStocksForUserLoggedIn() {
        String userId1 = getLoggedInUserId();
        User u = userDao.findById(userId1);
        List<String> categoryIdsList = u.getCategoryAccess(); //category ids
        List<Category> categoryList = categoryDao.getUserCategoriesById(categoryIdsList); // categoriesList
        List<SubCategory> subCategoryList = categoryDao.getUserSubCategoriesById(categoryIdsList);//subcategoryList

        List<String> subCategoriesIds = new ArrayList<>();

        for (int l = 0; l < subCategoryList.size(); l++)
        {
            subCategoriesIds.add(subCategoryList.get(l).getId());
        }
        List<Items> itemsList = categoryDao.getUserItemsBySubCategoryIds(subCategoriesIds); //itemsList
        List<String> itemsIds = new ArrayList<>();
        for (int m = 0; m < itemsList.size(); m++)
        {
            itemsIds.add(itemsList.get(m).getId());
        }
        return categoryDao.getUserStockByItemsIds(itemsIds);

    }
}
