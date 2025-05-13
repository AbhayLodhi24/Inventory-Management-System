package com.project.ims.Dto;

import java.time.LocalDateTime;

public class OrderReportDTO {
    private Integer orderId;
    private LocalDateTime orderDate;
    private String productName;
    private String categoryName;
    private String username;
    private String address;
    private Integer quantity;
    private Float totalPrice;

    public OrderReportDTO(Integer orderId, LocalDateTime orderDate, String productName, String categoryName,
                          String username, String address, Integer quantity, Float totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.productName = productName;
        this.categoryName = categoryName;
        this.username = username;
        this.address = address;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Integer getOrderId() { return orderId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getProductName() { return productName; }
    public String getCategoryName() { return categoryName; }
    public String getUsername() { return username; }
    public String getAddress() { return address; }
    public Integer getQuantity() { return quantity; }
    public Float getTotalPrice() { return totalPrice; }
}

