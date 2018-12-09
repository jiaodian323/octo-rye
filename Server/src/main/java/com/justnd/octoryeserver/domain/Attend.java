/**  
* Title: Attend.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月5日    
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**  
* Title: Attend  
* Description:   
* @author JiaoDian 
* @date 2018年11月5日  
*/
@Entity
@Table(name="attend_inf")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Attend implements Serializable{
	
	private static final long serialVersionUID = 7548462977100167580L;
	
	/**
	* 出勤标识
	*/  
	@Id @Column(name="attend_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	/**
	* 出勤日期
	*/  
	@Column(name="duty_day", nullable=false, length=50)
	private String dutyDay;
	/**
	* 打卡时间
	*/  
	@Column(name="punch_time")
	private Date punchTime;
	/**
	* 代表本次打卡是否为上班打卡
	*/  
	@Column(name="is_come", nullable=false)
	private boolean isCome;
	/**
	* 本次出勤的类型
	*/  
	@ManyToOne(targetEntity=AttendType.class)
	@JoinColumn(name="type_id", nullable=false)
	private AttendType type;
	/**
	* 本次出勤关联的员工
	*/  
	@ManyToOne(targetEntity=Employee.class)
	@JoinColumn(name="emp_id", nullable=false)
	private Employee employee;
	
	public Attend() {}
	
	public Attend(Integer id, String dutyDay, Date punchTime, boolean isCome,
			AttendType type, Employee employee) {
		this.id = id;
		this.dutyDay = dutyDay;
		this.punchTime = punchTime;
		this.isCome = isCome;
		this.type = type;
		this.employee = employee;
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
	 * @return the dutyDay
	 */
	public String getDutyDay() {
		return dutyDay;
	}

	/**
	 * @param dutyDay the dutyDay to set
	 */
	public void setDutyDay(String dutyDay) {
		this.dutyDay = dutyDay;
	}

	/**
	 * @return the punchTime
	 */
	public Date getPunchTime() {
		return punchTime;
	}

	/**
	 * @param punchTime the punchTime to set
	 */
	public void setPunchTime(Date punchTime) {
		this.punchTime = punchTime;
	}

	/**
	 * @return the isCome
	 */
	public boolean getIsCome() {
		return isCome;
	}

	/**
	 * @param isCome the isCome to set
	 */
	public void setIsCome(boolean isCome) {
		this.isCome = isCome;
	}

	/**
	 * @return the type
	 */
	public AttendType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AttendType type) {
		this.type = type;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dutyDay == null) ? 0 : dutyDay.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + (isCome ? 1231 : 1237);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Attend other = (Attend) obj;
		if (dutyDay == null) {
			if (other.dutyDay != null) return false;
		} else if (!dutyDay.equals(other.dutyDay)) return false;
		if (employee == null) {
			if (other.employee != null) return false;
		} else if (!employee.equals(other.employee)) return false;
		
		if (isCome != other.isCome) return false;
		return true;
	}
}
