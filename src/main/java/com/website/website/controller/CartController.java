package com.website.website.controller;


import com.website.website.dao.ICartDao;
import com.website.website.jwt.JwtUser;
import com.website.website.model.*;
import com.website.website.service.ICartService;
import com.website.website.service.ICategoryService;
import com.website.website.service.IUserService;
import com.website.website.vo.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/Cart")
public class CartController extends BaseController {

    @Autowired
    private ICartService cartService;
    @Autowired
    private ICartDao cartDao;


    @RequestMapping(value = "/{id}/addStockToCart", method = RequestMethod.POST)
    public Object addStock(@PathVariable("id")  String id) {
        return cartService.addStockToCart(id);

    }

    @RequestMapping(value = "/{id}/{count}/UpdateQuantity", method = RequestMethod.POST)
    public Object UpdateQuantity(@PathVariable("id")  String id,@PathVariable("count") Long count)
    {
        return cartService.UpdateQuantity(id,count);
    }

    @RequestMapping(value = "/getAllCartStock", method = RequestMethod.GET)
    public Object getAllCartStock()
    {
         List<Cart> l = cartService.getAllCartStock();
         double totalPrice = 0;
         double totalQuntity =0;

         for(int i =0;i<l.size();i++)
         {
             totalPrice += l.get(i).getPrice()*l.get(i).getQuantity();
             totalQuntity += l.get(i).getQuantity();
         }
         return Arrays.asList("no of Items: "+ l.size(),l,"total amount: "+totalPrice,"totalQuntity: "+ totalQuntity);

    }

    @RequestMapping(value = "/{id}/AddQuantity", method = RequestMethod.POST)
    public Object AddQuantity(@PathVariable("id") String id, Long count)
    {
        Cart cart = cartDao.findByCartID(id);
        Long quan = cart.getQuantity()+1;
        cart.setQuantity(quan);
        return cartDao.updateQuantityCountBasedOnCartId(id,cart.setQuantity(quan));
    }


    @RequestMapping(value = "/{id}/SubQuantity", method = RequestMethod.POST)
    public Object SubQuantity(@PathVariable("id") String id, Long count)
    {
        Cart cart = cartDao.findByCartID(id);
        Long quan = cart.getQuantity()-1;
        cart.setQuantity(quan);
        return cartDao.updateQuantityCountBasedOnCartId(id,cart.setQuantity(quan));
    }



    @RequestMapping(value = "/{id}/deleteCartItems", method = RequestMethod.DELETE)
    public Object deleteCartItems(@PathVariable("id") String id)
    {
        return cartService.deleteCartItems(id);
    }

    @RequestMapping(value = "/getAllByUserLoggedIn", method = RequestMethod.GET)
    public Object getAllByUserLoggedIn()
    {
        return cartService.getAllByUserLoggedIn();
    }

    @RequestMapping(value = "/getStocksForUserLoggedIn", method = RequestMethod.GET)
    public Object getStocksForUserLoggedIn()
    {
        return cartService.getStocksForUserLoggedIn();
    }

}

