package com.dailycodework.Ecommerce_shop.repositery;

import com.dailycodework.Ecommerce_shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepositery extends JpaRepository<Category,Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
