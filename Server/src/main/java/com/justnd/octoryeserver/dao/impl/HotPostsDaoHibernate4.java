/**   
* @Title: HotPostsDaoHibernate4.java 
* @Package com.justnd.octoryeserver.dao.impl 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月11日 下午11:17:08  
*/
package com.justnd.octoryeserver.dao.impl;

import java.util.Date;
import java.util.List;

import com.justnd.octoryeserver.dao.HotPostsDao;
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
	public List<HotPosts> findByDate(Date date) {
		List<HotPosts> hotPosts = find("select p from HotPosts p where p.postDate=?0", date);
		if (hotPosts != null && hotPosts.size() >= 1) {
			return hotPosts;
		}
		
		return null;
	}

}
