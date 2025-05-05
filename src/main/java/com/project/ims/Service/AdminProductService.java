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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAllWithCategoryAndSupplier().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addProduct(ProductDto productDto) {
        System.out.println("addProduct service called with DTO: " + productDto); // Log DTO in service
        Optional<Products> existingProductOptional = productRepository.findByNameAndCategoryIdAndSupplierIdAndNotId(
                productDto.getName(),
                productDto.getCategoryId(),
                productDto.getSupplierId(),
                productDto.getProductId()
        );

        if (existingProductOptional.isPresent()) {
            System.out.println("Duplicate product found: " + productDto.getName() + ", Category ID: " + productDto.getCategoryId() + ", Supplier ID: " + productDto.getSupplierId()); // Log duplicate
            throw new IllegalArgumentException("A product with the same name, category, and supplier already exists.");
        }

        Products product = new Products();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        System.out.println("Attempting to fetch Category with ID: " + productDto.getCategoryId()); // Log category fetch attempt
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> {
                    System.err.println("Category not found with ID: " + productDto.getCategoryId()); // Log category not found
                    return new IllegalArgumentException("Category not found with ID: " + productDto.getCategoryId());
                });
        product.setCategory(category);
        System.out.println("Category fetched: " + category); // Log fetched category

        System.out.println("Attempting to fetch Supplier with ID: " + productDto.getSupplierId()); // Log supplier fetch attempt
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId())
                .orElseThrow(() -> {
                    System.err.println("Supplier not found with ID: " + productDto.getSupplierId()); // Log supplier not found
                    return new IllegalArgumentException("Supplier not found with ID: " + productDto.getSupplierId());
                });
        product.setSupplier(supplier);
        System.out.println("Supplier fetched: " + supplier); // Log fetched supplier

        System.out.println("Saving product: " + product); // Log product before saving
        productRepository.save(product);
        System.out.println("Product saved successfully."); // Log after saving
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
        return productRepository.findByNameContainingIgnoreCase(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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