/**   
* @Title: DispatcherServlet.java 
* @Package www.justnd.magazineserver.servlet.dispatcher 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月6日 下午2:21:44  
*/
package com.justnd.octoryeserver.servlet.dispatcher;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @ClassName: DispatcherServlet
 * @Description: TODO
 * @author JD
 * @date 2018年12月6日 下午2:21:44
 * 
 */
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 6555124110876977092L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("初始化DispatcherServlet");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException, IOException {
		System.out.println("DispatchServlet接受到Post请求，继续转发");
		String requestType = request.getParameter("RequestType");
		switch (requestType) {
		case "Login":
			System.out.println("转发至LoginServlet");
			RequestDispatcher dispatchToLogin = request.getRequestDispatcher("LoginServlet");
			dispatchToLogin.forward(request, reponse);
			break;
		case "Register":
			RequestDispatcher dispatchToRegister = request.getRequestDispatcher("RegisterServlet");
			dispatchToRegister.forward(request, reponse);
			break;
		case "PushArticles":
			RequestDispatcher dispatchToPushArticles = request
					.getRequestDispatcher("PushArticlesServlet");
			dispatchToPushArticles.forward(request, reponse);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DispatchServlet接受到Get请求，转由Post处理");
		doPost(request, response);
	}
}
