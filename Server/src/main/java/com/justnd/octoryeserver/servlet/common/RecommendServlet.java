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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.justnd.octoryeserver.domain.ContentType;
import com.justnd.octoryeserver.domain.HotPosts;
import com.justnd.octoryeserver.servlet.base.BaseServlet;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.GsonUtil;

/**
 * @ClassName: RecommendServlet
 * @Description: TODO
 * @author JD
 * @date 2019年4月18日 下午9:54:50
 * 
 */
@WebServlet(name = "RecommendServlet", urlPatterns = ("/s/show/recommend"))
public class RecommendServlet extends BaseServlet {

	private static final long serialVersionUID = 1722882278436376202L;

	@Autowired
	private HotPostsDao hotPostDao;
	
	/** 
	* @Fields DATE_ARGUMENT_TAG : TODO 日期参数TAG
	*/ 
	private static final String DATE_ARGUMENT_TAG = "date";

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
			String queryDate = request.getParameter(DATE_ARGUMENT_TAG).trim();
			System.out.println("查询日期字符串为：" + queryDate);
			out.write(getResponseStr(queryDate));
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("RecommendServlet接受到Post请求，交由get请求处理");
		doGet(request, response);
	}

	public String getResponseStr(String dateStr) {
		Date date = stringToDate(dateStr);
		
		// 如果无法转换日期，直接返回
		if (date == null)
			return "";
		
		List<HotPosts> hotPosts = hotPostDao.findByDate(date);

		if (hotPosts == null || hotPosts.size() == 0)
			return "";

		BaseBean<RecommendBean> baseBean = new BaseBean<RecommendBean>();
		baseBean.setCode(ConstantUtil.STATUS_CODE_SUCCESS);
		baseBean.setMessage(ConstantUtil.SUCCESS_SEND_RECOMMENDED);

		RecommendBean dataBean = new RecommendBean();
		List<RecommendBean.ResultBean> results = new ArrayList<>();
		
		for (int i = 0; i < ContentType.values().length; i++) {
			RecommendBean.ResultBean resultBean = new RecommendBean.ResultBean();
			resultBean.setStyle(ContentType.values()[i].name());
			RecommendBean.ResultBean.HeadBean resultHeadBean = new RecommendBean.ResultBean.HeadBean();
			resultHeadBean.setTitle(ContentType.values()[i].name());
			resultBean.setHead(resultHeadBean);
			List<RecommendBean.ResultBean.BodyBean> resultBodyBeans = new ArrayList<>();
			for (int j = 0; j < hotPosts.size(); j++) {
				Iterator<Article> iterator = hotPosts.get(j).getPosts().iterator();
				while (iterator.hasNext()) {
					Article article = iterator.next();
					if (article != null) {
						ContentType type = article.getType();
						if (type == ContentType.values()[i]) {
							RecommendBean.ResultBean.BodyBean body = new RecommendBean.ResultBean.BodyBean();
							body.setParam(article.getId());
							body.setTitle(article.getTitle());
							body.setStyle(article.getType().name());
							body.setAuthorName(article.getAuthor().getAuthorName());
							body.setExtract(article.getExtract());
							body.setCover(article.getHeadImage());
							body.setPageViewNum(article.getPageviewCount());
							body.setLikeNum(article.getLikeNum());
							
							if (type == ContentType.MUSIC) {
								body.setAudio_url(article.getAudio_url());
								body.setAudio_platform(article.getAudio_platform());
								body.setAudio_platform_name(article.getAudio_platform_name());
								body.setAudio_platform_icon(article.getAudio_platform_icon());
								body.setMusic_name(article.getMusic_name());
								body.setAudio_author(article.getAudio_author());
								body.setAudio_album(article.getAudio_album());
								body.setAudio_cover(article.getAudio_cover());
								body.setAudio_duration(article.getAudio_durationS());
							}

							resultBodyBeans.add(body);
						}
					}
				}
			}
			resultBean.setBody(resultBodyBeans);
			results.add(resultBean);
		}
		dataBean.setResult(results);

		baseBean.setData(dataBean);
		String gsonStr = GsonUtil.objectToJsonStr(baseBean);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("GsonStr:" + gsonStr);

		return gsonStr;
	}
	
	/** 
	* @Title: stringToDate 
	* @Description: TODO 将特定日期格式的字符串转换为Date类实例
	* @param @param dateStr
	* @param @return
	* @return Date
	* @throws 
	*/
	private Date stringToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		DateFormat sdf = SimpleDateFormat.getDateInstance();
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}
}
