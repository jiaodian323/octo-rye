/**  
* Title: ApplicationDao.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月15日    
*/
package com.justnd.octoryeserver.dao;

import java.util.List;

import com.justnd.octoryeserver.domain.Application;
import com.justnd.octoryeserver.domain.Employee;

/**  
* Title: ApplicationDao  
* Description:   
* @author JiaoDian 
* @date 2018年11月15日  
*/
public interface ApplicationDao extends BaseDao<Application> {
	/**  
	 * Title: findByEmp  
	 * Description:   根据员工查询未处理的异动申请
	 * @param emp 需要查询的员工
	 * @return  该员工对应的未处理的异动申请
	 */ 
	List<Application> findByEmp(Employee emp);
}
