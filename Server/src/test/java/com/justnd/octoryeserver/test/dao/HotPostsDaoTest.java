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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.justnd.octoryeserver.dao.impl.ArticleDaoHibernate4;
import com.justnd.octoryeserver.dao.impl.AuthorDaoHibernate4;
import com.justnd.octoryeserver.dao.impl.HotPostsDaoHibernate4;
import com.justnd.octoryeserver.domain.Article;
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
			timeA = df.parse("2018-12-20");
			hotPost.setPostDate(timeA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Set<Article> articles = new HashSet<Article>();
		articles.add(articleDaoTest.get(Article.class, 19));
		articles.add(articleDaoTest.get(Article.class, 18));
		articles.add(articleDaoTest.get(Article.class, 20));
		articles.add(articleDaoTest.get(Article.class, 21));
		articles.add(articleDaoTest.get(Article.class, 24));
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
		HotPosts needToDelete = hotPostsDaoTest.get(HotPosts.class, 3);
		hotPostsDaoTest.delete(needToDelete);
	}

	@Test
	public void deleteMethodBTest() {
		hotPostsDaoTest.delete(HotPosts.class, 4);
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

	public void printHotPostsString(List<HotPosts> hotPosts) {
		if (hotPosts == null || hotPosts.size() == 0) {
			System.out.println("List is empty~~~");
		}

		for (int i = 0; i < hotPosts.size(); i++) {
			System.out.println(
					"HotPost id=" + hotPosts.get(i).getId() + ",postDate=" + hotPosts.get(i).getPostDate()
							+ ",Set<Article>=\n" + getArticleSetString(hotPosts.get(i).getPosts()));
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
