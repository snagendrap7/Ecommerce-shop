package com.dailycodework.Ecommerce_shop.service.category;

import com.dailycodework.Ecommerce_shop.exception.AlreadyExistsException;
import com.dailycodework.Ecommerce_shop.exception.ResourceNotFoundException;
import com.dailycodework.Ecommerce_shop.model.Category;
import com.dailycodework.Ecommerce_shop.repositery.CategoryRepositery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepositery categoryRepositery;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepositery.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not Found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepositery.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepositery.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepositery.existsByName(c.getName()))
                .map(categoryRepositery :: save )
                .orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exsits")
        );
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
            oldCategory.setName(category.getName());
            return categoryRepositery.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
//        categoryRepositery.deleteById(id);

        categoryRepositery.findById(id).ifPresentOrElse(categoryRepositery::delete,
                ()-> {throw new ResourceNotFoundException("Category not found");});

    }
}
