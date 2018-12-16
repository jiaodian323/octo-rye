/**   
* @Title: Article.java 
* @Package www.justnd.magazineserver.domain 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月7日 下午3:31:41  
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @ClassName: Article
 * @Description: TODO
 * @author JD
 * @date 2018年12月7日 下午3:31:41
 * 
 */
@Entity
@Table(name = "article_inf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {

	private static final long serialVersionUID = 2747520552361268345L;

	@Id
	@Column(name = "article_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * @Fields title : TODO 文章标题
	 */
	@Column(name = "title", nullable = false, length = 200)
	private String title;

	/**
	 * @Fields author : TODO 作者
	 */
	@ManyToOne(targetEntity = Author.class)
	@JoinColumn(name = "author_id", nullable=false)
	@Cascade(CascadeType.MERGE)
	private Author author;

	/**
	 * @Fields publishTime : TODO 发布时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "publishTime")
	private Date publishTime;

	/**
	 * @Fields content : TODO 文章内容
	 */
	@Lob
	@Column(name = "content")
	private Clob content;
	
//	@Column(name = "content", nullable = false)
//	private Clob content;

	/**
	 * @Fields headImage : TODO 文章头图片
	 */
	@Column(name = "headImage")
	private String headImage;

	/**
	 * @Fields contentImages : TODO 文章内容图片列表
	 */
	// @Column(name="contentImages", nullable=true)
	// private List<String> contentImages;

	/**
	 * @Fields tags : TODO 文章标签
	 */
	private String tags;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * @return the publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	public Clob getContent() {
		return content;
	}
	
	public void setContent(Clob content) {
		this.content = content;
	}
	
//	public Clob getContent() {
//		return content;
//	}

	/**
	 * @return the content
	 */
//	public Clob getContent() {
//		StringBuilder sb = new StringBuilder();
//		BufferedReader reader = null;
//		
//		if (content != null) {
//			try {
//				reader = new BufferedReader(content.getCharacterStream());
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//			try {
//				String str = "";
//				if (reader != null)
//					while ((str = reader.readLine()) != null) {
//						sb.append(str + "\r\n");
//					}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//		}
//		
//		return sb.toString();
//		
//		return content;
//	}

	/**
	 * @param content
	 *            the content to set
	 */
//	public void setContent(String content) {
//		// this.content = content;
//
//		try {
//			this.content = new javax.sql.rowset.serial.SerialClob(content
//					.toCharArray());
//		} catch (SerialException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * @return the headImage
	 */
	public String getHeadImage() {
		return headImage;
	}

	/**
	 * @param headImage
	 *            the headImage to set
	 */
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the contentImages
	 */
	// public List<String> getContentImages() {
	// return contentImages;
	// }
	//
	// /**
	// * @param contentImages the contentImages to set
	// */
	// public void setContentImages(List<String> contentImages) {
	// this.contentImages = contentImages;
	// }

}
