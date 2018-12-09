package com.justnd.octoryeserver.vo;

import java.io.Serializable;

public class SalaryBean
	implements Serializable
{
	private static final long serialVersionUID = 48L;
	private String empName;
	private double amount;

	public SalaryBean()
	{
	}
	
	public SalaryBean(String empName , double amount)
	{
		this.empName = empName;
		this.amount = amount;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	
	public String getEmpName()
	{
		return this.empName;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	
	public double getAmount()
	{
		return this.amount;
	}

}