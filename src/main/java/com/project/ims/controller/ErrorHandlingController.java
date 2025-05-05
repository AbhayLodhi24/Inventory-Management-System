package com.project.ims.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorHandlingController {
	  // --- New method to get the dashboard URL based on role ---
    @GetMapping("/user/get-dashboard-url")
    @ResponseBody // Indicate that the return value should be the response body
    public String getDashboardUrl() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            // Check if the authenticated user has the "ROLE_ADMIN" authority
            // Using authorities directly for a precise check
            boolean isAdmin = auth.getAuthorities().stream()
                                  .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            // Check if the authenticated user has the "ROLE_USER" authority
             boolean isUser = auth.getAuthorities().stream()
                                  .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));


            if (isAdmin) {
                return "/admin/dashboard"; // Return admin dashboard URL
            } else if (isUser) {
                 return "/user/products"; // Return user dashboard URL
            }
            // If authenticated but has neither role (unlikely if roles are assigned correctly)
             return "/login"; // Default or a page indicating role issue
        }

        // If not authenticated (shouldn't reach here if endpoint requires authentication,
        // but as a fallback)
        return "/login"; // Default or login page URL
    }
    
}
