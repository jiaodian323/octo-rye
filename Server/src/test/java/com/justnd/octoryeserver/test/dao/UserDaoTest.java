/**   
* @Title: UserDaoTest.java 
* @Package com.justnd.octoryeserver.dao.test 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月12日 下午3:35:24  
*/
package com.justnd.octoryeserver.test.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.justnd.octoryeserver.dao.impl.UserDaoHibernate4;
import com.justnd.octoryeserver.domain.User;

/**
 * @ClassName: UserDaoTest
 * @Description: TODO
 * @author JD
 * @date 2018年12月12日 下午3:35:24
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
		"classpath*:/daoContext.xml" })
public class UserDaoTest extends AbstractJUnit4SpringContextTests {
	@Resource(name = "userDaoN")
	UserDaoHibernate4 userDaoTest;

	@Test
	public void insertTest() {
		User userA = new User();
		userA.setUserName("niujjjj");
		userA.setPassword("jjjjjjjj");
		userA.setProfilePicture("http://pic/niujjjjjjjj");
		userA.setPhoneNumber("13888888888");

		userDaoTest.save(userA);
	}

	@Test
	public void updateTest() {
		List<User> allUsers = userDaoTest.findAll(User.class);

		Iterator<User> it = allUsers.iterator();
		while (it.hasNext()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date createTime = null;
			try {
				createTime = df.parse(df.format(new Date()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			User user = it.next();
			if (createTime != null) {
				user.setCreateTime(createTime);
				userDaoTest.update(user);
			}
		}
	}

	@Test
	public void findByUsernameTest() {
		User user = userDaoTest.findByUsername("noname");

		if (user != null) {
			System.out.println("检索出结果：");
			System.out.println("id:" + user.getId());
			System.out.println("username:" + user.getUserName());
			System.out.println("pass:" + user.getPassword());
			System.out.println("profilePicture:" + user.getProfilePicture());
			System.out.println("phoneNumber:" + user.getPhoneNumber());
		} else {
			System.out.println("无此用户名");
		}
	}

	@Test
	public void findByPhoneNumberTest() {
		User user = userDaoTest.findByPhoneNumber("13000000000");

		if (user != null) {
			System.out.println("检索出结果：");
			System.out.println("id:" + user.getId());
			System.out.println("username:" + user.getUserName());
			System.out.println("pass:" + user.getPassword());
			System.out.println("profilePicture:" + user.getProfilePicture());
			System.out.println("phoneNumber:" + user.getPhoneNumber());
		} else {
			System.out.println("无此手机号");
		}
	}
}
