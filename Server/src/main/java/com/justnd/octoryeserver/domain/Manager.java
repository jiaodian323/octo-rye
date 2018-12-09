/**  
* Title: Manager.java  
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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**  
* Title: Manager  
* Description:   
* @author JiaoDian 
* @date 2018年11月5日  
*/
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorValue(value="2")
public class Manager extends Employee implements Serializable{
	private static final long serialVersionUID = -5287252017856169221L;
	
	/**
	* 该经理管理的部门
	*/  
	@Column(name="dept_name", length=50)
	private String dept;
	/**
	* 该经理对应的所有员工
	*/  
	@OneToMany(targetEntity=Employee.class, mappedBy="manager")
	private Set<Employee> employees = new HashSet<>();
	/**
	* 该经理签署的所有批复
	*/
	@OneToMany(targetEntity=CheckBack.class, mappedBy="manager")
	private Set<CheckBack> checks = new HashSet<>();
	
	public Manager() {}
	
	public Manager(String dept, Set<Employee> employees, Set<CheckBack> checks) {
		this.dept = dept;
		this.employees = employees;
		this.checks = checks;
	}

	/**
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return the employees
	 */
	public Set<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	/**
	 * @return the checks
	 */
	public Set<CheckBack> getChecks() {
		return checks;
	}

	/**
	 * @param checks the checks to set
	 */
	public void setChecks(Set<CheckBack> checks) {
		this.checks = checks;
	}
}
