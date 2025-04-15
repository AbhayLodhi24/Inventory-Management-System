package com.project.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class DashboardController {
	
	@GetMapping("/admin/dashboard")
	public String getDashboardPage(Model model) {
		
		return "/Frontend/Admin/Dashboard/dashboard";
	}
}
