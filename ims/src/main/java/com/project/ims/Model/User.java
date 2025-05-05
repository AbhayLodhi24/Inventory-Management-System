package com.project.ims.Model;
 
import jakarta.persistence.*;
 
@Entity
@Table(name = "USERS")
public class User {
 
 @Id
    @SequenceGenerator(
        name = "id_sequence",
        sequenceName = "USER_ID_SEQ",
        allocationSize = 1
        )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @Column(name = "user_id")
    private Long userId;
 
    @Column(name = "username")
    private String username;
 
    @Column(name = "password")
    private String password;
 
    @Column(name = "email")
    private String email;
 
    @Column(name = "address")
    private String address;
 
    @Column(name = "role")
    private String role = "user"; // Default role
 
    // Constructors, getters, and setters
    public User(){}
 
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
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
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
 
    public String getRole() {
        return role;
    }
 
    public void setRole(String role) {
        this.role = role;
    }
}