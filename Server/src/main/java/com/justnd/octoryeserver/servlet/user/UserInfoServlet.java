/**   
* @Title: UserInfoServlet.java 
* @Package com.justnd.octoryeserver.servlet.user 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年4月10日 下午5:37:05  
*/
package com.justnd.octoryeserver.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.justnd.octoryeserver.beans.base.BaseBean;
import com.justnd.octoryeserver.beans.user.UserInfoBean;
import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.domain.User;
import com.justnd.octoryeserver.servlet.base.BaseServlet;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.GsonUtil;

/** 
* @ClassName: UserInfoServlet 
* @Description: TODO 用户信息查询Servlet
* @author JD
* @date 2019年4月10日 下午5:37:05 
*  
*/
@WebServlet(name="UserInfoServlet", urlPatterns=("/s/user/info"))
public class UserInfoServlet extends BaseServlet {

	private static final long serialVersionUID = 4384425475694219755L;
	
	@Autowired
	UserDao userDao;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doPost()方法");
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行doGet()方法");
		response.setContentType("text/json;charset=utf-8");
		
		try (PrintWriter out = response.getWriter()) {
			String idStr = request.getParameter("userId").trim();
			int id = Integer.parseInt(idStr);
			out.write(getResponseStr(id));
			out.close();
		}
	}
	
	private String getResponseStr(int id){
		User user = userDao.get(User.class, id);
		
		if (user == null)
			return "";
		
		UserInfoBean dataBean = new UserInfoBean();
		dataBean.setNickName(user.getNickName());
		dataBean.setSex(user.getSex());
		dataBean.setProfilePicture(user.getProfilePicture());
		
		BaseBean<UserInfoBean> bean = new BaseBean<UserInfoBean>();
		bean.setCode(ConstantUtil.STATUS_CODE_SUCCESS);
		bean.setMessage(ConstantUtil.SUCCESS_USER_INFO_QUERY);
		bean.setData(dataBean);
		
		String jsonStr = GsonUtil.objectToJsonStr(bean);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("用户信息JsonStr:" + jsonStr);
		
		return jsonStr;
	}
	
}
