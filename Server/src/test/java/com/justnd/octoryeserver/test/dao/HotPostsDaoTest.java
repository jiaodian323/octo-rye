/**   
* @Title: HotPostsDaoTest.java 
* @Package com.justnd.octoryeserver.dao.test 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月12日 下午3:55:54  
*/
package com.justnd.octoryeserver.test.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.justnd.octoryeserver.beans.base.BaseBean;
import com.justnd.octoryeserver.beans.recommend.RecommendBean;
import com.justnd.octoryeserver.dao.impl.ArticleDaoHibernate4;
import com.justnd.octoryeserver.dao.impl.AuthorDaoHibernate4;
import com.justnd.octoryeserver.dao.impl.HotPostsDaoHibernate4;
import com.justnd.octoryeserver.domain.Article;
import com.justnd.octoryeserver.domain.ContentType;
import com.justnd.octoryeserver.domain.HotPosts;

/**
 * @ClassName: HotPostsDaoTest
 * @Description: TODO
 * @author JD
 * @date 2018年12月12日 下午3:55:54
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
		"classpath*:/daoContext.xml" })
public class HotPostsDaoTest extends AbstractJUnit4SpringContextTests {
	@Resource(name = "hotPostsDaoN")
	HotPostsDaoHibernate4 hotPostsDaoTest;
	@Resource(name = "articleDaoN")
	ArticleDaoHibernate4 articleDaoTest;
	@Resource(name = "authorDaoN")
	AuthorDaoHibernate4 authorDaoTest;

	@Test
	public void getTest() {
		HotPosts hotPost = hotPostsDaoTest.get(HotPosts.class, 3);

		List<HotPosts> list = new ArrayList<HotPosts>();
		list.add(hotPost);
		printHotPostsString(list);
	}

	@Test
	public void insertTest() {
		HotPosts hotPost = new HotPosts();
		Date timeA;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			timeA = df.parse("2019-7-31");
			hotPost.setPostDate(timeA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Set<Article> articles = new HashSet<Article>();
		// Article
//		articles.add(articleDaoTest.get(Article.class, 27));
//		articles.add(articleDaoTest.get(Article.class, 28));
//		articles.add(articleDaoTest.get(Article.class, 30));
//		articles.add(articleDaoTest.get(Article.class, 31));
//		articles.add(articleDaoTest.get(Article.class, 32));
//		articles.add(articleDaoTest.get(Article.class, 34));

//		 articles.add(articleDaoTest.get(Article.class, 35));
//		 articles.add(articleDaoTest.get(Article.class, 36));
//		 articles.add(articleDaoTest.get(Article.class, 38));
//		 articles.add(articleDaoTest.get(Article.class, 39));
//		 articles.add(articleDaoTest.get(Article.class, 46));
//		 articles.add(articleDaoTest.get(Article.class, 49));
		 
//		 articles.add(articleDaoTest.get(Article.class, 37));
//		 articles.add(articleDaoTest.get(Article.class, 43));
//		 articles.add(articleDaoTest.get(Article.class, 45));
//		 articles.add(articleDaoTest.get(Article.class, 47));
//		 articles.add(articleDaoTest.get(Article.class, 48));
//		 articles.add(articleDaoTest.get(Article.class, 51));
//		 articles.add(articleDaoTest.get(Article.class, 52));
		 
		 articles.add(articleDaoTest.get(Article.class, 55));
		 articles.add(articleDaoTest.get(Article.class, 58));
		 articles.add(articleDaoTest.get(Article.class, 59));
		 articles.add(articleDaoTest.get(Article.class, 60));
		 articles.add(articleDaoTest.get(Article.class, 61));

		 // Music
//		articles.add(articleDaoTest.get(Article.class, 53));
//		articles.add(articleDaoTest.get(Article.class, 54));
		
		articles.add(articleDaoTest.get(Article.class, 56));
		articles.add(articleDaoTest.get(Article.class, 57));
		
		hotPost.setPosts(articles);

		hotPostsDaoTest.save(hotPost);
	}

	@Test
	public void updateTest() {
		HotPosts newHotPost = new HotPosts();
		newHotPost.setId(2);
		Date timeA;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeA = df.parse(df.format(new Date()));
			newHotPost.setPostDate(timeA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Set<Article> newArticles = new HashSet<Article>();
		newArticles.add(articleDaoTest.get(Article.class, 7));
		newArticles.add(articleDaoTest.get(Article.class, 8));
		newArticles.add(articleDaoTest.get(Article.class, 9));
		newHotPost.setPosts(newArticles);

		hotPostsDaoTest.update(newHotPost);
	}

	@Test
	public void deleteMethodATest() {
		HotPosts needToDelete = hotPostsDaoTest.get(HotPosts.class, 176);
		hotPostsDaoTest.delete(needToDelete);
	}

	@Test
	public void deleteMethodBTest() {
		hotPostsDaoTest.delete(HotPosts.class, 176);
	}

	@Test
	public void findAllTest() {
		List<HotPosts> list = hotPostsDaoTest.findAll(HotPosts.class);

		printHotPostsString(list);
	}

	@Test
	public void findCountTest() {
		System.out.println("数据库目前热门“文章列表”总数为：" + hotPostsDaoTest.findCount(HotPosts.class));
	}

	@Test
	public void findByDateTest() {
		try {
			Date time;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			time = df.parse("2018-12-19");
			List<HotPosts> hotPosts = hotPostsDaoTest.findByDate(time);

			printHotPostsString(hotPosts);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void deleteAllTest() {
		List<HotPosts> list = hotPostsDaoTest.findAll(HotPosts.class);
		for (HotPosts post : list) {
			hotPostsDaoTest.delete(HotPosts.class, post.getId());
		}
	}

	@Test
	public void getJsonStrTest() {
//		List<HotPosts> hotPosts = hotPostsDaoTest.findByDate(new Date());
//
//		if (hotPosts == null || hotPosts.size() == 0)
//			return;
//
//		BaseBean<RecommendBean> baseBean = new BaseBean<RecommendBean>();
//		baseBean.setCode(0);
//
//		RecommendBean dataBean = new RecommendBean();
//		List<RecommendBean.ResultBean> results = new ArrayList<>();
//
//		for (int i = 0; i < ContentType.values().length; i++) {
//			RecommendBean.ResultBean resultBean = new RecommendBean.ResultBean();
//			resultBean.setStyle(ContentType.values()[i].getName());
//			
//			for (int j = 0; j < hotPosts.size(); j++) {
//				Iterator<Article> iterator = hotPosts.get(i).getPosts().iterator();
//				while (iterator.hasNext()) {
//					Article article = iterator.next();
//					if (article != null) {
//						ContentType type = article.getType();
//						if (type == ContentType.values()[i]) {
//							RecommendBean.ResultBean.HeadBean resultHeadBean = new RecommendBean.ResultBean.HeadBean();
//							resultHeadBean.setTitle("热门焦点");
//							resultBean.setHead(resultHeadBean);
//							List<RecommendBean.ResultBean.BodyBean> resultBodyBeans = new ArrayList<>();
//							RecommendBean.ResultBean.BodyBean body = new RecommendBean.ResultBean.BodyBean();
//							body.setTitle(article.getTitle());
//							body.setStyle("gm_av");
//							body.setUp(article.getAuthor().getAuthorName());
//							body.setCover(article.getHeadImage());
//							body.setPageViewNum(article.getPageviewCount());
//							body.setDanmaku(article.getLikeNum().toString());
//
//							resultBodyBeans.add(body);
//						}
//					}
//				}
//			}
//		}
		
		
//		resultBean.setBody(resultBodyBeans);
//		results.add(resultBean);
//		dataBean.setResult(results);
//
//		baseBean.setData(dataBean);

		// Gson gson = new Gson();
//		Gson gson = new GsonBuilder().setLenient()// json宽松
//				.enableComplexMapKeySerialization()// 支持Map的key为复杂对象的形式
//				.serializeNulls() // 智能null
//				.setPrettyPrinting()// 调教格式
//				.create();
//		String gsonStr = gson.toJson(baseBean);
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("GsonStr:" + gsonStr);
	}

	public void printHotPostsString(List<HotPosts> hotPosts) {
		if (hotPosts == null || hotPosts.size() == 0) {
			System.out.println("List is empty~~~");
		}

		for (int i = 0; i < hotPosts.size(); i++) {
			System.out.println("HotPost id=" + hotPosts.get(i).getId() + ",postDate="
					+ hotPosts.get(i).getPostDate() + ",Set<Article>=\n"
					+ getArticleSetString(hotPosts.get(i).getPosts()));
		}
	}

	public String getArticleSetString(Set<Article> list) {
		try {
			if (list == null || list.size() == 0) {
				System.out.println("List is empty~~~~~~~~~~~~~~");
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		Iterator<Article> iterator = list.iterator();
		while (iterator.hasNext()) {
			Article article = iterator.next();
			sb.append(convertArticleToString(article) + "\n");
		}

		return sb.toString();
	}

	public String convertArticleToString(Article article) {
		return "Article id=" + article.getId() + ",title=" + article.getTitle() + ",content="
				+ article.getContent() + ",author id =" + article.getAuthor().getId()
				+ ",headImage=" + article.getHeadImage() + ",publishTime="
				+ article.getPublishTime() + ",tags=" + article.getTags();
	}
}
