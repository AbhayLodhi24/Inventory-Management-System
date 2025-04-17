package com.project.ims.Repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.project.ims.Model.Supplier;
 
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
 
}