package com.project.ims.Model;
 
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
 
 
@Component
@Entity
@Table(name="suppliers")
@Scope("prototype")
public class Supplier {
	
	@Id
	@Column(name="supplier_id")
	@SequenceGenerator(
	        name = "supplier_seq",
	        sequenceName = "supplier_id_sequence",
	        allocationSize = 1
	    )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_seq")
	private Integer supplierId;
	
	@Column(name="supplier_name")
	private String supplierName;
	
	@Column(name="supplier_email")
	private String supplier_email;
	
	@Column(name="supplier_phone_number")
	private String supplier_phno;
	
	@Column(name="address")	
	private String supplier_addr;
	
	@OneToMany(mappedBy = "supplier")
    private List<Products> products;
	
	public Integer getSupplierId() {
		return supplierId;
	}
 
	public String getSupplierName() {
		return supplierName;
	}
 
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
 
	public String getSupplier_email() {
		return supplier_email;
	}
 
	public void setSupplier_email(String supplier_email) {
		this.supplier_email = supplier_email;
	}
 
	public String getSupplier_phno() {
		return supplier_phno;
	}
 
	public void setSupplier_phno(String supplier_phno) {
		this.supplier_phno = supplier_phno;
	}
 
	public String getSupplier_addr() {
		return supplier_addr;
	}
 
	public void setSupplier_addr(String supplier_addr) {
		this.supplier_addr = supplier_addr;
	}
	
	public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
 
}