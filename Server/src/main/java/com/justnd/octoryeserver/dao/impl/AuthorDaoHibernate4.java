/**   
* @Title: AuthorDaoHibernate4.java 
* @Package com.justnd.octoryeserver.dao.impl 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月11日 下午10:38:53  
*/
package com.justnd.octoryeserver.dao.impl;

import java.util.List;

import org.junit.Test;

import com.justnd.octoryeserver.dao.AuthorDao;
import com.justnd.octoryeserver.domain.Author;

/**
 * @ClassName: AuthorDaoHibernate4
 * @Description: TODO
 * @author JD
 * @date 2018年12月11日 下午10:38:53
 * 
 */
public class AuthorDaoHibernate4 extends BaseDaoHibernate4<Author> implements AuthorDao {

	/**
	 * Title: findByName Description: 根据作者名字模糊查询
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public List<Author> findByName(String name) {
		List<Author> authors = find("select p from Author p where p.authorName like '%" + name + "%'");
		if (authors != null && authors.size() >= 1) {
			return authors;
		}
		return null;
	}

	/**
	 * Title: findByArea Description:
	 * 
	 * @param area
	 * @return
	 */
	@Override
	public List<Author> findByArea(String area) {
		List<Author> authors = find("select p from Author p where p.area=?0", area);
		if (authors != null && authors.size() >= 1) {
			return authors;
		}
		return null;
	}
}
