/**  
* Title: EmployeeeDao.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月15日    
*/
package com.justnd.octoryeserver.dao;

import java.util.List;

import com.justnd.octoryeserver.domain.Employee;

/**  
* Title: EmployeeeDao  
* Description:   
* @author JiaoDian 
* @date 2018年11月15日  
*/
public interface EmployeeDao extends BaseDao<Employee> {
	/**  
	 * Title: findByNameAndPass  
	 * Description:   根据用户名和密码查询员工
	 * @param emp 员工
	 * @return  符合制定用户名和密码的员工集合
	 */ 
	List<Employee> findByNameAndPass(Employee emp);
	
	/**  
	 * Title: findByName  
	 * Description:   根据用户名查询员工
	 * @param name 员工的用户名
	 * @return  符合用户名的员工
	 */ 
	Employee findByName(String name);
}
