package com.project.ims.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SupplierDto {
	private Integer supplierId;

    @NotBlank(message = "Supplier name is required.")
    @Size(min = 3, max = 50, message = "Supplier name must be between 3 and 50 characters.")
    private String supplierName;

    @NotBlank(message = "Supplier email is required.")   
    @Email(message = "Invalid email format.")    
    private String supplierEmail;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits.")
    private String supplierPhno;

    @NotBlank(message = "Address is required.")
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters.")
    private String supplierAddr;

    // Getters and Setters
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
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
}
