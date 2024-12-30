package com.dailycodework.Ecommerce_shop.request;

import com.dailycodework.Ecommerce_shop.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private String name;

    private String brand;

    private String description;

    private BigDecimal price;

    private int inventory;//stock of product

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

}
