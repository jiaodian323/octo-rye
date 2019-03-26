/**   
* @Title: RegisterServlet.java 
* @Package www.justnd.magazineserver.servlet 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月6日 下午3:06:18  
*/
package com.justnd.octoryeserver.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.servlet.base.BaseServlet;

/** 
* @ClassName: RegisterServlet 
* @Description: TODO
* @author JD
* @date 2018年12月6日 下午3:06:18 
*  
*/
@WebServlet(name="SignUpServlet", urlPatterns=("/s/user/register/signup"))
public class SignUpServlet extends BaseServlet {

	private static final long serialVersionUID = 2718811378143301923L;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void init() throws ServletException {
		super.init();
		// this.userDao = new UserDaoHibernate4();
		// WebApplicationContext appCtx =
		// WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		//
		// userDao = (UserDao)
		// BeanFactoryUtils.beanOfTypeIncludingAncestors(appCtx, UserDao.class);
		//
		// session = (SessionProvider)
		// BeanFactoryUtils.beanOfTypeIncludingAncestors(appCtx,
		// SessionProvider.class);
		System.out.println("初始化RegisterServlet");
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doPost()方法");

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doGet()方法");
		doPost(request, response);
	}
}
