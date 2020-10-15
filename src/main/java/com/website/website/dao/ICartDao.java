package com.website.website.dao;

import com.website.website.model.Cart;
import com.website.website.model.Stock;

import java.util.List;

public interface ICartDao {

    Stock findByStockId(String id);
    Cart findByCartID(String id);
    Cart findByStockIdInCart(String id);
    Object updateQuantityCount(String id, Long count);
    List<Cart> getAllCartStock();
    Cart findCartItemStockId(String id);
    Object updateQuantityCountBasedOnCartId(String id, Long count);
    Object deleteCartItems(String id);


}
