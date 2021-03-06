/**   
* @Title: ArticleDaoHibernate4.java 
* @Package com.justnd.magazineserver.dao.impl 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月8日 下午8:55:16  
*/
package com.justnd.octoryeserver.dao.impl;

import java.util.Date;
import java.util.List;

import com.justnd.octoryeserver.dao.ArticleDao;
import com.justnd.octoryeserver.domain.Article;
import com.justnd.octoryeserver.domain.Author;

/**
 * @ClassName: ArticleDaoHibernate4
 * @Description: TODO
 * @author JD
 * @date 2018年12月8日 下午8:55:16
 * 
 */
public class ArticleDaoHibernate4 extends BaseDaoHibernate4<Article> implements ArticleDao {
	/**  
	 * Title: findByAuthor  
	 * Description:   根据作者名字，查找该作者名下的所有文章
	 * @param author
	 * @return  
	 */ 
	@Override
	public List<Article> findByAuthor(Author author) {
		List<Article> articles = find("select p from Article p where p.author.id=?0", author.getId());
		if (articles != null && articles.size() >= 1) {
			return articles;
		}
		
		return null;
	}

	/**  
	 * Title: findByTags  
	 * Description:   
	 * @param tag
	 * @return  
	 */ 
	@Override
	public List<Article> findByTags(String tag) {
		List<Article> articles = find("select p from Article p where p.tags like '%" + tag + "%'");
		if (articles != null && articles.size() >= 1) {
			return articles;
		}
		
		return null;
	}
	
	
	/** 
	* @Title: findByPublishTime 
	* @Description: TODO
	* @param @param date
	* @param @return
	* @return List<Article>
	* @throws 
	*/
	/*********还未测试，此方法暂时不可用**************/
	public List<Article> findByPublishTime (Date date) {
		List<Article> articles = find("select p from Article p where p.publishTime=?0", date);
		if (articles != null && articles.size() >= 1) {
			return articles;
		}
		
		return null;
	}
}
