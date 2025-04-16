package com.project.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {
	
	@GetMapping("/login")
	public String login(Model model) {
		return "/Frontend/Admin/Login/login";
	}
}
