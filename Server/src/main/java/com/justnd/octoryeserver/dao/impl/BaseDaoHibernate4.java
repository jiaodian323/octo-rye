/**  
* Title: BaseDaoHibernate4.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月9日    
*/
package com.justnd.octoryeserver.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.justnd.octoryeserver.dao.BaseDao;

/**
 * Title: BaseDaoHibernate4 Description:
 * 
 * @author JiaoDian
 * @date 2018年11月9日
 */
@Transactional
public class BaseDaoHibernate4<T> implements BaseDao<T> {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	/**
	 * @Title: getCurrentSession @Description: TODO
	 *         该方法是提供给诸如测试框架中需要获取当前session时使用的方法，如果不通过此方法，
	 *         直接在诸如测试方法中使用该Dao组件的getSessionFactory().getCurrentSession()获取当前session对象时
	 *         会报异常（Could not obtain transaction-synchronized Session for current thread）,
	 *         因为此类被设定为AOP切入点，外部只能通过事务的形式获取session。另外需要注意的是，当此类或子类内部需要使用该方法时，
	 *         记得需要使用AOP代理去调用，否则该方法的事务切面将不生效。
	 * @param @return
	 * 			@return Session @throws
	 */
	public Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T get(Class<T> entityClazz, Serializable id) {
		return (T) getSessionFactory().getCurrentSession().get(entityClazz, id);
	}

	public Serializable save(T entity) {
		return getSessionFactory().getCurrentSession().save(entity);
	}

	public void update(T entity) {
		getSessionFactory().getCurrentSession().saveOrUpdate(entity);
	}

	public void delete(T entity) {
		getSessionFactory().getCurrentSession().delete(entity);
	}

	public void delete(Class<T> entityClazz, Serializable id) {
		getSessionFactory().getCurrentSession()
				.createQuery("delete " + entityClazz.getSimpleName() + " en where en.id = ?0")
				.setParameter("0", id).executeUpdate();
	}

	public List<T> findAll(Class<T> entityClazz) {
		return find("select en from " + entityClazz.getSimpleName() + " en");
	}

	public long findCount(Class<T> entityClazz) {
		List<?> l = find("select count(*) from " + entityClazz.getSimpleName());
		if (l != null && l.size() == 1) {
			return (Long) l.get(0);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	protected List<T> find(String hql) {
		return (List<T>) getSessionFactory().getCurrentSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	protected List<T> find(String hql, Object... params) {
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		for (int i = 0, len = params.length; i < len; i++) {
			query.setParameter(i + "", params[i]);
		}
		return (List<T>) query.list();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByPage(String hql, int pageNo, int pageSize) {
		return getSessionFactory().getCurrentSession().createQuery(hql)
				.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByPage(String hql, int pageNo, int pageSize, Object... params) {
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		for (int i = 0, len = params.length; i < len; i++) {
			query.setParameter(i + "", params[i]);
		}
		return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}
}
