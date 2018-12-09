/**  
* Title: BaseDao.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月9日    
*/
package com.justnd.octoryeserver.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Title: BaseDao Description:
 * 
 * @author JiaoDian
 * @date 2018年11月9日
 */
public interface BaseDao<T> {
	T get(Class<T> entityClazz, Serializable id);

	Serializable save(T entity);

	void update(T entity);

	void delete(T entity);

	void delete(Class<T> entityClazz, Serializable id);

	List<T> findAll(Class<T> entityClazz);

	long findCount(Class<T> entityClazz);
}
