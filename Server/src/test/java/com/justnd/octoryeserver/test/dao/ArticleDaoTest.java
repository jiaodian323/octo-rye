/**   
* @Title: AritcleDaoTest.java 
* @Package com.justnd.octoryeserver.dao.test 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月12日 下午3:40:18  
*/
package com.justnd.octoryeserver.test.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

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
import com.justnd.octoryeserver.domain.ContentType;

/**
 * @ClassName: AritcleDaoTest
 * @Description: TODO
 * @author JD
 * @date 2018年12月12日 下午3:40:18
 * 
 */
/**
 * @ClassName: ArticleDaoTest
 * @Description: TODO
 * @author JD
 * @date 2018年12月17日 下午3:36:02
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
	public void getTest() {
		Article article = articleDaoTest.get(Article.class, 12);
		System.out.println(article.getId());
		System.out.println("author id=" + article.getAuthor().getId());
		System.out.println("author authorName=" + article.getAuthor().getAuthorName());
		System.out.println("author area=" + article.getAuthor().getArea());
		System.out.println(article.getTitle());
		System.out.println(article.getContent());
		System.out.println(article.getPublishTime().toString().split(" ")[0]); // 只提取日期，不提取具体时间
		System.out.println(article.getHeadImage());
		System.out.println(article.getTags());
	}

	@Test
	public void insertTest() {

		Author authorA = authorDaoTest.get(Author.class, 1);

		Article articleA = new Article();
		articleA.setTitle("这是第10000篇测试文章，测试大文本");
		articleA.setAuthor(authorA); 
//      Clob content =
//		Hibernate.getLobCreator(getSession()) //
//				.createClob("谢霆锋已经有杂志正经八百地对他做深度访问，让他谈感情、谈家庭、谈事业发展，"); // LobHelper
//		lobHelper = getSession().getLobHelper(); // Clob content =
//		lobHelper.createClob("谢霆锋的第100次");
		String clobStr = getStringFromFile("jinlitest.txt");
		System.out.println("文章长度" + clobStr.length());
		System.out.println(clobStr);
		articleA.setContent(clobStr);
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

//		for (int i = 0; i < 5; i++) {
//			Author author = authorDaoTest.get(Author.class, 1);
//
//			Article articleA = new Article();
//			articleA.setTitle("这是第" + (i + 300) + "篇测试文章");
//			articleA.setAuthor(author);
//			// Clob content = Hibernate.getLobCreator(getSession())
//			// .createClob("谢霆锋已经有杂志正经八百地对他做深度访问，让他谈感情、谈家庭、谈事业发展，");
//			// LobHelper lobHelper = getSession().getLobHelper();
//			// Clob content = lobHelper.createClob("谢霆锋的第100次");
//			articleA.setContent("谢霆锋的第" + (i + 300) + "次");
//			articleA.setHeadImage("http://url");
//			Date timeA;
//			try {
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				timeA = df.parse(df.format(new Date()));
//				articleA.setPublishTime(timeA);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			articleA.setTags("test");
//
//			articleDaoTest.save(articleA);
//		}

	}

	@Test
	public void updateTest() {
		Article article = new Article();
		article.setId(20);
		String str = getStringFromFile("test3.txt");
		article.setContent(str);

		articleDaoTest.update(article);
	}

	@Test
	public void deleteMethodATest() {
		Article article = articleDaoTest.get(Article.class, 10);

		articleDaoTest.delete(article);
	}

	@Test
	public void deleteMethodBTest() {
		articleDaoTest.delete(Article.class, 11);
	}

	@Test
	public void findAllTest() {
		List<Article> allArticles = articleDaoTest.findAll(Article.class);

		printArticleList(allArticles);
	}
	
	@Test
	public void updateAllTest() {
		Random rand = new Random();
		
		List<Article> allArticles = articleDaoTest.findAll(Article.class);
		
		for (int i = 0; i < allArticles.size(); i++) {
			int typeInt = rand.nextInt(4);
			ContentType type = getContentType(typeInt);
			
			Article article = allArticles.get(i);
			article.setType(type);
			
			articleDaoTest.update(article);
		}
	}

	@Test
	public void findAllCountTest() {
		System.out.println("数据库目前文章总数为：" + articleDaoTest.findCount(Article.class));
	}

	@Test
	public void findByAuthorTest() {
		Author author = authorDaoTest.get(Author.class, 9);
		List<Article> allArticles = articleDaoTest.findByAuthor(author);

		printArticleList(allArticles);
	}

	@Test
	public void findByTagsTest() {
		List<Article> articles = articleDaoTest.findByTags("标签");

		printArticleList(articles);
	}

	@Test
	public void findByPublishTimeTest() {

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

	/**
	 * @Title: clobToStringBuffer @Description: TODO @param @param
	 *         clob @param @return @param @throws SQLException @param @throws
	 *         IOException @return StringBuffer
	 *         使用StringBuffer为其增加线程安全，虽然可能会降低效率 @throws
	 */
	public StringBuffer clobToStringBuffer(Clob clob) throws SQLException, IOException {
		Reader reader = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(reader);

		StringBuffer sb = new StringBuffer();
		String tempStr = br.readLine();
		while (tempStr != null) {
			sb.append(tempStr);
			tempStr = br.readLine();
		}

		return sb;
	}
	
	public ContentType getContentType(int i) {
		switch (i) {
		case 0:
			return ContentType.Article;
		case 1:
			return ContentType.Music;
		case 2:
			return ContentType.Video;
		case 3:
			return ContentType.Audio;
		default:
			return ContentType.Article;
		}
	}

	/**
	 * @Title: printArticleList @Description: TODO 打印Article列表 @param @param
	 * list @return void @throws
	 */
	public void printArticleList(List<Article> list) {
		if (list == null || list.size() == 0) {
			System.out.println("List is empty~~~~~~~~~~~~~~");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			System.out.println("id=" + list.get(i).getId() + ",title=" + list.get(i).getTitle()
					+ ",content=" + list.get(i).getContent() + ",author id ="
					+ list.get(i).getAuthor().getId() + ",headImage=" + list.get(i).getHeadImage()
					+ ",publishTime=" + list.get(i).getPublishTime() + ",tags="
					+ list.get(i).getTags());
		}
	}

	public String getStringFromFile(String fileName) {
		File file = new File(
				this.getClass().getClassLoader().getResource("").getPath() + fileName);
		String str = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buf = new byte[fis.available()];
			fis.read(buf);
			str = new String(buf);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str.toString();
	}
}
