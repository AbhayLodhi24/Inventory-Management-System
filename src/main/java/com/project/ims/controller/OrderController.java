package com.project.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.ims.Model.Orders;
import com.project.ims.Model.User;
import com.project.ims.Repository.UserRepository; // Import UserRepository
import com.project.ims.Service.UserOrderService;
// import com.project.ims.Service.UserService; // No longer strictly needed for getting ID here

// Added logging for debugging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // @Autowired // No longer needed for getting userId in this method
    // private UserService userService;

    @Autowired
    private UserRepository userRepository; // Keep for finding the User entity

    @Autowired
    private UserOrderService orderService; // Inject OrderService

    @GetMapping("/user/orders")
    public String viewUserOrders(Model model) {

        // Get the currently authenticated user's details from Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // This is the logged-in username

        // Find the User entity in the database using the username
        // Assumes UserRepository has a findByUsername method
        User user = userRepository.findByUsername(username);

        if (user != null) {
            Long userId = user.getUserId(); // Get the userId from the found User entity

            // Use the OrderService to get user's orders by their ID
            List<Orders> userOrders = orderService.getOrdersByUserId(userId);

            // Add the list of orders and the user object to the model
            model.addAttribute("ordersList", userOrders);
            model.addAttribute("user", user); // Add user to model (useful for sidebar etc.)

            // Log the number of orders found for debugging
            logger.info("Found {} orders for user: {}", userOrders.size(), username);


        } else {
             // This case should ideally not happen for an authenticated user,
             // but log an error if the user from SecurityContext isn't found in DB
             logger.error("Authenticated user '{}' from SecurityContext not found in UserRepository.", username);
             // Optionally add an error message to the model or redirect
             // model.addAttribute("errorMessage", "Could not retrieve user details.");
             // return "error-page"; // Example: return a generic error view
             // Or redirect: return "redirect:/logout"; // Force logout and re-auth
        }


        return "/Frontend/User/Orders/orders"; // Return the view name

    }

}