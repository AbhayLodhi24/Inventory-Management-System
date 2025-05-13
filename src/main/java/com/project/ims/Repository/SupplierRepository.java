package com.project.ims.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.ims.Model.Supplier;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
		@Query("SELECT s FROM Supplier s WHERE " +
				"LOWER(s.supplierName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
				"LOWER(s.supplierEmail) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
				"LOWER(s.supplierPhno) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
				"LOWER(s.supplierAddr) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		List<Supplier> searchSuppliers(@Param("keyword") String keyword);

	@Query("select s from Supplier s where upper(s.supplierEmail) = upper(:email)")
	Supplier findBySupplierEmailIgnoreCase(@Param("email") String email);
}