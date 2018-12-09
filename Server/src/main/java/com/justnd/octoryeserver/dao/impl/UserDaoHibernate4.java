/**   
* @Title: UserDaoHibernate4.java 
* @Package www.justnd.hrsystem.dao.impl 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月2日 下午3:14:53  
*/
package com.justnd.octoryeserver.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.domain.User;

/**
 * @ClassName: UserDaoHibernate4
 * @Description: TODO
 * @author JD
 * @date 2018年12月2日 下午3:14:53
 * 
 */
public class UserDaoHibernate4 extends BaseDaoHibernate4<User> implements UserDao {

	/**
	 * Title: findMD5PassStrByUsername Description:
	 * 
	 * @return
	 * 
	 */
	@Override
	public User findByUsername(String username) {
		List<User> users = find("select p from User p where p.userName = ?0", username);
		if (users != null && users.size() >= 1) {
			return users.get(0);
		}
		
		return null;
	}

}
