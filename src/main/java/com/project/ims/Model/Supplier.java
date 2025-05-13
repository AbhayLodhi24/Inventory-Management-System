package com.project.ims.Model;
import java.util.List;
 
import org.springframework.context.annotation.Scope;
 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="suppliers", uniqueConstraints = {@UniqueConstraint(columnNames = {"supplier_email"})})
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
    private String supplierEmail;
 
 
    @Column(name="supplier_phone_number")
    private String supplierPhno;
 
    @Column(name="address")
    private String supplierAddr;	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE)
    private List<Products> products;


	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupperId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierEmail() {
		return supplierEmail;
	}
 
	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}
 
	public String getSupplierPhno() {
		return supplierPhno;
	}
 
	public void setSupplierPhno(String supplierPhno) {
		this.supplierPhno = supplierPhno;
	}
 
	public String getSupplierAddr() {
		return supplierAddr;
	}
 
	public void setSupplierAddr(String supplierAddr) {
		this.supplierAddr = supplierAddr;
	}

	public List<Products> getProducts() {
        return products;
    }
 
    public void setProducts(List<Products> products) {
        this.products = products;
    }

 

}