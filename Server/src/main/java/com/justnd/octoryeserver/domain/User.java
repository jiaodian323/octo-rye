/**  
* Title: User.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月30日    
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**  
* Title: User  
* Description:   
* @author JiaoDian 
* @date 2018年11月30日  
*/
@Entity
@Table(name="user_inf")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable{
	
	private static final long serialVersionUID = -4288200195277529150L;
	
	@Id @Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username", nullable=false, length=50, unique=true)
	private String userName;
	
	@Column(name="nickname",length=20,unique=true)
	private String nickName;
	
	@Column(name = "sex")
	private int sex;
	
	@Column(name="pass", nullable=false)
	private String password;

	/** 
	* @Fields profilePicture : TODO 用户头像
	*/ 
	@Column(name="profilePicture", nullable=true, length=500)
	private String profilePicture;
	
	@Column(name="phoneNumber", nullable=false, unique=true)
	private String phoneNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime")
	private Date createTime;
	
	@Column(name = "createTimeStamp")
	private String createTimeStamp;
	
	@Column(name = "background")
	private String background;
	
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the profilePicture
	 */
	public String getProfilePicture() {
		return profilePicture;
	}

	/**
	 * @param profilePicture the profilePicture to set
	 */
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createTimeStamp
	 */
	public String getCreateTimeStamp() {
		return createTimeStamp;
	}

	/**
	 * @param createTimeStamp the createTimeStamp to set
	 */
	public void setCreateTimeStamp(String createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * @return the background
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(String background) {
		this.background = background;
	}
}
