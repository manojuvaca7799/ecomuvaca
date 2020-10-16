package com.website.website.dao;

import com.website.website.model.*;

import java.util.List;

public interface ICategoryDao
{
    List<Category> getUserCategoriesById(List<String> categoryIds);
    List<SubCategory> getUserSubCategoriesById(List<String> CategoryIds);
    List<Items> getUserItemsBySubCategoryIds(List<String> subCategoryIds);
    List<Stock> getUserStockByItemsIds(List<String> itemsIds);
    List<Items> getUserItemsByCategoryIds(List<String> categoryIds);
    List<SubCategory> getSubCategorysByCategoryId(String id);
    List<Items> getItemsByCategoryId(String id);
    SubCategory getSubCategoryById(String subCategoryId);
    Items getItemsById(String itemsId);

    Object modifyCategory(String categoryId, Category category);
    Object modifySubCategory(String subCategoryId,SubCategory subCategory);

    Object modifyItems(String itemsId,Items items);


    Object deleteCategory(String categoryId);
    Object deleteSubCategory(String categoryId);
    Object deleteItems(String categoryId);

    Category getCategory(String categoryId);
    SubCategory getSubCategory(String categoryId);
    Object getItems(String categoryId);


    List<Items> getItemsBySubCategoryId(String subCategoryId);
    List<SubCategory> getSubCategoryByCategoryId(String categoryId);
    List<Category> getCategoryByCategoryId(String categoryId);

    long deleteCategoryByCategoryId(String categoryId);
    long deleteSubCategoryBySubCategoryId(String subCategoryId);
    long deleteItemsByItemsId(String itemsId);

    Object searchCategory(String name);
    Object searchSubCategory(String name);
    Object searchItems(String name);

    Object getItemsForUser();
    List<Stock> getStock(String itemsId);

    List<Category> getAllCategories();
    List<Items> getAllItems();
    List<Stock> getAllStocks();
    List<SubCategory> getAllSubCategory();

    List<Stock> getStockByCategoryId(String categoryId);
    List<Stock> getStockBySubCategoryId(String subCategoryId);

    List<Stock> findStockByCategoryIds(List<String> categoryIds);

    List<Stock> findStockBySubCategoryIdAndItemsId(List<String> subCategoryIds, List<String> itemsIds);

    List<Stock> findStockBySubCategoryIdOrItemsId(List<String> subCategoryIds, List<String> itemsIds);

    Object getStockByCategoryIdThruSubAndItems(String categoryId);

    List<Items> getItemsBySubCategoryIds(List<String> subCategoryIds,String categoryId);

    SubCategory getSubCategoryId(String id);
    Items getItemBySubCategoryId(String id);
    Items getItemByCategoryId(String id);

    List<Category> getCategoriesByListOfIds(List<String> categoryIds);
    List<Stock> getStockByCategoryIds(List<String> categoryIds);


    List<SubCategory> getSubCategoriesByListOfIds(List<String> subCategoryIds);
    List<Stock> getStockByListofSubCategoryIds(List<String> subCategoryIds);



}
