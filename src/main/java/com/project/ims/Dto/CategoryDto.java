package com.project.ims.Dto;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
 
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
 
	public String getCategoryDescription() {
		return categoryDescription;
	}
 
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}	
 
	@NotBlank(message = "Category name is required.")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters.")
    private String categoryName;
 
    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String categoryDescription;
}