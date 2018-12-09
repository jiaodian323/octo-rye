/**  
* Title: AttendType.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月14日    
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**  
* Title: AttendType  
* Description:   
* @author JiaoDian 
* @date 2018年11月14日  
*/

@Entity
@Table(name="attend_type_inf")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class AttendType
	implements Serializable
{
	private static final long serialVersionUID = 4759383418537971120L;
	/**
	* 类型标识
	*/  
	@Id @Column(name="type_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	/**
	* 出勤类型
	*/  
	@Column(name="type_name", nullable=false , length=50)
	private String name;
	/**
	* 
	*/  
	@Column(name="amerce_amount", nullable=false)
	private double amerce;

	public AttendType()
	{
	}
	public AttendType(Integer id , String name , double amerce)
	{
		this.id = id;
		this.name = name;
		this.amerce = amerce;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId()
	{
		return this.id;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}

	public void setAmerce(double amerce)
	{
		this.amerce = amerce;
	}
	public double getAmerce()
	{
		return this.amerce;
	}
}