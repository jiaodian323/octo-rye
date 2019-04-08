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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.justnd.octoryeserver.beans.base.BaseBean;
import com.justnd.octoryeserver.beans.user.LoginBean;
import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.domain.User;
import com.justnd.octoryeserver.security.MD5Coder;
import com.justnd.octoryeserver.servlet.base.BaseServlet;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.GsonUtil;

import net.sf.json.JSONObject;

/**
 * @ClassName: LoginServlet
 * @Description: TODO
 * @author JD
 * @date 2018年12月2日 下午9:07:01
 * 
 */
@WebServlet(name = "LoginServlet", urlPatterns=("/s/user/login"))
public class LoginServlet extends BaseServlet {
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
			
			String bodyStr = getRequestBodyStr(request);
			LoginBean login = GsonUtil.jsonStrToObject(bodyStr, LoginBean.class);

			// 获得请求中传来的用户名和密码
			String phoneNumber = login.getPhoneNumber();
			String password = login.getPassword();
			System.out.println("PhoneNumber=" + phoneNumber + ",password=" + password);

			// 密码验证结果
			Boolean verifyResult = verifyLogin(phoneNumber, password);

			@SuppressWarnings("rawtypes")
			BaseBean resBean = new BaseBean();
			
			if (verifyResult) {
				resBean.setCode(1);
				resBean.setMessage(ConstantUtil.SUCCESS_LOGIN);
			} else {
				resBean.setCode(0);
				resBean.setMessage(ConstantUtil.FAILED_LOGIN);
			}
			
			out.write(GsonUtil.objectToJsonStr(resBean));
			out.close();
		}
	}

	/** 
	* @Title: verifyLogin 
	* @Description: TODO 查询数据库，对比密码哈希码
	* @param @param phone
	* @param @param password
	* @param @return
	* @return Boolean
	* @throws 
	*/
	private Boolean verifyLogin(String phone, String password) {
		User user = userDao.findByPhoneNumber(phone);
		if (user != null) {
			System.out.println("in database:phone=" + user.getPhoneNumber() + ",pass=" + user.getPassword()
			+ ",createTime=" + user.getCreateTimeStamp());
			String createTime = user.getCreateTimeStamp();
			String checkedStr = "";
			try {
				checkedStr = MD5Coder.encode(password, createTime);
				System.out.println("checkedStr=" + checkedStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("In dataBase=" + user.getPassword());
			
			return checkedStr.equals(user.getPassword());
		}

		return false;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doGet()方法");
		doPost(request, response);
	}
}
