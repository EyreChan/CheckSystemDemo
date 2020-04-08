package com.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
	@RequestMapping("/index_admin")
	public String index_admin(HttpServletRequest request,Model model) {
		return "index_admin";
	}
}
