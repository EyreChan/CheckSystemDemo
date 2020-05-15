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
import com.system.service.DFormatService;
import com.system.service.TemplateService;
import com.system.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService = null;
	@Autowired
	private UserService userService = null;
	@Autowired
	private TemplateService templateService = null;
	@Autowired
	private DFormatService dformatService = null;
	
	@RequestMapping("/index_admin")
	public String index_admin(HttpServletRequest request,Model model) {
		return "index_admin";
	}
	
	@RequestMapping("/admin_manageUser")
	public String admin_manageUser(HttpServletRequest request,Model model) {
		return "admin_manageUser";
	}
	
	@RequestMapping("/admin_getAllUsers")
	@ResponseBody
	public List<User> admin_getAllUsers(){
		List<User> res = this.userService.getAllUsers();
		return res;
	}
	
	@RequestMapping("/admin_deleteUser")
	@ResponseBody
	public int admin_deleteUser(String name) {
		int res = this.userService.deleteUser(name);
		this.templateService.deleteTemplateByUser(name);
		this.dformatService.deleteFormatByUser(name);
		//System.out.println(res);
		return res;
	}
	
	@RequestMapping("/admin_editUser")
	public String admin_editUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "admin_editUser";
	}
	
	@RequestMapping("/admin_getUserByName")
	@ResponseBody
	public User admin_getUserByName(String name){
		User user = this.userService.getUserByName(name);
		return user;
	}
	
	@RequestMapping("/admin_updateByName")
	@ResponseBody
	public int admin_updateByName(String name, String gender, String age, String phone, String password){
		User user = new User(name, gender, phone, Integer.parseInt(age), password);
		int res = this.userService.updateUser(user);
		return res;
	}
	
	@RequestMapping("/admin_addUser")
	public String admin_addUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "admin_addUser";
	}
	
	@RequestMapping("/admin_insertUser")
	@ResponseBody
	public int admin_insertUser(String name, String gender, String age, String phone, String password){
		User user = new User(name, gender, phone, Integer.parseInt(age), password);
		int res = this.userService.insertUser(user);
		return res;
	}
}
