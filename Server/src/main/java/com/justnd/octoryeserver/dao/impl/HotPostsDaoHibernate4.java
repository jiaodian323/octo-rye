/**   
* @Title: HotPostsDaoHibernate4.java 
* @Package com.justnd.octoryeserver.dao.impl 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月11日 下午11:17:08  
*/
package com.justnd.octoryeserver.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.justnd.octoryeserver.dao.HotPostsDao;
import com.justnd.octoryeserver.domain.Article;
import com.justnd.octoryeserver.domain.HotPosts;

/** 
* @ClassName: HotPostsDaoHibernate4 
* @Description: TODO
* @author JD
* @date 2018年12月11日 下午11:17:08 
*  
*/
public class HotPostsDaoHibernate4 extends BaseDaoHibernate4<HotPosts> implements HotPostsDao {
	/**  
	 * Title: findByDate  
	 * Description:   
	 * @param date
	 * @return  
	 */
	@Override
	public Set<Article> findByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
