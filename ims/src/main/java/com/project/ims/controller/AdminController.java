package com.project.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AdminController {
	
	@GetMapping("/admin/dashboard")
	public String getDashboardPage(Model model) {
		
		return "/Frontend/Admin/Dashboard/dashboard";
	}
	
	@GetMapping("/admin/order")
	public String getOrderPage(Model model) {
		
		return "/Frontend/Admin/Orders/order";
	}
	
	@GetMapping("/admin/product")
	public String getProductPage(Model model) {
		
		return "/Frontend/Admin/Products/product";
	}
	
	@GetMapping("/admin/categories")
	public String getCategoriesPage(Model model) {
		
		return "/Frontend/Admin/Categories/categories";
	}
	
	@GetMapping("/admin/suppliers")
	public String getSuppliersPage(Model model) {
		
		return "/Frontend/Admin/Suppliers/suppliers";
	}
	
	@GetMapping("/admin/users")
	public String getUsersPage(Model model) {
		
		return "/Frontend/Admin/Users/users";
	}
	
	@GetMapping("/admin/profile")
	public String getProfilePage(Model model) {
		
		return "/Frontend/Admin/admin_profile/admin";
	}
	
}
