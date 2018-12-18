/**   
* @Title: AuthorDaoTest.java 
* @Package com.justnd.octoryeserver.test 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月12日 上午1:39:06  
*/
package com.justnd.octoryeserver.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.justnd.octoryeserver.dao.impl.AuthorDaoHibernate4;
import com.justnd.octoryeserver.domain.Author;

/**
 * @ClassName: AuthorDaoTest
 * @Description: TODO
 * @author JD
 * @date 2018年12月12日 上午1:39:06
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
		"classpath*:/daoContext.xml" })
public class AuthorDaoTest extends AbstractJUnit4SpringContextTests {
	@Resource(name="authorDaoN")
	AuthorDaoHibernate4 authorDaoTest;
	
	@Test
	public void getTest() {
		Author author = authorDaoTest.get(Author.class, 9);
		System.out.println(author.getId());
		System.out.println(author.getAuthorName());
		System.out.println(author.getArea());
		System.out.println(author.getIntroduction());
	}

	@Test
	public void insertTest() {
//		Author authorA = new Author();
//		authorA.setAuthorName("韩寒");
//		authorA.setArea("上海");
//		authorA.setIntroduction("《三重门》、《就这么飘来飘去》作者，电影《后会无期》导演");
//		
//		Author authorB = new Author();
//		authorB.setAuthorName("路遥");
//		authorB.setArea("陕西");
//		authorB.setIntroduction("《平凡的世界》作者");
//		
//		Author authorC = new Author();
//		authorC.setAuthorName("鲁迅");
//		authorC.setArea("浙江");
//		authorC.setIntroduction("《呐喊》作者");
//
//		authorDaoTest.save(authorA);
//		authorDaoTest.save(authorB);
//		authorDaoTest.save(authorC);
		
		Author author = new Author();
		author.setAuthorName("吴彦祖");
		author.setArea("香港");
		author.setIntroduction("其实我是一个演员");
		authorDaoTest.save(author);
	}
	
	@Test
	public void updateTest() {
		Author originalAuthor = authorDaoTest.get(Author.class, 9);
		System.out.println("id=" + originalAuthor.getId() + ",authorName=" + originalAuthor.getAuthorName()
		+ ",area=" + originalAuthor.getArea() + ",introduction=" + originalAuthor.getIntroduction());
		
		Author newAuthor = new Author();
		newAuthor.setId(originalAuthor.getId());
		newAuthor.setAuthorName("第二新吴彦祖");
		newAuthor.setArea("第二新香港");
		newAuthor.setIntroduction("这是第二新的介绍");
		authorDaoTest.update(newAuthor);
	}
	
	@Test
	public void deleteMethodATest() {
		Author authorA = new Author();
		authorA.setId(16);
		authorDaoTest.delete(authorA);
	}
	
	@Test
	public void deleteMethodBTest() {
		authorDaoTest.delete(Author.class, 14);
	}
	
	@Test
	public void findAllTest() {
		List<Author> authors = authorDaoTest.findAll(Author.class);
		printAuthorList(authors);
	}
	
	@Test 
	public void findCountTest() {
		System.out.println("数据库目前作者总数为：" + authorDaoTest.findCount(Author.class));
	}
	
	@Test
	public void findByNameTest() {
		List<Author> results = authorDaoTest.findByName("韩寒");
		printAuthorList(results);
	}
	
	@Test
	public void findByArea() {
		List<Author> results = authorDaoTest.findByArea("香港");
		printAuthorList(results);
	}
	
	public void printAuthorList(List<Author> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("id=" + list.get(i).getId() + ",authorName=" + list.get(i).getAuthorName()
			+ ",area=" + list.get(i).getArea() + ",introduction=" + list.get(i).getIntroduction());
		}
	}
}
