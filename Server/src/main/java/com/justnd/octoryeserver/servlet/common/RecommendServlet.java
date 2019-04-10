/**   
* @Title: HotPostServlet.java 
* @Package com.justnd.octoryeserver.servlet 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月7日 下午12:46:52  
*/
package com.justnd.octoryeserver.servlet.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.justnd.octoryeserver.beans.base.BaseBean;
import com.justnd.octoryeserver.beans.recommend.RecommendBean;
import com.justnd.octoryeserver.dao.HotPostsDao;
import com.justnd.octoryeserver.domain.Article;
import com.justnd.octoryeserver.domain.HotPosts;
import com.justnd.octoryeserver.servlet.base.BaseServlet;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.GsonUtil;

/**
 * @ClassName: HotPostServlet
 * @Description: TODO
 * @author JD
 * @date 2019年1月7日 下午12:46:52
 * 
 */
@WebServlet(name="RecommendServlet", urlPatterns=("/s/show/recommend"))
public class RecommendServlet extends BaseServlet {

	private static final long serialVersionUID = 1722882278436376202L;

	@Autowired
	private HotPostsDao hotPostDao;
	
	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("初始化RecommendServlet");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("RecommendServlet接受到get请求");
		response.setContentType("text/json;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
//			String queryDate = request.getParameter("Date").trim();
			out.write(getResponseStr());
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("RecommendServlet接受到Post请求，交由get请求处理");
		doGet(request, response);
	}

	public String getResponseStr() {
		List<HotPosts> hotPosts = hotPostDao.findByDate(new Date());
		
		if (hotPosts == null || hotPosts.size() == 0)
			return "";

		BaseBean<RecommendBean> baseBean = new BaseBean<RecommendBean>();
		baseBean.setCode(ConstantUtil.STATUS_CODE_SUCCESS);
		baseBean.setMessage(ConstantUtil.SUCCESS_SEND_RECOMMENDED);
		
		RecommendBean dataBean = new RecommendBean();
		List<RecommendBean.ResultBean> results = new ArrayList<>();
		RecommendBean.ResultBean resultBean = new RecommendBean.ResultBean();
		resultBean.setType("test");
		RecommendBean.ResultBean.HeadBean resultHeadBean = new RecommendBean.ResultBean.HeadBean();
		resultHeadBean.setTitle("推荐文章");
		resultBean.setHead(resultHeadBean);
		List<RecommendBean.ResultBean.BodyBean> resultBodyBeans = new ArrayList<>();
		for (int i = 0; i < hotPosts.size(); i++) {
			Iterator<Article> iterator = hotPosts.get(i).getPosts().iterator();
			while (iterator.hasNext()) {
				Article article = iterator.next();
				if (article != null) {
					RecommendBean.ResultBean.BodyBean body = new RecommendBean.ResultBean.BodyBean();
					body.setParam(article.getId());
					body.setTitle(article.getTitle());
					String testStr = article.getType().toString();
					System.out.println("testStr = " + testStr);
					body.setStyle(article.getType().toString());
					body.setAuthorName(article.getAuthor().getAuthorName());
					body.setExtract(article.getExtract());
					body.setCover(article.getHeadImage());
					body.setPageViewNum(article.getPageviewCount());
					body.setLikeNum(article.getLikeNum());

					resultBodyBeans.add(body);
				}
			}
		}
		resultBean.setBody(resultBodyBeans);
		results.add(resultBean);
		dataBean.setResult(results);
		
		baseBean.setData(dataBean);
		String gsonStr = GsonUtil.objectToJsonStr(baseBean);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("GsonStr:" + gsonStr);
		
		return gsonStr;
	}
}
