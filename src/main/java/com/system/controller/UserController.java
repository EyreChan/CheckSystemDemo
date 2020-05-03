package com.system.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.pojo.Template;
import com.system.service.UserService;
import com.system.service.TemplateService;
import com.system.service.DFormatService;
import com.system.service.SFormatService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService = null;
	@Autowired 
	private TemplateService templateService = null;
	@Autowired 
	private DFormatService dformatService = null;
	@Autowired 
	private SFormatService sformatService = null;
	
	@RequestMapping("/user_manageTemplate")
	public String user_manageTemplate(HttpServletRequest request,Model model) {
		return "user_manageTemplate";
	}
	
	@RequestMapping("/user_getTemplates")
	@ResponseBody
	public List<Template> user_getTemplates(HttpServletRequest request,Model model) {
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
			return this.templateService.getTemplatesByUser(userName);
		}
		return null;
	}
	
	@RequestMapping("/user_deleteTemplate")
	@ResponseBody
	public int user_deleteTemplate(String name, HttpServletRequest request,Model model) {
		int res = 0;
		
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
			this.dformatService.deleteFormat(name, userName, "template");
			this.sformatService.deleteFormat(name, userName, "template");
			res = this.templateService.deleteTemplate(name, userName);
		}
		return res;
	}
	
	@RequestMapping("/user_addTemplate")
	public String user_addTemplate(HttpServletRequest request,Model model) {
		return "user_addTemplate";
	}
	
	@RequestMapping("/user_checkDoc")
	public String user_checkDoc(HttpServletRequest request,Model model) {
		return "user_checkDoc";
	}
	
	@RequestMapping("/user_checkStyle")
	public String user_checkStyle(HttpServletRequest request,Model model) {
		return "user_checkStyle";
	}
}
