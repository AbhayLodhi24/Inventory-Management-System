package com.project.ims.controller;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ims.Model.Category;
import com.project.ims.Model.Products;
import com.project.ims.Model.User;
import com.project.ims.Repository.UserRepository;
import com.project.ims.Service.CategoryService; // Import CategoryService
import com.project.ims.Service.UserOrderService;

//import com.project.ims.Model.Products;
 
import com.project.ims.Service.UserProductService;
 
 
@Controller
@RequestMapping
public class UserProductController {
 
	private static final Logger logger = LoggerFactory.getLogger(UserProductController.class);
 
	@Autowired
    private UserRepository userRepository;
 
    @Autowired
    private UserProductService userProductService;
 
    @Autowired
    private UserOrderService orderService;
    
    @Autowired
    private UserProductService productService;

    @Autowired
    private CategoryService categoryService;
 
	@GetMapping("/user/products")
	public String getProductsPage(Model model) {
		List<Products> products = userProductService.getAllProducts();
 
		Set<String> uniqueCategories = products.stream()
                .map(product -> product.getCategory().getCategoryName())
                .collect(Collectors.toSet());
 
        model.addAttribute("productsUserList", products);
        model.addAttribute("uniqueCategories", uniqueCategories);
        // Retrieve userId
        // Long userId = userService.getUserId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Long userId = user.getUserId();
       if (userId != null) {
        // Assign the fetched User object to currentUser
           model.addAttribute("user", user); // Add user to model
       } else {
           model.addAttribute("user", null); // Ensure 'user' is always in the model
       }  // Add user to model
 
		return "/Frontend/User/Products/products";
	}
	@PostMapping("/user/place-order")
	public String placeOrder(
	        @RequestParam("quantity") Integer quantity,
	        @RequestParam("productName") String productName,
	        @RequestParam("categoryName") String categoryName,
	        @RequestParam("userId") Long userId,
	        Model model) {
	    try {
	        // Now you need to find the Product and Category entities based on their names
	        Products product = productService.findByName(productName);
	        Category category = categoryService.findByCategoryName(categoryName);
 
	        if (product == null || category == null) {
	            model.addAttribute("error", "Failed to place order: Product or Category not found.");
	            return "redirect:/user/products"; // Or handle the error appropriately
	        }
 
	        // Call orderService.placeNewOrder with names, not IDs
	        orderService.placeNewOrder(quantity, productName, categoryName, userId);
	        model.addAttribute("message", "Order placed successfully!");
	        return "redirect:/user/products"; // Redirect to the products page or an order confirmation page
 
	    } catch (Exception e) {
	        logger.error("Error placing order:", e);
	        model.addAttribute("error", "Failed to place order: " + e.getMessage());
	        return "redirect:/user/products"; // Or handle the error appropriately
	    }
	}
}