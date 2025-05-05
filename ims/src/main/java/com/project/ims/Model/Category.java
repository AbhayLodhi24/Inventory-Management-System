package com.project.ims.model;
 
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name="categories")
@Scope("prototype")
@Component
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId; // Rename this field to 'id'
	
    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;
	
	@Column(name="CATEGORY_DESCRIPTION")
	private String category_description;
	
	@OneToMany(mappedBy = "category")
    private List<Products> products;


	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}
 
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
 
	public String getCategory_description() {
		return category_description;
	}
 
	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}
	
	public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
 
	
}