/**  
* Title: ManagerDao.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月15日    
*/
package com.justnd.octoryeserver.dao;

import java.util.List;

import com.justnd.octoryeserver.domain.Manager;

/**  
* Title: ManagerDao  
* Description:   
* @author JiaoDian 
* @date 2018年11月15日  
*/
public interface ManagerDao extends BaseDao<Manager> {
	/**  
	 * Title: findByNameAndPass  
	 * Description:   根据用户名和密码查询经理
	 * @param mgr 包含制定用户名、密码的经理
	 * @return  符合制定用户名和密码的经理
	 */ 
	List<Manager> findByNameAndPass(Manager mgr);
	
	/**  
	 * Title: findByName  
	 * Description:   根据用户名查找经理
	 * @param name 经理的名字
	 * @return  符合该用户名的经理
	 */ 
	Manager findByName(String name);
}
