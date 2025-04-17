package com.project.ims.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.ims.Model.Products;


@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    
}