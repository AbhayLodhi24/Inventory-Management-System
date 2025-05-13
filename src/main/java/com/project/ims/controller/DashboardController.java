package com.project.ims.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.ims.Dto.ChartDataPoint;
import com.project.ims.Dto.DashboardData;
import com.project.ims.Service.DashboardService;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin/dashboard")
    public String getDashboardPage(Model model) {
        DashboardData dashboardData = dashboardService.getDashboardData();

        int year = java.time.LocalDate.now().getYear();
        int month = java.time.LocalDate.now().getMonthValue();

        // Revenue trend by day in selected month
        List<ChartDataPoint> revenueTrend = dashboardService.getRevenueTrendForMonth(year, month);
        List<String> revenueTrendLabels = revenueTrend.stream().map(ChartDataPoint::getLabel).collect(Collectors.toList());
        List<Double> revenueTrendValues = revenueTrend.stream().map(ChartDataPoint::getValue).collect(Collectors.toList());

        // Orders per day in selected month
        List<ChartDataPoint> ordersPerDay = dashboardService.getOrdersPerDayForMonth(year, month);
        List<String> ordersPerDayLabels = ordersPerDay.stream().map(ChartDataPoint::getLabel).collect(Collectors.toList());
        List<Double> ordersPerDayValues = ordersPerDay.stream().map(ChartDataPoint::getValue).collect(Collectors.toList());

        // Revenue by category (pie)
        List<ChartDataPoint> revenueByCategory = dashboardService.getRevenueByCategory();
        List<String> revenueByCategoryLabels = revenueByCategory.stream().map(ChartDataPoint::getLabel).collect(Collectors.toList());
        List<Double> revenueByCategoryValues = revenueByCategory.stream().map(ChartDataPoint::getValue).collect(Collectors.toList());

        model.addAttribute("dashboardData", dashboardData);
        model.addAttribute("revenueTrendLabels", revenueTrendLabels);
        model.addAttribute("revenueTrendValues", revenueTrendValues);
        model.addAttribute("ordersPerDayLabels", ordersPerDayLabels);
        model.addAttribute("ordersPerDayValues", ordersPerDayValues);
        model.addAttribute("revenueByCategoryLabels", revenueByCategoryLabels);
        model.addAttribute("revenueByCategoryValues", revenueByCategoryValues);

        return "/Frontend/Admin/Dashboard/dashboard";

    }

}
