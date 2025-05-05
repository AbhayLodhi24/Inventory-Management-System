package com.project.ims.Repository;

import com.project.ims.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

    Optional<Products> findByName(String name);

    @Query("SELECT p FROM Products p WHERE p.name = :name AND p.category.categoryId = :categoryId AND p.supplier.supplierId = :supplierId AND p.productId <> :productId")
    Optional<Products> findByNameAndCategoryIdAndSupplierIdAndNotId(
            @Param("name") String name,
            @Param("categoryId") Integer categoryId,
            @Param("supplierId") Integer supplierId,
            @Param("productId") Integer productId
    );
    
    @Query("SELECT p FROM Products p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.supplier ORDER BY p.productId")
    List<Products> findAllWithCategoryAndSupplier();

    Optional<Products> findById(Integer productId);
    
    List<Products> findByNameContainingIgnoreCase(String name); // Method name-based query

    @Query("SELECT SUM(p.stock) FROM Products p")
    Integer getTotalStock();
}