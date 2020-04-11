package com.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.service.AdminService;
import com.system.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService = null;
	@Autowired
	private UserService userService = null;
	
	@RequestMapping("/admin_manageUser")
	public String index_admin(HttpServletRequest request,Model model) {
		return "admin_manageUser";
	}
}
