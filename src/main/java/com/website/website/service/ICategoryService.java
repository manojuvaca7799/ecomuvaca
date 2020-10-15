package com.website.website.service;

import com.website.website.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ICategoryService
{
    List<Category> getUserCategories(String userId);

    Object addCategory(String userId,  Category category);
    Object addSubCategory(SubCategory subCategory, String categoryId);
    Object addItems(Items items, String categoryId, String subcategoryId);

    SubCategory getSubCategory(String subCategoryId);
    Items getItems(String itemsId);
    Object modifyCategory(String categoryId, Category category);
    Object modifySubCategory(String subCategoryId,SubCategory subCategory);
    Object modifyItems(String itemsId,Items items);
    Object deleteCategory(String categoryId);
    Object getCategoryById(String categoryId);


    List<Items> getItemsBySubCategoryId(String subCategoryId);
    List<SubCategory> getSubCategoryByCategoryId(String categoryId);
    List<Category> getCategoryByCategoryId(String categoryId);


    long deleteCategoryByCategoryId(String categoryId);
    long deleteSubCategoryBySubCategoryId(String subCategoryId);
    long deleteItemsByItemsId(String itemsId);

    Object searchCatSubCatItems(String name);

    Object searchOnlyItems(String name);

    Object getSubCatAndItems(String categoryId);

    Object getItemsForUser();

    Object addStock(String itemsId, String categoryId,Stock stock,String subCategoryId);
    List<Stock> getStock(String itemsId);

//    String addPhoto(String name, MultipartFile file) throws IOException;
//
//    Stock getPhoto(String id);

    Object uploadPhoto(String image);

    Object getAll();

    List<Category> getAllCategories();
    List<Items> getAllItems();
    List<Stock> getAllStocks();
    List<SubCategory> getAllSubCategory();

    List<Stock> getStockByCategoryId(String categoryId);
    List<Stock> getStockBySubCategoryId(String subCategoryId);

    List<Stock> findStockByCategoryIds(List<String> categoryIds);

    List<Stock> findStockBySubCategoryIdAndItemsId(List<String> subCategoryIds, List<String> itemsIds, String categoryId);


    List<Items> getItemsBySubCategoryId(List<String> subCategoryIds, String categoryId);

    Object getCategoryAndStockByListOfCategoryIds(List<String> categoryIds,List<String> subCategoryIds);
    Object getSubCategoryAndStockByListOfSubCategoryIds(List<String> SubCategoryIds);



}
