package com.system.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {
    public LoginFilter() {
    }

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("过滤器销毁");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("utf-8");

		HttpServletResponse resp = (HttpServletResponse) response;
		
		String url = req.getRequestURI();
		System.out.println(url);

		if(url.endsWith(".css")) {
			resp.setContentType("text/css;charset=utf-8");
			chain.doFilter(req, resp);
			return;
		}
		
		if(url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".js") || url.endsWith(".ico")) {
			chain.doFilter(req, resp);
			return;
		}
		
		resp.setContentType("text/html;charset=utf-8");
		
		List<String> list= new ArrayList<String>(); //将不需要过滤的请求存放到集合中
		list.add("/log/login");//登录页面
		list.add("/log/goRegister");//跳转到注册页面请求
		list.add("/log/doRegister");
		list.add("/validate");
		list.add("/static");
		list.add("/index_");
		list.add("/index/");
		list.add("/user");
		String path = req.getServletPath();//得到请求的url
		System.out.println("uri:"+path);
		//循环集合把不需要过滤的请求放行
		for(String l : list){
			if(path.contains(l)){
				chain.doFilter(req, resp);
				return;
			}
		}
		Cookie[] cookies = req.getCookies();//这样便可以获取一个cookie数组
		int flag=0;
		if(cookies!=null)
		{
			for(Cookie cookie : cookies)
			{
				if(cookie.getName().toString().equals("loginName"))
				{
					flag=1;
					break;
				}
			}
		}
		if(flag==0){
			System.out.println("uri为: " + path);
			System.out.println("无用户");
			resp.sendRedirect("/log/login");
		}
		else
		{
			System.out.println("有用户");
			chain.doFilter(req, resp);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		// TODO Auto-generated method stub
		System.out.println("过滤器初始化");
	}

}