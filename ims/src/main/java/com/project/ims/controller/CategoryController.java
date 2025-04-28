package com.project.ims.controller;

import com.project.ims.Model.Category;
import com.project.ims.Model.CategoryDto;
import com.project.ims.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(@RequestParam(value = "query", required = false) String query,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryService.getAllCategories(query, pageable);
        model.addAttribute("categoriesList", categoryPage.getContent());
        model.addAttribute("currentPage", categoryPage.getNumber());
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("totalItems", categoryPage.getTotalElements());
        model.addAttribute("query", query);
        model.addAttribute("cat", new CategoryDto()); // For the add/edit modal form
        return "Frontend/Admin/Category";
    }

    @PostMapping("/addCategory")
    public String addCategory(@Valid @ModelAttribute("cat") CategoryDto categoryDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "Frontend/Admin/Category :: addCategoryModalContent";
        }
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategory_description(categoryDTO.getCategoryDescription());
        try {
            categoryService.saveCategory(category);
            return "redirect:/admin/categories?message=Category added successfully";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("nameError", "Category with this name already exists!");
            return "Frontend/Admin/Category :: addCategoryModalContent";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Category deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Category not found!");
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable int id,
                                 @Valid @ModelAttribute("cat") CategoryDto categoryDTO,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "Frontend/Admin/Category :: editCategoryModalContent";
        }
        Optional<Category> existingCategoryOptional = categoryService.getCategoryById(id);
        if (!existingCategoryOptional.isPresent()) {
            return "redirect:/admin/categories?error=Category not found";
        }
        Category existingCategory = existingCategoryOptional.get();
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        existingCategory.setCategory_description(categoryDTO.getCategoryDescription());
        try {
            categoryService.updateCategory(existingCategory);
            return "redirect:/admin/categories?message=Category updated successfully";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("editNameError", "Category with this name already exists!");
            return "Frontend/Admin/Category :: editCategoryModalContent";
        }
    }
}