package com.project.ims.Service;

import com.project.ims.Dto.ProductDto;
import com.project.ims.Model.Category;
import com.project.ims.Model.Products;
import com.project.ims.Model.Supplier;
import com.project.ims.Repository.CategoryRepository;
import com.project.ims.Repository.ProductRepository;
import com.project.ims.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public List<ProductDto> getAllProducts() {
    	List<Products> productsList = productRepository.findAll();
    	List<ProductDto> productDtos = new ArrayList<>();
    	for(Products prod : productsList) {
    		productDtos.add(convertToDto(prod));
    	}
    	return productDtos;
    }

    @Transactional
    public void addProduct(ProductDto productDto) {
        Optional<Products> existingProductOptional = productRepository.findByNameAndCategoryIdAndSupplierIdAndNotId(
                productDto.getName(),
                productDto.getCategoryId(),
                productDto.getSupplierId(),
                productDto.getProductId()
        );

        if (existingProductOptional.isPresent()) {
            throw new IllegalArgumentException("A product with the same name, category, and supplier already exists.");
        }

        Products product = new Products();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Category not found with ID: " + productDto.getCategoryId());
                });
        product.setCategory(category);

        Supplier supplier = supplierRepository.findById(productDto.getSupplierId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Supplier not found with ID: " + productDto.getSupplierId());
                });
        product.setSupplier(supplier);

        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {
        Optional<Products> existingProductOptional = productRepository.findById(productDto.getProductId());

        if (!existingProductOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found with ID: " + productDto.getProductId());
        }

        Products product = existingProductOptional.get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        // Fetch the Category entity
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + productDto.getCategoryId()));
        product.setCategory(category);

        // Fetch the Supplier entity
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found with ID: " + productDto.getSupplierId()));
        product.setSupplier(supplier);

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
    
    public List<ProductDto> searchProducts(String query) {
    	List<Products> productsList = productRepository.findByNameContainingIgnoreCase(query);
    	List<ProductDto> productDtos = new ArrayList<>();
    	for(Products prod : productsList) {
    		productDtos.add(convertToDto(prod));
    	}
    	return productDtos;
    }
    
    private ProductDto convertToDto(Products product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());

        dto.setCategory(product.getCategory()); // Set the Category object
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryId());
            dto.setCategoryName(product.getCategory().getCategoryName());
        } else {
            dto.setCategoryId(null);
            dto.setCategoryName(null);
        }

        dto.setSupplier(product.getSupplier()); // Set the Supplier object
        if (product.getSupplier() != null) {
            dto.setSupplierId(product.getSupplier().getSupplierId());
            dto.setSupplierName(product.getSupplier().getSupplierName());
        } else {
            dto.setSupplierId(null);
            dto.setSupplierName(null);
        }
        return dto;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId));
    }

    public Supplier getSupplierById(int supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found with ID: " + supplierId));
    }
}