/**  
* Title: PaymentDao.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月15日    
*/
package com.justnd.octoryeserver.dao;

import java.util.List;

import com.justnd.octoryeserver.domain.Employee;
import com.justnd.octoryeserver.domain.Payment;

/**  
* Title: PaymentDao  
* Description:   
* @author JiaoDian 
* @date 2018年11月15日  
*/
public interface PaymentDao extends BaseDao<Payment> {
	/**  
	 * Title: findByEmp  
	 * Description:   根据员工查询月结薪水
	 * @param emp 员工
	 * @return  该员工对应的月结薪水集合
	 */ 
	List<Payment> findByEmp(Employee emp);
	
	/**  
	 * Title: findByMonthAndEmp  
	 * Description:   根据员工和发薪月份来查询月结薪水
	 * @param payMonth 发薪月份
	 * @param emp 领薪的员工
	 * @return  指定员工、指定月份的月结薪水
	 */ 
	Payment findByMonthAndEmp(String payMonth, Employee emp);
}
