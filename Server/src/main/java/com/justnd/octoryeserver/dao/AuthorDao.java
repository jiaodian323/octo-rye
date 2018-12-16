/**   
* @Title: AuthorDao.java 
* @Package com.justnd.octoryeserver.dao 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月11日 下午9:38:25  
*/
package com.justnd.octoryeserver.dao;

import java.util.List;

import com.justnd.octoryeserver.domain.Author;

/** 
* @ClassName: AuthorDao 
* @Description: TODO
* @author JD
* @date 2018年12月11日 下午9:38:25 
*  
*/
public interface AuthorDao extends BaseDao<Author> {
	/** 
	* @Title: findByName 
	* @Description: TODO 根据查询字符串查找模糊匹配到的作者列表
	* @param @param name
	* @param @return
	* @return List<Author>
	* @throws 
	*/
	List<Author> findByName(String name);
	
	/** 
	* @Title: findByArea 
	* @Description: TODO 根据所属地区进行查询该地区里的作者列表
	* @param @param area
	* @param @return
	* @return List<Author>
	* @throws 
	*/
	List<Author> findByArea(String area);
}
