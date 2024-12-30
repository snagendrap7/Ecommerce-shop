package com.dailycodework.Ecommerce_shop.service.cart;

import com.dailycodework.Ecommerce_shop.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewCArt();

    Cart getCartByUserId(Long userId);

}
