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
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.justnd.octoryeserver.beans.base.BaseBean;
import com.justnd.octoryeserver.beans.user.SignUpBean;
import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.domain.User;
import com.justnd.octoryeserver.security.MD5Coder;
import com.justnd.octoryeserver.servlet.base.BaseServlet;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.GsonUtil;

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
		System.out.println("初始化RegisterServlet");
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doPost()方法");
		response.setContentType("text/json;charset=utf-8");
		
		try (PrintWriter out = response.getWriter()) {
			String bodyStr = getRequestBodyStr(request);
			System.out.println("bodyStr:" + bodyStr);
			SignUpBean sign = GsonUtil.jsonStrToObject(bodyStr, SignUpBean.class);
			
			User user = new User();
			user.setUserName(sign.getPhoneNumber());
			user.setPhoneNumber(sign.getPhoneNumber());
			long now = System.currentTimeMillis();
			String nowStamp = String.valueOf(now);
			user.setCreateTimeStamp(nowStamp);
			user.setCreateTime(new Date(now));
			user.setPassword(MD5Coder.encode(sign.getPass(), nowStamp));
			
			userDao.save(user);

			@SuppressWarnings("rawtypes")
			BaseBean resultBean = new BaseBean();
			resultBean.setCode(1);
			resultBean.setMessage(ConstantUtil.SUCCESS_SIGN_UP);
			out.write(GsonUtil.objectToJsonStr(resultBean));
			out.close();
		} catch(Exception e) {
            e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doGet()方法");
		doPost(request, response);
	}
}
