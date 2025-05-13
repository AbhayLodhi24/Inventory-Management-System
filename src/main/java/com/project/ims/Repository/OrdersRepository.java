package com.project.ims.Repository;

import com.project.ims.Dto.OrderReportDTO;
import com.project.ims.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByUser_UserId(Long userId);

    @Query("SELECT new com.project.ims.Dto.OrderReportDTO(" +
            "o.orderId, o.orderDate, o.productName, o.categoryName, u.username, u.address, o.quantity, o.totalPrice)" +
            " FROM Orders o JOIN o.user u")
    List<OrderReportDTO> fetchOrderReport();

    @Query("SELECT SUM(o.totalPrice) FROM Orders o")
    Float getTotalRevenue();

    long countByOrderDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.orderDate >= :start AND o.orderDate < :end")
    Float getRevenueInDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT TO_CHAR(order_date, 'YYYY-MM-DD'), COUNT(*) " +
            "FROM orders WHERE order_date >= :startDate AND order_date < :endDate " +
            "GROUP BY TO_CHAR(order_date, 'YYYY-MM-DD') " +
            "ORDER BY TO_CHAR(order_date, 'YYYY-MM-DD')", nativeQuery = true)
    List<Object[]> findOrdersCountByDayRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value = "SELECT TO_CHAR(order_date, 'YYYY-MM-DD'), SUM(total_price) " +
            "FROM orders WHERE order_date >= :startDate AND order_date < :endDate " +
            "GROUP BY TO_CHAR(order_date, 'YYYY-MM-DD') " +
            "ORDER BY TO_CHAR(order_date, 'YYYY-MM-DD')", nativeQuery = true)
    List<Object[]> findRevenueByDayRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value = "SELECT o.category_name, SUM(o.total_price) " +
            "FROM orders o GROUP BY o.category_name", nativeQuery = true)
    List<Object[]> findRevenueByCategory();

    @Query(
            value = "SELECT o.order_id, o.order_date, o.product_name, o.category_name, u.username, u.address, o.quantity, o.total_price " +
                    "FROM orders o " +
                    "JOIN users u ON o.user_id = u.user_id " +
                    "WHERE " +
                    "   LOWER(u.username) LIKE LOWER('%' || :searchTerm || '%') OR " +
                    "   LOWER(u.address) LIKE LOWER('%' || :searchTerm || '%') OR " +
                    "   LOWER(o.product_name) LIKE LOWER('%' || :searchTerm || '%') OR " +
                    "   LOWER(o.category_name) LIKE LOWER('%' || :searchTerm || '%') OR " +
                    "   TO_CHAR(o.quantity) LIKE '%' || :searchTerm || '%' OR " +
                    "   TO_CHAR(o.total_price) LIKE '%' || :searchTerm || '%'" +
                    "ORDER BY o.order_id DESC ",
            nativeQuery = true)
    List<Object[]> searchOrdersNative(@Param("searchTerm") String searchTerm);
}
