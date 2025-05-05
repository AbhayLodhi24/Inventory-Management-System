package com.project.ims.controller;

import com.project.ims.Model.User;
import com.project.ims.Repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@Valid User user,RedirectAttributes redirectAttributes) {
        
    	User existingUser = userRepository.findByUsername(user.getUsername());
    	User existingEmail= userRepository.findByemail(user.getEmail());
    	
    	if (existingUser != null) {
           
            redirectAttributes.addFlashAttribute("error", "Username '" + user.getUsername() + "' already exists. Please choose another.");
            
            return "redirect:/login#registerContainer"; // Redirect back to login, potentially focusing the register part
        }
    	else if(existingEmail != null) {
    		redirectAttributes.addFlashAttribute("error", "Email '" + user.getEmail() + "' already exists. Please choose another.");
            
            return "redirect:/login#registerContainer";
    		
    	}
    	else {
           
            user.setRole("user");
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful for '" + user.getUsername() + "'! Please log in.");
            return "redirect:/login"; // Redirect to login page after successful registration
        }
    	
    	
    }
}