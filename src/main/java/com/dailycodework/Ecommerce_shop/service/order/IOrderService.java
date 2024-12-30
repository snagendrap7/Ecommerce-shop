package com.dailycodework.Ecommerce_shop.service.order;

import com.dailycodework.Ecommerce_shop.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);

    Order getOrder(long orderId);



}
