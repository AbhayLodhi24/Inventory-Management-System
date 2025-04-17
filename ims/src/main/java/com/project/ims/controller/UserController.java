package com.project.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {
	
	@GetMapping("/user/orders")
	public String getOrdersPage(Model model) {
		return "/Frontend/User/Orders/orders";
	}
	
	@GetMapping("/user/products")
	public String getProductsPage(Model model) {
		return "/Frontend/User/Products/products";
	}
	
	@GetMapping("/user/profile")
	public String getProfilePage(Model model) {
		return "/Frontend/User/Profile/profile";
	}
	
	
}
