/**  
* Title: EmployeeDaoHibernate4.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月16日    
*/
package com.justnd.octoryeserver.dao.impl;

import java.util.List;

import com.justnd.octoryeserver.dao.EmployeeDao;
import com.justnd.octoryeserver.domain.Employee;

/**
 * Title: EmployeeDaoHibernate4 Description:
 * 
 * @author JiaoDian
 * @date 2018年11月16日
 */
public class EmployeeDaoHibernate4 extends BaseDaoHibernate4<Employee> implements EmployeeDao {

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.EmployeeeDao#findByNameAndPass(www.justnd.hrsystem.domain.Employee)
	 */
	@Override
	public List<Employee> findByNameAndPass(Employee emp) {
		return find("select p from Employee p where p.name = ?0 and p.pass=?1", emp.getName(),
				emp.getPass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see www.justnd.hrsystem.dao.EmployeeeDao#findByName(java.lang.String)
	 */
	@Override
	public Employee findByName(String name) {
		List<Employee> emps = find("select e from Employee e where e.name = ?0", name);
		if (emps !=null && emps.size() >=1) {
			return emps.get(0);
		}
		return null;
	}

}
