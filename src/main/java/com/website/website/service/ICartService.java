package com.website.website.service;

import com.website.website.model.Cart;

import java.util.List;

public interface ICartService
{
    Object addStockToCart(String itemId);
    Object UpdateQuantity(String id, Long count);
    List<Cart> getAllCartStock();
    Object deleteCartItems(String id);
    Object getAllByUserLoggedIn();
    Object getStocksForUserLoggedIn();


}
