/**   
* @Title: Author.java 
* @Package www.justnd.magazineserver.domain 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月7日 下午4:41:44  
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** 
* @ClassName: Author 
* @Description: TODO
* @author JD
* @date 2018年12月7日 下午4:41:44 
*  
*/
@Entity
@Table(name="author_inf")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Author implements Serializable{

	private static final long serialVersionUID = 3280144056999496272L;
	
	/** 
	* @Fields id : TODO 作者ID
	*/ 
	@Id @Column(name="author_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	/** 
	* @Fields authorName : TODO 作者笔名
	*/ 
	@Column(name="authorName", nullable=false, unique=true)
	private String authorName;
	
	/** 
	* @Fields area : TODO 所属地区
	*/ 
	@Column(name="area", nullable=true)
	private String area;
	
	/** 
	* @Fields introduction : TODO 作者简介
	*/ 
	@Column(name="introduction", length=1000)
	private String introduction;

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
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Author.class)
			return false;
		
		if (this.id == ((Author)obj).id)
			return true;
		else 
			return false;
	}
}
