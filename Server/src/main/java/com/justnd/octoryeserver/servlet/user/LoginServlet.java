/**   
* @Title: LoginServlet.java 
* @Package www.justnd.hrsystem.servlet 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月2日 下午9:07:01  
*/
package com.justnd.octoryeserver.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.domain.User;

import net.sf.json.JSONObject;

/**
 * @ClassName: LoginServlet
 * @Description: TODO
 * @author JD
 * @date 2018年12月2日 下午9:07:01
 * 
 */
@WebServlet(name = "LoginServlet", urlPatterns=("/user/login"))
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -8874223897408260892L;

	@Autowired
	private UserDao userDao;

	/**
	 * Title: init Description:
	 * 
	 * @throws ServletException
	 */
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
		System.out.println("初始化LoginServlet");
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doPost()方法");

		// 设置响应内容类型
		response.setContentType("text/json;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {

			// 获得请求中传来的用户名和密码
			String username = request.getParameter("Username").trim();
			String password = request.getParameter("Password").trim();
			System.out.println("Username=" + username + ",password=" + password);

			// 密码验证结果
			Boolean verifyResult = verifyLogin(username, password);

			Map<String, String> params = new HashMap<>();
			JSONObject jsonObject = new JSONObject();

			if (verifyResult) {
				params.put("Result", "success");
			} else {
				params.put("Result", "failed");
			}

			jsonObject.put("params", params);
			out.write(jsonObject.toString());
			out.close();
			// request.setAttribute("jsonObject", jsonObject.toString());
		}
	}

	private Boolean verifyLogin(String userName, String password) {
		User user = userDao.findByUsername(userName);

		// 账户密码验证
		return null != user && password.equals(user.getPassword());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doGet()方法");
		doPost(request, response);
	}
}
