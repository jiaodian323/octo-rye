/**   
* @Title: AritcleDaoTest.java 
* @Package com.justnd.octoryeserver.dao.test 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月12日 下午3:40:18  
*/
package com.justnd.octoryeserver.test.dao;

import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.justnd.octoryeserver.dao.impl.ArticleDaoHibernate4;
import com.justnd.octoryeserver.dao.impl.AuthorDaoHibernate4;
import com.justnd.octoryeserver.domain.Article;
import com.justnd.octoryeserver.domain.Author;

/**
 * @ClassName: AritcleDaoTest
 * @Description: TODO
 * @author JD
 * @date 2018年12月12日 下午3:40:18
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
		"classpath*:/daoContext.xml" })
public class ArticleDaoTest extends AbstractJUnit4SpringContextTests {
	@Resource(name = "articleDaoN")
	ArticleDaoHibernate4 articleDaoTest;
	@Resource(name = "authorDaoN")
	AuthorDaoHibernate4 authorDaoTest;
	// @Autowired
	// HibernateTransactionManager transactionManager;

	@Test
	public void insertTest() {
		 Author authorA = new Author();
		 authorA.setAuthorName("吴彦祖");
		 authorA.setArea("香港");
		 authorA.setIntroduction("我长得真帅");
		 authorDaoTest.save(authorA);

		Article articleA = new Article();
		articleA.setTitle("这是第50篇测试文章");
		articleA.setAuthor(authorA);
		// Clob content = Hibernate.getLobCreator(getSession())
		// .createClob("谢霆锋已经有杂志正经八百地对他做深度访问，让他谈感情、谈家庭、谈事业发展，");
		LobHelper lobHelper = getSession().getLobHelper();
		Clob content = lobHelper.createClob("谢霆锋的第50次");
		articleA.setContent(content);
		articleA.setHeadImage("http://url");
		Date timeA;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeA = df.parse(df.format(new Date()));
			articleA.setPublishTime(timeA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		articleA.setTags("test");

		articleDaoTest.save(articleA);
	}

	public Session getSession() {
		Session currentSession;
		try {
			currentSession = articleDaoTest.getCurrentSession();
			return currentSession;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
