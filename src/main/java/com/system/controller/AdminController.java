    package com.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.pojo.User;
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
	public String admin_manageUser(HttpServletRequest request,Model model) {
		return "admin_manageUser";
	}
	
	@RequestMapping("/admin_getAllUsers")
	@ResponseBody
	public List<User> admin_getAllUsers(){
		List<User> res = this.userService.getAllUsers();
		System.out.println(res.size());
		return res;
	}
	
	@RequestMapping("/admin_editUserByName")
	public String editRecord(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "admin_editUserByName";
	}
	
	@RequestMapping("/admin_deleteUser")
	@ResponseBody
	public int admin_deleteUser(String username) {
		return this.userService.deleteUser(username);
	}
}
