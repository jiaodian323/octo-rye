/**   
* @Title: HotPosts.java 
* @Package www.justnd.magazineserver.domain 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月8日 上午2:58:12  
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** 
* @ClassName: HotPosts 
* @Description: TODO
* @author JD
* @date 2018年12月8日 上午2:58:12 
*  
*/
@Entity
@Table(name="hotPost_inf")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class HotPosts implements Serializable{

	private static final long serialVersionUID = 5142128576177920777L;
	
	/** 
	* @Fields id : TODO ID
	*/ 
	@Id @Column(name="hotPost_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	/** 
	* @Fields postDate : TODO 推荐日期
	*/ 
	@Temporal(TemporalType.DATE)
	@Column(name="postDate", nullable=false)
	private Date postDate;
	
	/** 
	* @Fields hotPosts : TODO 推荐文章列表
	*/ 
	@ManyToMany(targetEntity=Article.class)
	@JoinTable(name="hotPost_article",
			joinColumns=@JoinColumn(name="hotPost_id",referencedColumnName="hotPost_id"),
			inverseJoinColumns=@JoinColumn(name="article_id", referencedColumnName="article_id"))
	private Set<Article> posts;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the hotPosts
	 */
	public Set<Article> getHotPosts() {
		return posts;
	}

	/**
	 * @param hotPosts the hotPosts to set
	 */
	public void setHotPosts(Set<Article> hotPosts) {
		this.posts = hotPosts;
	}

}
