/**  
* Title: PaymentDaoHibernate4.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月16日    
*/
package com.justnd.octoryeserver.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.justnd.octoryeserver.dao.PaymentDao;
import com.justnd.octoryeserver.domain.Employee;
import com.justnd.octoryeserver.domain.Payment;

/**  
* Title: PaymentDaoHibernate4  
* Description:   
* @author JiaoDian 
* @date 2018年11月16日  
*/
public class PaymentDaoHibernate4 extends BaseDaoHibernate4<Payment> implements PaymentDao {

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public Payment get(Class<Payment> entityClazz, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#save(java.lang.Object)
	 */
	@Override
	public Serializable save(Payment entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(Payment entity) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Payment entity) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#delete(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public void delete(Class<Payment> entityClazz, Serializable id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#findAll(java.lang.Class)
	 */
	@Override
	public List<Payment> findAll(Class<Payment> entityClazz) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#findCount(java.lang.Class)
	 */
	@Override
	public long findCount(Class<Payment> entityClazz) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.PaymentDao#findByEmp(www.justnd.hrsystem.domain.Employee)
	 */
	@Override
	public List<Payment> findByEmp(Employee emp) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.PaymentDao#findByMonthAndEmp(java.lang.String, www.justnd.hrsystem.domain.Employee)
	 */
	@Override
	public Payment findByMonthAndEmp(String payMonth, Employee emp) {
		// TODO Auto-generated method stub
		return null;
	}

}
