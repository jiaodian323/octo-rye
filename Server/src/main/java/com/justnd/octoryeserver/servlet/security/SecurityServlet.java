/**   
* @Title: SecurityServlet.java 
* @Package com.justnd.octoryeserver.servlet.security 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月15日 下午3:22:00  
*/
package com.justnd.octoryeserver.servlet.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/** 
* @ClassName: SecurityServlet 
* @Description: TODO
* @author JD
* @date 2019年3月15日 下午3:22:00 
*  
*/
@WebServlet(name="SecurityServlet", urlPatterns=("/s/sec/info"))
public class SecurityServlet extends HttpServlet {
	private static final long serialVersionUID = 6964158382365571245L;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("初始化SecurityServlet");
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("SecurityServlet接受到Get请求，交由Post请求处理");
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("SecurityServlet接受到Post请求");
		response.setContentType("text/json;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	}
}
