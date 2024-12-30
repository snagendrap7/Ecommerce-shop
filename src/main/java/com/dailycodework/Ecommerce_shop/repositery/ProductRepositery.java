package com.dailycodework.Ecommerce_shop.repositery;

import com.dailycodework.Ecommerce_shop.model.Category;
import com.dailycodework.Ecommerce_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositery extends JpaRepository<Product,Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryAndBrand(Category category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
