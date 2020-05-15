package com.system.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.pojo.Admin;
import com.system.pojo.User;
import com.system.service.AdminService;
import com.system.service.UserService;

@Controller
@RequestMapping("/log")
public class LogController {
	@Autowired
	private UserService userService = null;
	@Autowired
	private AdminService adminService = null;

	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model model) {
    	System.out.println("------进入/log/login------");
		
		String nameCookie = null;
    	String passwordCookie = null;
    	
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null) {
    		for (Cookie cookie : cookies) {
    			// 得到cookie的用户名
    	        if (cookie.getName().equals("loginName"))
    	        	nameCookie = cookie.getValue(); 

    	        // 得到cookie的密码
    	        if (cookie.getName().equals("loginPassword"))
    	        	passwordCookie = cookie.getValue(); 

    		}
    		if (nameCookie != null && passwordCookie != null) {
    			User account = this.userService.getUserByName(nameCookie);
    			if(account != null && account.getPassword().equals(passwordCookie)) {
    				model.addAttribute("account", account);
    				System.out.println("准备回到redirect:/");
    				return "redirect:/";
    			}
    		}
    	}
    	System.out.println("准备回到login");
		return "login";
	}	
	
	@RequestMapping("/validate")
	@ResponseBody
	public String validate(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		System.out.println("validate");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		System.out.println("name" + name);
		
		
	    Cookie nameCookie = new Cookie("loginName", URLEncoder.encode(name, "UTF-8"));  
	    Cookie passwordCookie = new Cookie("loginPassword", URLEncoder.encode(password, "UTF-8"));  
	    nameCookie.setMaxAge(60 * 60);  
	    nameCookie.setPath("/");  
	    passwordCookie.setMaxAge(60 * 60);  
	    passwordCookie.setPath("/");  
		
	    User user = this.userService.getUserByName(name);
	    if(user != null) {
	    	if(user.getPassword().equals(password)) {
	    		response.addCookie(nameCookie);
	    	    response.addCookie(passwordCookie); 
	    	    model.addAttribute("user", user);
	    	    
	    	    System.out.println("user");
	    	    
	    	    return "/index.jsp";
	    	}
	    }
		else {
			Admin admin = this.adminService.getAdminByName(name);
			if(admin != null) {
				if(admin.getPassword().equals(password)) {
		    	    response.addCookie(nameCookie);  
		    	    response.addCookie(passwordCookie); 
		    	    model.addAttribute("admin", admin);
		    	    
		    	    System.out.println("admin");
					
					return "/admin/index_admin";
				}
			}
		}
    	
    	model.addAttribute("flag", 1);
    	return "/log/login";	
	}
	
	@RequestMapping("/goRegister")
	public String goRegister(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "register";
	}
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public Integer doRegister(String name, String password, String phone, String gender, String age, HttpServletResponse response, Model model) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setPhone(phone);
		user.setGender(gender);
		user.setAge(Integer.parseInt(age));
		
		int flag = this.userService.insertUser(user);
		
		if(flag == 0)
			return flag;
		
	    Cookie nameCookie = new Cookie("loginName", name);  
	    Cookie passwordCookie = new Cookie("loginPassword", password);  
	    nameCookie.setMaxAge(60 * 60);  
	    nameCookie.setPath("/"); 
	    passwordCookie.setMaxAge(60 * 60);  
	    passwordCookie.setPath("/");  

		response.addCookie(nameCookie);  
	    response.addCookie(passwordCookie); 	
		
		return flag;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        //删除登录cookie  
		System.out.println("-----------进入/log/logout");
        Cookie cookieName = new Cookie("loginName", "");  
        Cookie cookiePassword = new Cookie("loginPassword", "");  
        cookieName.setMaxAge(0);  
        cookieName.setPath("/");  
        cookiePassword.setMaxAge(0); 
        cookiePassword.setPath("/");  
        response.addCookie(cookieName);  
        response.addCookie(cookiePassword);  
       
		return "redirect:/log/login";
	}
}
