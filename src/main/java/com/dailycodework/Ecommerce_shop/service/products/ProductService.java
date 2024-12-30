package com.dailycodework.Ecommerce_shop.service.products;

import com.dailycodework.Ecommerce_shop.dto.ImageDto;
import com.dailycodework.Ecommerce_shop.dto.ProductDto;
import com.dailycodework.Ecommerce_shop.exception.ProductNotFoundException;
import com.dailycodework.Ecommerce_shop.model.Category;
import com.dailycodework.Ecommerce_shop.model.Image;
import com.dailycodework.Ecommerce_shop.model.Product;
import com.dailycodework.Ecommerce_shop.repositery.CategoryRepositery;
import com.dailycodework.Ecommerce_shop.repositery.ImageRepository;
import com.dailycodework.Ecommerce_shop.repositery.ProductRepositery;
import com.dailycodework.Ecommerce_shop.request.AddProductRequest;
import com.dailycodework.Ecommerce_shop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepositery productRepositery;

    private final CategoryRepositery categoryRepositery;

    private final ImageRepository imageRepository;

    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest product) {
        //check if the category is found in the DB
        //if yes, set it as the new product category
        //if No, then save it as a new category
        //The set as the new product category.

        Category category= Optional.ofNullable(categoryRepositery.findByName(product.getCategory().getName())).orElseGet(()->{
            return categoryRepositery.save(new Category(product.getCategory().getName()));
        });
        product.setCategory(category);
        return productRepositery.save(createProduct(product,category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepositery.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepositery.findById(id).ifPresentOrElse(productRepositery::delete,()->{
            throw new ProductNotFoundException("Product not found");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepositery.findById(productId)
                .map(existingProduct->updateExistingProduct(existingProduct,request))
                .map(productRepositery :: save)
                .orElseThrow(()->new ProductNotFoundException("Product not Found!"));
    }

    private Product updateExistingProduct(Product existingProduct,ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setName(request.getName());

        Category category=categoryRepositery.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;


    }

    @Override
    public List<Product> getAllProducts() {
        return productRepositery.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepositery.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepositery.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(Category category, String brand) {
        return productRepositery.findByCategoryAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepositery.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepositery.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepositery.countProductsByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image->modelMapper.map(image,ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
