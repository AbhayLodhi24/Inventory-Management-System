package com.project.ims.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.ims.Model.Supplier;
import com.project.ims.Repository.SupplierRepository;
@Service
public class SupplierService {
		@Autowired
		private SupplierRepository supplierRepository;
		public List<Supplier> getAllSuppliers(String keyword) {
			if (keyword != null && !keyword.isEmpty()) {
				return supplierRepository.searchSuppliers(keyword);
			} else {
				return supplierRepository.findAll();
			}
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
	    public boolean supplierEmailExists(String email) {
	    	return supplierRepository.findBySupplierEmailIgnoreCase(email) != null ;
	    }
}