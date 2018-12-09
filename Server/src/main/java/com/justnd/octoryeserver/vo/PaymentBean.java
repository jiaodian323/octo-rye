package com.justnd.octoryeserver.vo;

import java.io.Serializable;

public class PaymentBean implements Serializable
{
	private static final long serialVersionUID = 48L;
	private String payMonth;
	private double amount;

	public PaymentBean()
	{
	}

	public PaymentBean(String payMonth , double amount)
	{
		this.payMonth = payMonth;
		this.amount = amount;
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

}