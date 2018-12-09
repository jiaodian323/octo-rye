/**   
* @Title: ArticleDaoHibernate4.java 
* @Package com.justnd.magazineserver.dao.impl 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月8日 下午8:55:16  
*/
package com.justnd.octoryeserver.dao.impl;

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
	 * Title: findById Description: 根据文章ID检索文章
	 * 
	 * @param articleId
	 * @return
	 */
	@Override
	public Article findById(Integer articleId) {
		List<Article> articles = find("select p from Article p where p.id=?0", articleId);
		if (articles != null && articles.size() >= 1) {
			return articles.get(0);
		}
		
		return null;
	}

	/**
	 * Title: findByAuthor Description: 根据作者查找该作者名下的所有文章
	 * 
	 * @param author
	 * @return
	 */
	@Override
	public List<Article> findByAuthor(Author author) {
		List<Article> articles = find("select p from Article p where p.author=?0", author);
		if (articles != null && articles.size() >= 1) {
			return articles;
		}
		
		return null;
	}

	/**
	 * Title: findByTags Description:
	 * 
	 * @param tag
	 * @return
	 */
	@Override
	public List<Article> findByTags(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

}
