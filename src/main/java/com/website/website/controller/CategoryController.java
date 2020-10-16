package com.website.website.controller;

import com.website.website.dao.ICategoryDao;
import com.website.website.model.*;
import com.website.website.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping(value = "/Category")
public class CategoryController extends BaseController{

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICategoryDao categoryDao;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public Object getUserCategories(String userId)
    {
        return categoryService.getUserCategories(userId);
    }

    @RequestMapping(value = "/{id}/subCategory", method = RequestMethod.GET)
    public SubCategory getSubCategoryById(@PathVariable("id") String subCategoryId)

    {
        return categoryService.getSubCategory(subCategoryId);
    }


    @RequestMapping(value = "/{id}/items", method = RequestMethod.GET)
    public Items getItemsById(@PathVariable("id") String itemsId)
    {
        return categoryService.getItems(itemsId);
    }



    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object addCategory(@RequestBody Category category)
    {
        String userId = getLoggedInUserId();
        return categoryService.addCategory(userId ,category);
    }


    @RequestMapping(value = "/{categoryId}/addSubCategory", method = RequestMethod.POST)
    public SubCategory addSubCategory(@PathVariable("categoryId")  String categoryId,
                           @RequestBody SubCategory subCategory) {
        String userId = this.getLoggedInUserId();
        categoryService.addSubCategory(subCategory,categoryId);
        return null;

    }


    @RequestMapping(value = "/{categoryId}/addItems/{subCategoryId}", method = RequestMethod.POST)
    public SubCategory addSubCategory(@PathVariable("categoryId") String categoryId,@PathVariable("subCategoryId") String subCategoryId,
                                      @RequestBody Items items) {
        String userId = this.getLoggedInUserId();
        categoryService.addItems(items,categoryId,subCategoryId);
        return null;

    }

    @RequestMapping(value = "/{id}/modifyCategory", method = RequestMethod.POST)
    public Object modifyCategory(@PathVariable("id") String categoryId, @RequestBody Category category)
    {
        return categoryService.modifyCategory(categoryId,category);
    }

    @RequestMapping(value = "/{id}/modifySubCategory", method = RequestMethod.POST)
    public Object modifySubCategory(@PathVariable("id") String subCategoryId, @RequestBody SubCategory subCategory)
    {
        return categoryService.modifySubCategory(subCategoryId,subCategory);
    }

    @RequestMapping(value = "/{id}/modifyItems", method = RequestMethod.POST)
    public Object modifyItems(@PathVariable("id") String itemsId, @RequestBody Items items)
    {
        return categoryService.modifyItems(itemsId,items);
    }

    @RequestMapping(value = "/{id}/deleteCategory", method = RequestMethod.DELETE)
    public Object deleteCategory(@PathVariable("id") String categoryId)
    {
        return categoryService.deleteCategory(categoryId);
    }

    @RequestMapping(value = "/{id}/getCategory", method = RequestMethod.GET)
    public Object getCategory(@PathVariable("id") String categoryId)
    {
        return categoryService.getCategoryById(categoryId);
    }

    @RequestMapping(value = "/{id}/getItemsBySubCategoryId", method = RequestMethod.GET)
    public Object getItemsBySubCategoryId(@PathVariable("id") String subCategoryId)
    {
        return categoryService.getItemsBySubCategoryId(subCategoryId);
    }

    @RequestMapping(value = "/{id}/getSubCategoryByCategoryId", method = RequestMethod.GET)
    public Object getSubCategoryByCategoryId(@PathVariable("id") String categoryId)
    {
        return categoryService.getSubCategoryByCategoryId(categoryId);
    }

    @RequestMapping(value = "/{id}/getCategoryByCategoryId", method = RequestMethod.GET)
    public Object getCategoryByCategoryId(@PathVariable("id") String categoryId)
    {
        return categoryService.getCategoryByCategoryId(categoryId);
    }


    @RequestMapping(value = "/{id}/deleteCategoryByCategoryId", method = RequestMethod.DELETE)
    public Object deleteCategoryByCategoryId(@PathVariable("id") String categoryId)
    {
        return categoryService.deleteCategoryByCategoryId(categoryId);
    }

    @RequestMapping(value = "/{id}/deleteSubCategoryBySubCategoryId", method = RequestMethod.DELETE)
    public Object deleteSubCategoryBySubCategoryId(@PathVariable("id") String subCategoryId)
    {
        return categoryService.deleteSubCategoryBySubCategoryId(subCategoryId);
    }

    @RequestMapping(value = "/{id}/deleteItemsByItemsId", method = RequestMethod.DELETE)
    public Object deleteItemsByItemsId(@PathVariable("id") String itemsId)
    {
        return categoryService.deleteItemsByItemsId(itemsId);
    }

    @RequestMapping(value = "/{name}",method = RequestMethod.GET)
    public Object search(@PathVariable("name") String name)
    {
        return categoryService.searchCatSubCatItems(name);
    }


