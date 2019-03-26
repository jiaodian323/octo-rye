/**   
* @Title: SignUpCheckServlet.java 
* @Package com.justnd.octoryeserver.servlet 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月8日 下午8:29:23  
*/
package com.justnd.octoryeserver.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.justnd.octoryeserver.beans.base.BaseBean;
import com.justnd.octoryeserver.beans.user.UserInfoBean;
import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.domain.User;
import com.justnd.octoryeserver.servlet.base.BaseServlet;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.GsonUtil;

/**
 * @ClassName: SignUpCheckServlet
 * @Description: TODO
 * @author JD
 * @date 2019年3月8日 下午8:29:23
 * 
 */
@WebServlet(name = "SignUpCheckServlet", urlPatterns = ("/s/user/register/signupcheck"))
public class SignUpCheckServlet extends BaseServlet {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -8926096994358466070L;

	@Autowired
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("初始化SignUpCheckServlet");
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doPost()方法");
		response.setContentType("application/octet-stream;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String bodyStr = getRequestBodyStr(request);
			System.out.println("bodyStr:" + bodyStr);
			UserInfoBean user = GsonUtil.jsonStrToObject(bodyStr, UserInfoBean.class);
			String phoneNumber = user.getPhoneNumber();
			System.out.println("phoneNubmer:" + phoneNumber);

			@SuppressWarnings("rawtypes")
			BaseBean resultBean = new BaseBean();
			if (isSignedUp(phoneNumber)) {
				resultBean.setCode(1);
				resultBean.setMessage(ConstantUtil.PHONE_NUMBER_ALREADY_SIGNED_UP);
			} else {
				resultBean.setCode(0);
			}
			out.write(GsonUtil.objectToJsonStr(resultBean));
			out.close();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doGet()方法");
		doPost(request, response);
	}
	
	private boolean isSignedUp(String phoneNumber) {
		User user = userDao.findByPhoneNumber(phoneNumber);

		// 如该手机号已注册，则返回true
		if (user != null)
			return true;

		return false;
	}
}
