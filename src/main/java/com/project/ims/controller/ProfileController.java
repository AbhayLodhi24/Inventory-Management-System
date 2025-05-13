package com.project.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.ims.Dto.UserProfileUpdateForm;
import com.project.ims.Model.User;
import com.project.ims.Repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class ProfileController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@GetMapping("/admin/profile")
    public String getProfilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        if (user != null) {
            model.addAttribute("user", user);

             // Create and pass a DTO for the form, populating it with admin user data
            // If the form object is already in the model (e.g., from a redirect after validation error),
            // it means a previous submission failed, and Thymeleaf will use the data from the flash attribute.
            if (!model.containsAttribute("userProfileUpdateForm")) {
                UserProfileUpdateForm form = new UserProfileUpdateForm();
                form.setUserId(user.getUserId());
                form.setUsername(user.getUsername());
                form.setEmail(user.getEmail());
                form.setAddress(user.getAddress());
                model.addAttribute("userProfileUpdateForm", form);
            } else {
                 // If the form object is already present (from redirect), it means
                 // there were validation errors. The 'user' object is still added
                 // to the model, although th:object will bind to userProfileUpdateForm.
                 // Adding the original user just in case it's needed for other parts of the page.
                  model.addAttribute("user", user);
            }
        }
        return "Frontend/Admin/admin_profile/admin"; // Ensure this matches your template path
    }
	
	@GetMapping("/user/profile")
    public String getUserProfilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username of the logged-in user
        User user = userRepository.findByUsername(username);

        if (user != null) {
            model.addAttribute("user", user);

            if (!model.containsAttribute("userProfileUpdateForm")) {
                UserProfileUpdateForm form = new UserProfileUpdateForm();
                form.setUserId(user.getUserId());
                form.setUsername(user.getUsername());
                form.setEmail(user.getEmail());
                form.setAddress(user.getAddress());
                model.addAttribute("userProfileUpdateForm", form);
            } else {
                  model.addAttribute("user", user);
            }
        }
        return "/Frontend/User/Profile/profile";
    }
	

    @PostMapping("/admin/profile/update")
    public String updateAdminProfile(@Valid UserProfileUpdateForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // **Server-side Bean Validation**
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateForm", bindingResult);
            redirectAttributes.addFlashAttribute("userProfileUpdateForm", form);
            return "redirect:/admin/profile";
        }

        // **Check for Duplicate Username/Email (Excluding Current Admin User)**
        User currentAdminUser = userRepository.findById(form.getUserId()).orElse(null);

        if (currentAdminUser != null) {
            // Check for duplicate username, excluding the current admin user
            User existingUserWithSameUsername = userRepository.findByUsername(form.getUsername());
            if (existingUserWithSameUsername != null && !existingUserWithSameUsername.getUserId().equals(currentAdminUser.getUserId())) {
                 // Add the error message and the form data back to flash attributes
                 redirectAttributes.addFlashAttribute("error", "Username '" + form.getUsername() + "' already exists. Please choose another.");
                 redirectAttributes.addFlashAttribute("userProfileUpdateForm", form); // Pass form back to retain data
                 return "redirect:/admin/profile"; // Redirect back to the profile page
            }

            // Check for duplicate email, excluding the current admin user
            User existingUserWithSameEmail = userRepository.findByemail(form.getEmail());
            if (existingUserWithSameEmail != null && !existingUserWithSameEmail.getUserId().equals(currentAdminUser.getUserId())) {
                 // Add the error message and the form data back to flash attributes
                 redirectAttributes.addFlashAttribute("error", "Email '" + form.getEmail() + "' already exists. Please choose another.");
                 redirectAttributes.addFlashAttribute("userProfileUpdateForm", form); // Pass form back to retain data
                 return "redirect:/admin/profile"; // Redirect back to the profile page
            }

            // If Bean Validation and Duplicate Checks Pass, Update Admin User Details
            currentAdminUser.setUsername(form.getUsername());
            currentAdminUser.setEmail(form.getEmail());
            currentAdminUser.setAddress(form.getAddress());
            userRepository.save(currentAdminUser);

            // Update security context if username changed
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             if (!authentication.getName().equals(form.getUsername())) {
                 UserDetails userDetails = userDetailsService.loadUserByUsername(form.getUsername());
                 Authentication newAuth = new UsernamePasswordAuthenticationToken(
                         userDetails,
                         authentication.getCredentials(),
                         userDetails.getAuthorities()
                 );
                 SecurityContextHolder.getContext().setAuthentication(newAuth);
             }

            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        } else {
             redirectAttributes.addFlashAttribute("error", "Could not find admin profile.");
        }

        // Redirect to the profile page after successful update or if user not found
        return "redirect:/admin/profile";
    }
    @PostMapping("/user/profile/update")
    public String updateUserProfile(@Valid UserProfileUpdateForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateForm", bindingResult);
            redirectAttributes.addFlashAttribute("userProfileUpdateForm", form);
            return "redirect:/user/profile";
        }

        User currentUser = userRepository.findById(form.getUserId()).orElse(null);

        if (currentUser != null) {
            User existingUserWithSameUsername = userRepository.findByUsername(form.getUsername());
            if (existingUserWithSameUsername != null && !existingUserWithSameUsername.getUserId().equals(currentUser.getUserId())) {
                 redirectAttributes.addFlashAttribute("error", "Username '" + form.getUsername() + "' already exists. Please choose another.");
                 redirectAttributes.addFlashAttribute("userProfileUpdateForm", form);
                 return "redirect:/user/profile";
            }

            User existingUserWithSameEmail = userRepository.findByemail(form.getEmail());
            if (existingUserWithSameEmail != null && !existingUserWithSameEmail.getUserId().equals(currentUser.getUserId())) {
                 redirectAttributes.addFlashAttribute("error", "Email '" + form.getEmail() + "' already exists. Please choose another.");
                 redirectAttributes.addFlashAttribute("userProfileUpdateForm", form);
                 return "redirect:/user/profile";
            }

            currentUser.setUsername(form.getUsername());
            currentUser.setEmail(form.getEmail());
            currentUser.setAddress(form.getAddress());
            userRepository.save(currentUser);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             if (!authentication.getName().equals(form.getUsername())) {
                 UserDetails userDetails = userDetailsService.loadUserByUsername(form.getUsername());
                 Authentication newAuth = new UsernamePasswordAuthenticationToken(
                         userDetails,
                         authentication.getCredentials(),
                         userDetails.getAuthorities()
                 );
                 SecurityContextHolder.getContext().setAuthentication(newAuth);
             }

            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        } else {
             redirectAttributes.addFlashAttribute("error", "Could not find user profile.");
        }

        return "redirect:/user/profile";
    }

}
