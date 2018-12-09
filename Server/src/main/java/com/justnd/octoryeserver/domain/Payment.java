/**  
* Title: Payment.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月5日    
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**  
* Title: Payment  
* Description:   
* @author JiaoDian 
* @date 2018年11月5日  
*/
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="payment_inf")
public class Payment
	implements Serializable
{
	private static final long serialVersionUID = 3039518302345677279L;
	
	@Id @Column(name="pay_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="pay_month", nullable=false, length=50)
	private String payMonth;
	@Column(name="pay_amount", nullable=false)
	private double amount;
	@ManyToOne(targetEntity=Employee.class)
	@JoinColumn(name="emp_id", nullable=false)
	private Employee employee;

	public Payment()
	{
	}

	public Payment(Integer id , String payMonth ,
		double amount , Employee employee)
	{
		this.id = id;
		this.payMonth = payMonth;
		this.amount = amount;
		this.employee = employee;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public Integer getId()
	{
		return this.id;
	}

	public void setPayMonth(String payMonth)
	{
		this.payMonth = payMonth;
	}
	
	public String getPayMonth()
	{
		return this.payMonth;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	public double getAmount()
	{
		return this.amount;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}
	public Employee getEmployee()
	{
		return this.employee;
	}
}
