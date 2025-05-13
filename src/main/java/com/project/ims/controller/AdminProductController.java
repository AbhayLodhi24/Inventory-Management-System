package com.project.ims.controller;

import com.project.ims.Dto.ProductDto;
import com.project.ims.Service.AdminProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private AdminProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("productsList", products);
        model.addAttribute("prod", new ProductDto());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("suppliers", productService.getAllSuppliers());
        return "Frontend/Admin/Products/product";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid @ModelAttribute("prod") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<ProductDto> productsList = productService.getAllProducts();
            model.addAttribute("productsList", productsList);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("suppliers", productService.getAllSuppliers());
            model.addAttribute("prod", productDto);
            return "Frontend/Admin/Products/product";
        }

        try {
            productService.addProduct(productDto);
            return "redirect:/admin/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("productExistsError", e.getMessage());
            List<ProductDto> productsList = productService.getAllProducts();
            model.addAttribute("productsList", productsList);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("suppliers", productService.getAllSuppliers());
            model.addAttribute("prod", productDto);
            return "Frontend/Admin/Products/product";
        } catch (DataAccessException e) {
            e.printStackTrace();
            String databaseErrorMessage = "An error occurred while saving the product.";
            if (e.getRootCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) e.getRootCause();
                if (sqlException.getSQLState().startsWith("72") && sqlException.getErrorCode() == 12899) {
                    databaseErrorMessage = "The product name is too long (maximum 255 characters).";
                }
            }
            model.addAttribute("databaseError", databaseErrorMessage);
            List<ProductDto> productsList = productService.getAllProducts();
            model.addAttribute("productsList", productsList);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("suppliers", productService.getAllSuppliers());
            model.addAttribute("prod", productDto);
            return "Frontend/Admin/Products/product";
        }
    }

    @PostMapping("/editProduct")
    public String editProduct(@Valid @ModelAttribute("prod") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<ProductDto> productsList = productService.getAllProducts();
            model.addAttribute("productsList", productsList);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("suppliers", productService.getAllSuppliers());
            model.addAttribute("prod", productDto);
            return "Frontend/Admin/Products/product";
        }
        try {
            productService.updateProduct(productDto);
            return "redirect:/admin/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("productExistsError", e.getMessage());
            List<ProductDto> productsList = productService.getAllProducts();
            model.addAttribute("productsList", productsList);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("suppliers", productService.getAllSuppliers());
            model.addAttribute("prod", productDto);
            return "Frontend/Admin/Products/product";
        } catch (DataAccessException e) {
            e.printStackTrace();
            String databaseErrorMessage = "An error occurred while updating the product.";
            if (e.getRootCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) e.getRootCause();
                if (sqlException.getSQLState().startsWith("72") && sqlException.getErrorCode() == 12899) {
                    databaseErrorMessage = "The product name is too long (maximum 255 characters).";
                }
            }
            model.addAttribute("databaseError", databaseErrorMessage);
            List<ProductDto> productsList = productService.getAllProducts();
            model.addAttribute("productsList", productsList);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("suppliers", productService.getAllSuppliers());
            model.addAttribute("prod", productDto);
            return "Frontend/Admin/Products/product";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/products";
    }
    
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<ProductDto> searchResults = productService.searchProducts(query);
        model.addAttribute("productsList", searchResults);
        model.addAttribute("prod", new ProductDto()); // For the add/edit modal
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("suppliers", productService.getAllSuppliers());
        model.addAttribute("searchQuery", query); // To display the search query in the view
        return "Frontend/Admin/Products/product";
    }

    @GetMapping("/existingNames")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getExistingProductDetails() {
        List<ProductDto> allProducts = productService.getAllProducts();
        List<Map<String, Object>> existingDetails = allProducts.stream()
                .map(product -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("productId", product.getProductId());
                    details.put("name", product.getName());
                    details.put("supplierId", product.getSupplierId());
                    details.put("categoryId", product.getCategoryId());
                    return details;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(existingDetails);
    }
}