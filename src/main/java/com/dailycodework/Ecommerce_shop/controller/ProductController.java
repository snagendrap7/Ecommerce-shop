package com.dailycodework.Ecommerce_shop.controller;

import com.dailycodework.Ecommerce_shop.dto.ProductDto;
import com.dailycodework.Ecommerce_shop.exception.ResourceNotFoundException;
import com.dailycodework.Ecommerce_shop.model.Category;
import com.dailycodework.Ecommerce_shop.model.Product;
import com.dailycodework.Ecommerce_shop.request.AddProductRequest;
import com.dailycodework.Ecommerce_shop.request.ProductUpdateRequest;
import com.dailycodework.Ecommerce_shop.response.ApiResponse;
import com.dailycodework.Ecommerce_shop.service.products.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    public final IProductService iProductService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products =iProductService.getAllProducts();
        List<ProductDto> convertedProducts =iProductService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse>getProductById(@PathVariable("productId") Long id){
        try{
            Product product=iProductService.getProductById(id);
            var productDto =iProductService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success",productDto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = iProductService.addProduct(product);
            ProductDto productDto=iProductService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Add product success!",productDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId){
        try {
            Product theProduct = iProductService.updateProduct(request, productId);
            ProductDto productDto=iProductService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Update product success!",productDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try{
            iProductService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!",productId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
        try{
            List<Product> products=iProductService.getProductsByBrandAndName(brandName, productName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndName(@RequestParam Category category,@RequestParam String brand){
        try{
            List<Product> products=iProductService.getProductsByCategoryAndBrand(category , brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/{name}/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndName(@RequestParam String name){
        try{
            List<Product> products=iProductService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try{
            List<Product> products=iProductService.getProductByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
        try{
            List<Product> products=iProductService.getProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error",e.getMessage()));
        }
    }


    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try{
            var productCount = iProductService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!",productCount));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