    @RequestMapping(value = "/items/{name}",method = RequestMethod.GET)
    public Object searchOnlyItems(@PathVariable("name") String name)

    {
        return categoryService.searchOnlyItems(name);
    }

    @RequestMapping(value = "/{id}/getSubCatAndItems", method = RequestMethod.GET)
    public Object getSubCatAndItems(@PathVariable("id") String categoryId)
    {
        return categoryService.getSubCatAndItems(categoryId);
    }

    @RequestMapping(value = "/getItemsForUser", method = RequestMethod.GET)
    public Object getItemsForUser()
    {
        return categoryService.getItemsForUser();
    }

    @RequestMapping(value = "/{itemsId}/addStock/{categoryId}/{subCategoryId}", method = RequestMethod.POST)
    public Items addStock(@PathVariable("itemsId")  String itemsId,
                                      @RequestBody Stock stock, @PathVariable("categoryId") String categoryId,@PathVariable("subCategoryId") String subCategoryId) {
        categoryService.addStock(itemsId,categoryId, stock, subCategoryId);
        return null;
    }

    @RequestMapping(value = "/{id}/getStockByItemsId", method = RequestMethod.GET)
    public Object getStockByItemsId(@PathVariable("id") String itemsId)
    {
        return categoryService.getStock(itemsId);
    }

//    @RequestMapping(value = "/photo/add", method = RequestMethod.POST)
//    public String addPhoto(@RequestParam("name") String name, @RequestParam("image") MultipartFile image, Model model ) throws IOException
//    {
//        String id = categoryService.addPhoto(name,image);
//        return "added with : " + id;
//    }
//
//    @RequestMapping(value = "photos/{id}", method = RequestMethod.GET)
//    public String getPhoto(@PathVariable String id, Model model)
//    {
//        Stock stock1 = categoryService.getPhoto(id);
//        model.addAttribute("name", stock1.getId());
//        model.addAttribute("image", Base64.getEncoder().encodeToString(stock1.getImage().getData()));
//        return "photos";
//    }

    @RequestMapping(value = "/addPhoto", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object addPhoto(@RequestBody String image )
    {
        return categoryService.uploadPhoto(image);
    }


   @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object getAll()
   {
       return categoryService.getAll();
   }

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET)
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/getAllSubCategory", method = RequestMethod.GET)
    public List<SubCategory> getAllSubCategory()
    {
        return categoryService.getAllSubCategory();
    }

    @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
    public List<Items> getAllItems()
    {
        return categoryService.getAllItems();
    }

    @RequestMapping(value = "/getAllStocks", method = RequestMethod.GET)
    public List<Stock> getAllStocks()
    {
        return categoryService.getAllStocks();
    }

    @RequestMapping(value = "/{id}/getStockByCategoryId", method = RequestMethod.GET)
    public Object getStockByCategoryId(@PathVariable("id") String categoryId)
    {
        return categoryService.getStockByCategoryId(categoryId);
    }

    @RequestMapping(value = "/{id}/getStockBySubCategoryId", method = RequestMethod.GET)
    public Object getStockBySubCategoryId(@PathVariable("id") String subCategoryId)
    {
        return categoryService.getStockBySubCategoryId(subCategoryId);
    }


    @RequestMapping(value="/findStockByCategoryIds", method = RequestMethod.GET)
    public List<Stock>  findStockByCategoryIds(@RequestBody List<String> categoryIds)
    {
         return categoryService.findStockByCategoryIds(categoryIds);
    }

    @RequestMapping(value="/findStockBySubCategoryIdAndItemsId", method = RequestMethod.GET)
    public List<Stock>  findStockBySubCategoryIdAndItemsId(@RequestParam List<String> subCategoryIds,@RequestParam List<String> itemsIds,@RequestParam String categoryId)
    {
        return categoryService.findStockBySubCategoryIdAndItemsId(subCategoryIds, itemsIds,categoryId);
    }

    @RequestMapping(value = "/{id}/getStockByCategoryIdThruSubAndItems", method = RequestMethod.GET)
    public Object getStockByCategoryIdThruSubAndItems(@PathVariable("id") String categoryId)
    {
        return categoryDao.getStockByCategoryIdThruSubAndItems(categoryId);
    }

    @RequestMapping(value = "/getItemsByCategoryIds", method = RequestMethod.GET)
    public Object getItemsByCategoryIds(@RequestParam List<String> subCategoryIds, String categoryId)
    {
        return categoryDao.getItemsBySubCategoryIds(subCategoryIds, categoryId);
    }

    
    @RequestMapping(value = "/getCategoryAndStockByListOfCategoryIds", method = RequestMethod.GET)
    public Object getCategoryAndStockByListOfCategoryIds(@RequestParam List<String> categoryIds,@RequestParam List<String> subCategoryIds)
    {
        return categoryService.getCategoryAndStockByListOfCategoryIds(categoryIds,subCategoryIds);
    }
}

