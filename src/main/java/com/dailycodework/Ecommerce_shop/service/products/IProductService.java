package com.dailycodework.Ecommerce_shop.service.products;

import com.dailycodework.Ecommerce_shop.dto.ProductDto;
import com.dailycodework.Ecommerce_shop.model.Category;
import com.dailycodework.Ecommerce_shop.model.Product;
import com.dailycodework.Ecommerce_shop.request.AddProductRequest;
import com.dailycodework.Ecommerce_shop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(Category category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String category, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);


}
