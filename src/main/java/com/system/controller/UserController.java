package com.system.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.pojo.Format;
import com.system.service.UserService;
import com.system.service.FormatService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService = null;
	@Autowired 
	private FormatService formatService = null;
	
	@RequestMapping("/user_manageTemplate")
	public String user_manageTemplate(HttpServletRequest request,Model model) {
		return "user_manageTemplate";
	}
	
	@RequestMapping("/user_getTemplates")
	@ResponseBody
	public List<Format> user_getTemplates(HttpServletRequest request,Model model) {
		HttpServletRequest req = (HttpServletRequest) request;
		
		String userName = null;
		
		Cookie[] cookies = req.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().toString().equals("loginName")) {
					userName = cookie.getValue();
				}
			}
		}
		
		if(userName != null){
			return this.formatService.getFormatsByUser(userName);
		}
		return null;
	}
}
