package com.dailycodework.Ecommerce_shop.repositery;

import com.dailycodework.Ecommerce_shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
}
