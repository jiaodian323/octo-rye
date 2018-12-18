/**   
* @Title: HotPostsDao.java 
* @Package com.justnd.magazineserver.dao 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月8日 下午8:39:43  
*/
package com.justnd.octoryeserver.dao;

import java.util.Date;
import java.util.List;

import com.justnd.octoryeserver.domain.HotPosts;

/** 
* @ClassName: HotPostsDao 
* @Description: TODO
* @author JD
* @date 2018年12月8日 下午8:39:43 
*  
*/
public interface HotPostsDao extends BaseDao<HotPosts> {
	/** 
	* @Title: findByDate 
	* @Description: TODO 根据日期查询当日的热门推荐
	* @param @param date
	* @param @return
	* @return Set<Article>
	* @throws 
	*/
	List<HotPosts> findByDate(Date date);
}
