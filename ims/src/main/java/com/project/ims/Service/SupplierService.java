package com.project.ims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.ims.model.Supplier;
import com.project.ims.repository.SupplierRepository;

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

	    // Method to update an existing supplier
	    public Supplier updateSupplier(Supplier updatedSupplier) {
	        return supplierRepository.save(updatedSupplier);
	    }
				
}