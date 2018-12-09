/**  
* Title: ApplicationDaoHibernate4.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月16日    
*/
package com.justnd.octoryeserver.dao.impl;

import java.util.List;

import com.justnd.octoryeserver.dao.ApplicationDao;
import com.justnd.octoryeserver.domain.Application;
import com.justnd.octoryeserver.domain.Employee;

/**
 * Title: ApplicationDaoHibernate4 Description:
 * 
 * @author JiaoDian
 * @date 2018年11月16日
 */
public class ApplicationDaoHibernate4 extends BaseDaoHibernate4<Application>
		implements ApplicationDao {
	/**
	 * Title: findByEmp Description: 根据员工查询未处理的异动申请
	 * 
	 * @param emp 需要查询的员工
	 * @return 该员工对应的未处理的异动申请
	 */
	public List<Application> findByEmp(Employee emp) {
		return find("select a from application as a where " + "a.attend.employee=?0", emp);
	}
}
