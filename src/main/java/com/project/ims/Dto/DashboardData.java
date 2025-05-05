package com.project.ims.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardData {
    private int totalProducts;
    private int totalStock;
    private int orderToday;
    private int revenue;

    public int getTotalProducts() {
        return totalProducts;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getOrderToday() {
        return orderToday;
    }

    public int getTotalStock() {
        return totalStock;
    }
// Getters and Setters

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setOrderToday(int orderToday) {
        this.orderToday = orderToday;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }
}
