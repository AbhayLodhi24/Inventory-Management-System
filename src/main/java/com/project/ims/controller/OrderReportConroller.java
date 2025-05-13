package com.project.ims.controller;

import com.project.ims.Dto.OrderReportDTO;
import com.project.ims.Service.OrdersReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class OrderReportConroller {
    @Autowired
    private OrdersReportService ordersReportService;

    @GetMapping("/admin/order")
    public String getOrderPage(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            Model model) {

        List<OrderReportDTO> ordersList = ordersReportService.searchOrders(query);
        model.addAttribute("ordersList", ordersList);
        model.addAttribute("query", query);
        return "Frontend/Admin/Orders/order";
    }
}
