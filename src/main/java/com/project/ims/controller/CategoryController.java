package com.project.ims.controller;
 
import com.project.ims.Model.Category;
import com.project.ims.Dto.CategoryDto;
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
        return "Frontend/Admin/Categories/categories";
    }
 
    @PostMapping("/addCategory")
    public String addCategory(@Valid @ModelAttribute("cat") CategoryDto categoryDTO,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "Frontend/Admin/Categories/categories :: addCategoryModalContent";
        }
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        try {
            categoryService.saveCategory(category);
            return "redirect:/admin/categories?message=Category added successfully";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("nameError", "Category with this name already exists!");
            return "Frontend/Admin/Categories/categories :: addCategoryModalContent";
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
 
    @PostMapping("/editCategory")
    public String editCategory(@RequestParam(value = "id", required = false) Integer id, // To differentiate show/update
                               @Valid @ModelAttribute("cat") CategoryDto categoryDTO,
                               BindingResult bindingResult,
                               Model model) {
        if (id != null) {
            // Logic to fetch and populate the edit modal (if needed server-side)
            Optional<Category> categoryOptional = categoryService.getCategoryById(id);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                categoryDTO.setCategoryId(category.getCategoryId());
                categoryDTO.setCategoryName(category.getCategoryName());
                categoryDTO.setCategoryDescription(category.getCategoryDescription());
                model.addAttribute("cat", categoryDTO);
                return "Frontend/Admin/Categories/categories :: editCategoryModalContent"; // Return fragment to update modal
            } else {
                return "redirect:/admin/categories?error=Category not found";
            }
        }
 
        // If no ID is provided in the POST, assume it's an update submission
        if (bindingResult.hasErrors()) {
            return "Frontend/Admin/Categories/categories :: editCategoryModalContent";
        }
 
        if (categoryDTO.getCategoryId() == null) {
            return "redirect:/admin/categories?error=Invalid category ID for update";
        }
 
        Optional<Category> existingCategoryOptional = categoryService.getCategoryById(categoryDTO.getCategoryId());
        if (!existingCategoryOptional.isPresent()) {
            return "redirect:/admin/categories?error=Category not found";
        }
 
        Category existingCategory = existingCategoryOptional.get();
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        existingCategory.setCategoryDescription(categoryDTO.getCategoryDescription());
 
        try {
            categoryService.updateCategory(existingCategory);
            return "redirect:/admin/categories?message=Category updated successfully";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("editNameError", "Category with this name already exists!");
            return "Frontend/Admin/Categories/categories :: editCategoryModalContent";
        }
    }
}