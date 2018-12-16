/**   
* @Title: HotPostsDaoTest.java 
* @Package com.justnd.octoryeserver.dao.test 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月12日 下午3:55:54  
*/
package com.justnd.octoryeserver.test.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.justnd.octoryeserver.dao.impl.HotPostsDaoHibernate4;

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
    @Resource(name="hotPostsDaoN")
    HotPostsDaoHibernate4 hotPostsDaoTest;
    
    @Test
    public void testHotPostsDao() {
    	
    }
}
