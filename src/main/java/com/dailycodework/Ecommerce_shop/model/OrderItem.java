package com.dailycodework.Ecommerce_shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int  quantity;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    public OrderItem(Order order, Product product , int quantity, BigDecimal price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;



}
