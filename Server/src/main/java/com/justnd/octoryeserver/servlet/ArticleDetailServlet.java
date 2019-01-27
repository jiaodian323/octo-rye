/**   
* @Title: ContentDetailServlet.java 
* @Package com.justnd.octoryeserver.servlet 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月27日 上午2:11:42  
*/
package com.justnd.octoryeserver.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.justnd.octoryeserver.beans.ArticleDetailBean;
import com.justnd.octoryeserver.dao.ArticleDao;
import com.justnd.octoryeserver.domain.Article;
import com.justnd.octoryeserver.domain.Author;
import com.justnd.octoryeserver.utils.GsonUtil;

/** 
* @ClassName: ContentDetailServlet 
* @Description: TODO
* @author JD
* @date 2019年1月27日 上午2:11:42 
*  
*/
@WebServlet(name="ArticleDetailServlet", urlPatterns=("/s/show/articledetail"))
public class ArticleDetailServlet extends HttpServlet {

	/** 
	* @Fields serialVersionUID : TODO 
	*/ 
	private static final long serialVersionUID = -8562809472596492895L;

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("初始化ContentDetailServlet");
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ArticleDetailServlet接受到get请求");
		response.setContentType("text/json;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String idStr = request.getParameter("aid").trim();
			int id = Integer.parseInt(idStr);
			out.write(getResponseStr(id));
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ArticleDetailServlet接受到Post请求，交由get请求处理");
		doGet(request, response);
	}

	public String getResponseStr(int id) {
		Article article = articleDao.get(Article.class, id);
		
		if (article == null)
			return "";
		
		ArticleDetailBean bean = new ArticleDetailBean();
		bean.setType(article.getType().toString());
		bean.setTitle(article.getTitle());
		bean.setPublishTime("");
		ArticleDetailBean.Author authorBean = new ArticleDetailBean.Author();
		Author author = article.getAuthor();
		authorBean.setName(author.getAuthorName());
		authorBean.setIntroduction(author.getIntroduction());
		bean.setAuthor(authorBean);
		bean.setExtract(article.getExtract());
		bean.setContent(article.getContent());
		bean.setHeadImage(article.getHeadImage());
		bean.setLikeNum(article.getLikeNum());
		bean.setPageviewCount(article.getPageviewCount());

		String gsonStr = GsonUtil.toJsonString(bean);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("GsonStr:" + gsonStr);
		
		return gsonStr;
	}
}
