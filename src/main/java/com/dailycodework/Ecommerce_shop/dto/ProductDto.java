package com.dailycodework.Ecommerce_shop.dto;

import com.dailycodework.Ecommerce_shop.model.Category;
import com.dailycodework.Ecommerce_shop.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private String brand;

    private String description;

    private BigDecimal price;

    private int inventory;//stock of product

    private Category category;

    private List<ImageDto> images;

}
