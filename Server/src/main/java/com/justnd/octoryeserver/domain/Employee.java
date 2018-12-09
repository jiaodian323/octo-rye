/**  
* Title: Employee.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月5日    
*/
package com.justnd.octoryeserver.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Title: Employee Description:
 * 
 * @author JiaoDian
 * @date 2018年11月5日
 */
@Entity
@Table(name="employee_inf")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorColumn(name="emp_type",discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue(value="1")
public class Employee implements Serializable{

	private static final long serialVersionUID = -6837726641961810830L;
	
	/**
	* 标识属性
	*/  
	@Id @Column(name="emp_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	/**
	* 员工姓名
	*/  
	@Column(name="emp_name", nullable=false, length=50, unique=true)
	private String name;
	/**
	* 员工密码
	*/  
	@Column(name="emp_pass", nullable=false, length=50)
	private String pass;
	/**
	* 员工工资
	*/  
	@Column(name="emp_salary", nullable=false)
	private double salary;
	/**
	* 员工对应的经理
	*/  
	@ManyToOne(targetEntity=Manager.class)
	@JoinColumn(name="mgr_id")
	private Manager manager;
	/**
	* 员工对应的出勤记录
	*/  
	@OneToMany(targetEntity=Attend.class, mappedBy="employee")
	private Set<Attend> attends = new HashSet<>();
	/**
	* 员工对应的工资支付记录
	*/  
	@OneToMany(targetEntity=Payment.class, mappedBy="employee")
	private Set<Payment> payments = new HashSet<>();

	public Employee() {

	}

	public Employee(Integer id, String name, String pass, double salary, Manager manager,
			Set<Attend> attends, Set<Payment> payments) {
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.salary = salary;
		this.manager = manager;
		this.attends = attends;
		this.payments = payments;
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * @return the manager
	 */
	public Manager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/**
	 * @return the attends
	 */
	public Set<Attend> getAttends() {
		return attends;
	}

	/**
	 * @param attends the attends to set
	 */
	public void setAttends(Set<Attend> attends) {
		this.attends = attends;
	}

	/**
	 * @return the payments
	 */
	public Set<Payment> getPayments() {
		return payments;
	}

	/**
	 * @param payments the payments to set
	 */
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name))
			return false;
		if (pass == null) {
			if (other.pass != null) return false;
		} else if (!pass.equals(other.pass))
			return false;
		return true;
	}
}
