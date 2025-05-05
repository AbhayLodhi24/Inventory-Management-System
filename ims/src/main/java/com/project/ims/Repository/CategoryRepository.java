package com.project.ims.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import org.springframework.stereotype.Repository;

import com.project.ims.model.Category;
 
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}