/**  
* Title: UserDao.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年12月2日    
*/
package com.justnd.octoryeserver.dao;

import com.justnd.octoryeserver.domain.User;

/**
 * Title: UserDao Description:
 * 
 * @author JiaoDian
 * @date 2018年12月2日
 */
public interface UserDao extends BaseDao<User> {
	
	/** 
	* @Title: findMD5PassStrByUsername 
	* @Description: TODO 根据用户名查找用户密码
	* @param @return
	* @return String
	* @throws 
	*/
	User findByUsername(String username);
	
	
}