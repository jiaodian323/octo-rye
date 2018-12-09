/**  
* Title: ManagerDaoHibernate4.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月16日    
*/
package com.justnd.octoryeserver.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.justnd.octoryeserver.dao.ManagerDao;
import com.justnd.octoryeserver.domain.Manager;

/**  
* Title: ManagerDaoHibernate4  
* Description:   
* @author JiaoDian 
* @date 2018年11月16日  
*/
public class ManagerDaoHibernate4 extends BaseDaoHibernate4<Manager> implements ManagerDao {

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public Manager get(Class<Manager> entityClazz, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#save(java.lang.Object)
	 */
	@Override
	public Serializable save(Manager entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(Manager entity) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Manager entity) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#delete(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public void delete(Class<Manager> entityClazz, Serializable id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#findAll(java.lang.Class)
	 */
	@Override
	public List<Manager> findAll(Class<Manager> entityClazz) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.BaseDao#findCount(java.lang.Class)
	 */
	@Override
	public long findCount(Class<Manager> entityClazz) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.ManagerDao#findByNameAndPass(www.justnd.hrsystem.domain.Manager)
	 */
	@Override
	public List<Manager> findByNameAndPass(Manager mgr) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see www.justnd.hrsystem.dao.ManagerDao#findByName(java.lang.String)
	 */
	@Override
	public Manager findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
