package com.project.ims.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Products {

    @Id
    @Column(name = "PRODUCT_ID")
    private Integer productId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Float price;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    @Column(name = "CATEGORY_ID", nullable = false)
    private Integer categoryId;

    @Column(name = "SUPPLIER_ID", nullable = false)
    private Integer supplierId;
    // Foreign Key to Categories table
//  @ManyToOne
//  @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID", nullable = false)
//  private Category category;
//
//  // Foreign Key to Suppliers table
//  @ManyToOne
//  @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "SUPPLIER_ID", nullable = false)
//  private Supplier supplier;
  
  
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Override
	public String toString() {
		return "Products [productId=" + productId + ", name=" + name + ", price=" + price + ", stock=" + stock
				+ ", categoryId=" + categoryId + ", supplierId=" + supplierId + "]";
	}

  
}