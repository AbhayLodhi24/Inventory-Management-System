package com.project.ims.Service;
 
import com.project.ims.Model.Products;
import com.project.ims.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public class UserProductService {
 
    @Autowired
    private ProductRepository productRepository;
 
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
 
    public Products saveProduct(Products product) {
        return productRepository.save(product);
    }
 
    public Products findByName(String name) {
        return productRepository.findByName(name)
                .orElse(null); // Returns null if no product with that name is found
    }
 
    // You can add other product-related methods here, such as:
    // - findByCategory(Category category)
    // - findByPriceRange(double minPrice, double maxPrice)
    // - updateStock(int productId, int newStock)
}