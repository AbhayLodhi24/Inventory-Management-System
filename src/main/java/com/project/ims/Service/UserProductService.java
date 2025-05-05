package com.project.ims.Service;
 
import com.project.ims.Model.Products;
import com.project.ims.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
 
@Service
public class UserProductService {
 
    @Autowired
    private ProductRepository productRepository;
 
    public Optional<Products> getProductById(Integer id) {
        return productRepository.findById(id);
    }
 
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
 
    public Products saveProduct(Products product) {
        return productRepository.save(product);
    }
 
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
 
    public Products updateProduct(Products updatedProduct) {
        return productRepository.save(updatedProduct);
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