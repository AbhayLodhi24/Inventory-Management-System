package com.project.ims.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.ims.Model.Supplier;
import com.project.ims.Repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Service
public class SupplierService {
	
		@Autowired
		private SupplierRepository supplierRepository;
		
		public Page<Supplier> getAllSuppliers(String keyword, Pageable pageable) {
			
		    return supplierRepository.searchSuppliersWithPagination(keyword,pageable);
		}
		
		public List<Supplier> getAllSuppliersFromTable(){
			return supplierRepository.findAll();
		}
		
		  public Supplier saveSupplier(Supplier supplier) {
		        return supplierRepository.save(supplier);
		    }
		
		public boolean deleteSupplier(int id) {
	        if (supplierRepository.existsById(id)) {
	            supplierRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }
		
		public Optional<Supplier> getSupplierById(int id) {
			System.out.println(id);
	        return supplierRepository.findById(id);
	        
	    }

		@Transactional
	    public void updateSupplier(SupplierDto supplierDto) {
	        Optional<Supplier> existingSupplierOptional = supplierRepository.findById(supplierDto.getSupplierId());

	        if (!existingSupplierOptional.isPresent()) {
	            throw new IllegalArgumentException("Supplier not found with ID: " + supplierDto.getSupplierId());
	        }

	        Supplier existingSupplier = existingSupplierOptional.get();

	        // Check for duplicate name, excluding the current supplier being updated
	        Supplier existingNameSupplier = supplierRepository.findBySupplierName(supplierDto.getSupplierName());
	        if (existingNameSupplier != null && !existingNameSupplier.getSupplierId().equals(supplierDto.getSupplierId())) {
	            throw new DataIntegrityViolationException("Supplier with this name already exists.");
	        }

	        existingSupplier.setSupplierName(supplierDto.getSupplierName());
	        existingSupplier.setContactPerson(supplierDto.getContactPerson());
	        existingSupplier.setEmail(supplierDto.getEmail());
	        existingSupplier.setPhone(supplierDto.getPhone());

	        supplierRepository.save(existingSupplier);
	    }
				
}