package com.project.ims.Service;
 
import com.project.ims.Model.Category;
import com.project.ims.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
 
@Service
public class CategoryService {
 
    @Autowired
    private CategoryRepository categoryRepository;
 
    public List<Category> getAllCategories(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return categoryRepository.searchCategories(keyword);
        } else {
            return categoryRepository.findAll();
        }
    }
 
    public List<Category> getAllCategoriesFromTable() {
        return categoryRepository.findAll();
    }
 
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
 
    public boolean deleteCategory(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
 
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
 
    public Category updateCategory(Category updatedCategory) {
        return categoryRepository.save(updatedCategory);
    }
 
    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }
}

