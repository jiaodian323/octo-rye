/**   
* @Title: Comment.java 
* @Package com.justnd.octoryeserver.domain 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月7日 下午3:35:16  
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/** 
* @ClassName: Comment 
* @Description: TODO
* @author JD
* @date 2019年1月7日 下午3:35:16 
*  
*/
@Entity
@Table(name = "comments_inf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment implements Serializable{
	private static final long serialVersionUID = -7834627571675253015L;
	
	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** 
	* @Fields owner : TODO 评论发表人
	*/ 
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable=false)
	private User owner;
	
	/** 
	* @Fields sendTime : TODO 评论发表时间
	*/ 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "publishTime")
	private Date sendTime;
	
	/** 
	* @Fields content : TODO 评论内容
	*/ 
	@Column(name = "content", nullable = false, length = 500)
	private String content;
	
	/** 
	* @Fields likes : TODO 被点赞的数量
	*/ 
	@Column(name = "likes")
	private Integer likes;

	@ManyToOne(targetEntity = Article.class)
	@JoinColumn(name = "article_id", nullable=false)
	@Cascade(CascadeType.MERGE)
	private Article article;

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
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the likes
	 */
	public Integer getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
}
