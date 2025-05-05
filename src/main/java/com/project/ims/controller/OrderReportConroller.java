package com.project.ims.controller;

import com.project.ims.Service.OrdersReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ims.Dto.OrderReportDTO;


@Controller
public class OrderReportConroller {
    @Autowired
    private OrdersReportService ordersReportService;

    @GetMapping("/admin/order")
    public String getOrderPage(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            Model model) {

        page = Math.max(page, 0);
        Page<OrderReportDTO> ordersPage = ordersReportService.searchOrders(query, page, size);
        model.addAttribute("ordersList", ordersPage.getContent());
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        return "Frontend/Admin/Orders/order";
    }
}
