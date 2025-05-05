package com.project.ims.Dto; // You might need to create a 'Dto' package

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Use NotNull for Long/primitive wrappers
import jakarta.validation.constraints.Size;

public class UserProfileUpdateForm {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 25, message = "Username must be between 5 and 25 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(min=5,max = 30, message = "Email must be between 5 and 30 characters")
    private String email;

    @NotBlank(message = "Address is required")
    @Size(min=2,max = 50, message = "Address must be between 2 and 50 characters")
    private String address;

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}