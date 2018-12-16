/**   
* @Title: ArticleDao.java 
* @Package www.justnd.magazineserver.dao 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月8日 上午2:12:00  
*/
package com.justnd.octoryeserver.dao;

import java.util.List;

import com.justnd.octoryeserver.domain.Article;
import com.justnd.octoryeserver.domain.Author;

/** 
* @ClassName: ArticleDao 
* @Description: TODO
* @author JD
* @date 2018年12月8日 上午2:12:00 
*  
*/
public interface ArticleDao extends BaseDao<Article> {
	/** 
	* @Title: findByAuthor 
	* @Description: TODO 根据作者查询文章列表
	* @param @param author
	* @param @return
	* @return List<Article>
	* @throws 
	*/
	List<Article> findByAuthor(Author author);
	
	/** 
	* @Title: findByTags 
	* @Description: TODO 根据标签查询该标签下的文章列表
	* @param @param tags
	* @param @return
	* @return List<Article>
	* @throws 
	*/
	List<Article> findByTags(String tags);
}
