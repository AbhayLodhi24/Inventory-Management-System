package com.project.ims.Repository;

import com.project.ims.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE " +
            "LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.categoryDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Category> searchCategories(@Param("keyword") String keyword);

    default Page<Category> searchCategoriesWithPagination(String searchTerm, Pageable pageable) {
        List<Category> categories = searchCategories(searchTerm);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), categories.size());
        return new PageImpl<>(categories.subList(start, end), pageable, categories.size());
    }

    Category findByCategoryName(String categoryName);
}