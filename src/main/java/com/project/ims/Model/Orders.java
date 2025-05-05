package com.project.ims.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "orders")
@Scope("prototype")
@Component
@Data
public class Orders {

    @Id
    @SequenceGenerator(
        name = "order_sequence",
        sequenceName = "order_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private Float totalPrice;

    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDateTime orderDate;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private User user;


    @Column(name = "PRODUCT_NAME", nullable = false) // Assuming name cannot be null
    private String productName;

    @Column(name = "CATEGORY_NAME", nullable = false) // Assuming name cannot be null
    private String categoryName;

     public Integer getOrderId() { return orderId; }
     public void setOrderId(Integer orderId) { this.orderId = orderId; }

     public Integer getQuantity() { return quantity; }
     public void setQuantity(Integer quantity) { this.quantity = quantity; }

     public Float getTotalPrice() { return totalPrice; }
     public void setTotalPrice(Float totalPrice) { this.totalPrice = totalPrice; }

     public LocalDateTime getOrderDate() { return orderDate; }
     public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

     public User getUser() { return user; }
     public void setUser(User user) { this.user = user; }

     public String getProductName() { return productName; }
     public void setProductName(String productName) { this.productName = productName; }

     public String getCategoryName() { return categoryName; }
     public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}