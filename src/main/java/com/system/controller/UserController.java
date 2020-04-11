package com.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.service.UserService;
import com.system.service.AdminService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService patientService = null;
	@Autowired
	private AdminService adminService = null;
	
	@RequestMapping("/user_manageTemplate")
	public String index_admin(HttpServletRequest request,Model model) {
		return "user_manageTemplate";
	}
}
