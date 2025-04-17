package com.project.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.ims.Model.Products;
import com.project.ims.Repository.ProductRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/dashboard")
	public String getDashboardPage(Model model) {
		
		return "/Frontend/Admin/Dashboard/dashboard";
	}
	
	@GetMapping("/order")
	public String getOrderPage(Model model) {
		
		return "/Frontend/Admin/Orders/order";
	}
	
	@GetMapping("/product")
	public String getProductPage(Model model) {
		List<Products> products = productRepository.findAll();
        model.addAttribute("products", products);
		return "/Frontend/Admin/Products/product";
	}
	
	@GetMapping("/categories")
	public String getCategoriesPage(Model model) {
		
		return "/Frontend/Admin/Categories/categories";
	}
	
	@GetMapping("/suppliers")
	public String getSuppliersPage(Model model) {
		
		return "/Frontend/Admin/Suppliers/suppliers";
	}
	
	@GetMapping("/users")
	public String getUsersPage(Model model) {
		
		return "/Frontend/Admin/Users/users";
	}
	
	@GetMapping("/profile")
	public String getProfilePage(Model model) {
		
		return "/Frontend/Admin/admin_profile/admin";
	}
	
}
