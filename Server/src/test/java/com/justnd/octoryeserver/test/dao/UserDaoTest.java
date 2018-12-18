/**   
* @Title: UserDaoTest.java 
* @Package com.justnd.octoryeserver.dao.test 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月12日 下午3:35:24  
*/
package com.justnd.octoryeserver.test.dao;

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
public class UserDaoTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="userDaoN")
	UserDaoHibernate4 userDaoTest;
	
	@Test
	public void insertTest() {
		User userA = new User();
		userA.setUserName("nnnnn");
		userA.setPassword("22222");
		userA.setProfilePicture("pic A");
		
		User userB = new User();
		userB.setUserName("ddddd");
		userB.setPassword("33333");
		userB.setProfilePicture("pic B");
		
		User userC = new User();
		userC.setUserName("中文");
		userC.setPassword("娃娃");
		userC.setProfilePicture("地址");
		
		userDaoTest.save(userA);
		userDaoTest.save(userB);
	}
}
