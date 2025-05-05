package com.project.ims.controller;

import com.project.ims.Dto.UserDto;
import com.project.ims.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("usersList", users);
        model.addAttribute("user", new UserDto());
        System.out.println("Fetching users!!!!!!!!!");
        return "Frontend/Admin/Users/users";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        System.out.println("Inside add user");
     if (result.hasErrors()) {
         System.out.println("Error happened"+result);
            List<UserDto> usersList = userService.getAllUsers();
            model.addAttribute("usersList", usersList);
            model.addAttribute("user", userDto);
            return "Frontend/Admin/Users/users";
        }
        try {
            userService.addUser(userDto);
            System.out.println("Working!!!!");
            return "redirect:/admin/users";
        } catch (IllegalArgumentException e) {
         System.out.println("Inside Catch illegal");
            model.addAttribute("userExistsError", e.getMessage());
            List<UserDto> usersList = userService.getAllUsers();
            model.addAttribute("usersList", usersList);
            model.addAttribute("user", userDto);
            return "Frontend/Admin/Users/users";
        } catch (DataAccessException e) {
         System.out.println("inside dataccess"+e.getMessage());
            model.addAttribute("databaseError", "An error occurred while saving the user.");
            List<UserDto> usersList = userService.getAllUsers();
            model.addAttribute("usersList", usersList);
            model.addAttribute("user", userDto);
            return "Frontend/Admin/Users/users";
        }
    }


    @GetMapping("/search")
    public String searchUsers(@RequestParam("query") String query, Model model) {
        List<UserDto> searchResults = userService.searchUsers(query);
        model.addAttribute("usersList", searchResults);
        model.addAttribute("user", new UserDto());
        model.addAttribute("searchQuery", query);
        return "Frontend/Admin/Users/users";
    }
    



}