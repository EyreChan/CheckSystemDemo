package com.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
