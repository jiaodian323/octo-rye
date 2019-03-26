/**   
* @Title: SecurityServlet.java 
* @Package com.justnd.octoryeserver.servlet.security 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月15日 下午3:22:00  
*/
package com.justnd.octoryeserver.servlet.security;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.justnd.octoryeserver.beans.base.BaseBean;
import com.justnd.octoryeserver.beans.security.SecurityBean;
import com.justnd.octoryeserver.security.RSACoder;
import com.justnd.octoryeserver.servlet.base.BaseServlet;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.FileUtil;
import com.justnd.octoryeserver.util.GsonUtil;

/**
 * @ClassName: SecurityServlet
 * @Description: TODO
 * @author JD
 * @date 2019年3月15日 下午3:22:00
 * 
 */
@WebServlet(name = "SecurityServlet", urlPatterns = ("/s/sec/info"))
public class SecurityServlet extends BaseServlet {
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

		try (PrintWriter out = response.getWriter()) {
			// 目前这里未跟进客户端信息做相应业务处理，而是直接将密钥信息发送给了客户端
			// 日后需要增加更多业务逻辑在这部分代码之前
			String bodyStr = getRequestBodyStr(request);

			System.out.println("bodyStr:" + bodyStr);
			out.write(getResponseStr());
			out.close();
		}
	}

	private String getResponseStr() {
		BaseBean<SecurityBean> baseBean = new BaseBean<SecurityBean>();
		baseBean.setCode(ConstantUtil.STATUS_CODE_SUCCESS);

		SecurityBean dataBean = new SecurityBean();
		String publicKeyStr = "";
		try {
			publicKeyStr = RSACoder.loadKeyStrByFile(
					new File(FileUtil.getWebInfSecPath() + ConstantUtil.PUBLIC_KEY_FILE_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBean.setPublicKeyString(publicKeyStr);

		baseBean.setData(dataBean);
		String jsonStr = GsonUtil.objectToJsonStr(baseBean);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("安全信息响应JsonStr:" + jsonStr);

		return jsonStr;
	}
}
