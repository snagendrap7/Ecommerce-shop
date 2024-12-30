package com.dailycodework.Ecommerce_shop.service.category;

import com.dailycodework.Ecommerce_shop.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);

    Category getCategoryByName(String Name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Category category,Long Id);

    void deleteCategoryById(Long id);

}
