package com.dailycodework.Ecommerce_shop.controller;

import com.dailycodework.Ecommerce_shop.exception.AlreadyExistsException;
import com.dailycodework.Ecommerce_shop.model.Category;
import com.dailycodework.Ecommerce_shop.response.ApiResponse;
import com.dailycodework.Ecommerce_shop.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="${api.prefix}/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService iCategoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try{
            List<Category> categories=iCategoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try{
            Category theCategory=iCategoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success",theCategory));
        }
        catch(AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try {
            Category thecaCategory = iCategoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", thecaCategory));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category thecaCategory = iCategoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", thecaCategory));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            iCategoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Delete Success", null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try {
            Category thecaCategory = iCategoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Update Success", thecaCategory));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
