/**  
* Title: AttendDaohibernate4.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月16日    
*/
package com.justnd.octoryeserver.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.justnd.octoryeserver.dao.AttendDao;
import com.justnd.octoryeserver.domain.Attend;
import com.justnd.octoryeserver.domain.AttendType;
import com.justnd.octoryeserver.domain.Employee;

/**
 * Title: AttendDaohibernate4 Description:
 * 
 * @author JiaoDian
 * @date 2018年11月16日
 */
public class AttendDaoHibernate4 extends BaseDaoHibernate4<Attend> implements AttendDao {
	public List<Attend> findByEmpAndMonth(Employee emp, String month) {
		return find(
				"from Attend as a where a.employee=?0 " + "and substring(a.dutyDay , 0 , 7)=?=1",
				emp, month);
	}

	public List<Attend> findByEmpAndDutyDay(Employee emp, String dutyDay) {
		return find("from Attend as a where a.employee=?0 and " + "a.dutyDay=?1", emp, dutyDay);
	}

	public Attend findByEmpAndDutyDayAndCome(Employee emp, String dutyDay, boolean isCome) {
		List<Attend> a1 = findByEmpAndDutyDay(emp, dutyDay);
		if (a1 != null && a1.size() > 1) {
			for (Attend attend : a1) {
				if (attend.getIsCome() == isCome) {
					return attend;
				}
			}
		}
		return null;
	}

	public List<Attend> findByEmpUnAttend(Employee emp, AttendType type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String end = sdf.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -3);
		String start = sdf.format(c.getTime());
		return find("from Attend as a where a.employee=?0 and "
				+ "a.type != ?1 and a.dutyDay between ?2 and ?3", emp, type, start, end);
	}
}
