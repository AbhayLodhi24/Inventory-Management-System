package com.project.ims.Model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
@Component
@Scope("prototype")
public class Products {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_id_seq",
            allocationSize = 1
        )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Float price;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	@ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID", nullable = false, insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "SUPPLIER_ID", nullable = false, insertable = false, updatable = false)
    private Supplier supplier;


  
}