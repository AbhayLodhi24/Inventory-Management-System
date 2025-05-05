package com.project.ims.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ims.Dto.ChartDataPoint;
import com.project.ims.Dto.DashboardData;
import com.project.ims.Repository.OrdersRepository;
import com.project.ims.Repository.ProductRepository;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    public DashboardData getDashboardData() {
        DashboardData data = new DashboardData();

        // Total products and stock
        data.setTotalProducts((int) productsRepository.count());
        data.setTotalStock(productsRepository.getTotalStock() != null ? productsRepository.getTotalStock() : 0);

        // Today's range
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        // Orders Today
        data.setOrderToday((int) ordersRepository.countByOrderDateBetween(start, end));

        // Revenue (all-time)
        Float totalRev = ordersRepository.getTotalRevenue();
        data.setRevenue(totalRev == null ? 0 : totalRev.intValue());

        return data;
    }

    // Helper formatter
//    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // For revenue per day in selected month
    public List<ChartDataPoint> getRevenueTrendForMonth(int year, int month) {
        java.time.LocalDate start = java.time.LocalDate.of(year, month, 1);
        java.time.LocalDate end = start.plusMonths(1);
        List<Object[]> raw = ordersRepository.findRevenueByDayRange(
                start.atStartOfDay(),
                end.atStartOfDay()
        );
        return raw.stream().map(r ->
                new ChartDataPoint((String) r[0], r[1]!=null ? ((Number) r[1]).doubleValue() : 0)
        ).collect(Collectors.toList());
    }

    // For orders per day in selected month
    public List<ChartDataPoint> getOrdersPerDayForMonth(int year, int month) {
        java.time.LocalDate start = java.time.LocalDate.of(year, month, 1);
        java.time.LocalDate end = start.plusMonths(1);
        List<Object[]> raw = ordersRepository.findOrdersCountByDayRange(
                start.atStartOfDay(),
                end.atStartOfDay()
        );
        return raw.stream().map(r ->
                new ChartDataPoint((String) r[0], r[1]!=null ? ((Number) r[1]).doubleValue() : 0)
        ).collect(Collectors.toList());
    }

    // 3. Revenue by category
    public List<ChartDataPoint> getRevenueByCategory() {
        List<Object[]> raw = ordersRepository.findRevenueByCategory();
        return raw.stream().map(r -> new ChartDataPoint((String) r[0], ((Number)r[1]).doubleValue())).collect(Collectors.toList());

    }
}
