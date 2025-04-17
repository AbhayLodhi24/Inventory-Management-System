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
 
@Entity
@Table(name="categories")
@Scope("prototype")
@Component
public class Category {
	@Id
	@SequenceGenerator(
	        name = "category_sequence",
	        sequenceName = "category_id_sequence",
	        allocationSize = 1
	    )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
	@Column(name="CATEGORY_ID")
	private Integer categoryId;
	
	@Column(name="CATEGORY_NAME")
	private String categoryName;
	
	@Column(name="CATEGORY_DESCRIPTION")
	private String category_description;
	
	@OneToMany(mappedBy = "category")
    private List<Products> products;
	
	public Integer getCategoryId() {
		return categoryId;
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