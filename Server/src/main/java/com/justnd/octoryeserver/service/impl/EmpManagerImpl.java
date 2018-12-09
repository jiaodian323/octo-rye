/**  
* Title: EmpManagerImpl.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月16日    
*/
package com.justnd.octoryeserver.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.justnd.octoryeserver.dao.ApplicationDao;
import com.justnd.octoryeserver.dao.AttendDao;
import com.justnd.octoryeserver.dao.AttendTypeDao;
import com.justnd.octoryeserver.dao.CheckBackDao;
import com.justnd.octoryeserver.dao.EmployeeDao;
import com.justnd.octoryeserver.dao.ManagerDao;
import com.justnd.octoryeserver.dao.PaymentDao;
import com.justnd.octoryeserver.dao.UserDao;
import com.justnd.octoryeserver.domain.Application;
import com.justnd.octoryeserver.domain.Attend;
import com.justnd.octoryeserver.domain.AttendType;
import com.justnd.octoryeserver.domain.Employee;
import com.justnd.octoryeserver.domain.Manager;
import com.justnd.octoryeserver.domain.Payment;
import com.justnd.octoryeserver.service.EmpManager;
import com.justnd.octoryeserver.vo.AttendBean;
import com.justnd.octoryeserver.vo.PaymentBean;

/**
 * Title: EmpManagerImpl Description:
 * 
 * @author JiaoDian
 * @date 2018年11月16日
 */
public class EmpManagerImpl implements EmpManager {
	private ApplicationDao appDao;
	private AttendDao attendDao;
	private AttendTypeDao typeDao;
	private CheckBackDao checkDao;
	private EmployeeDao empDao;
	private ManagerDao mgrDao;
	private PaymentDao payDao;
	private UserDao userDao;

	/**
	 * @param appDao
	 *            the appDao to set
	 */
	public void setAppDao(ApplicationDao appDao) {
		this.appDao = appDao;
	}

	/**
	 * @param attendDao
	 *            the attendDao to set
	 */
	public void setAttendDao(AttendDao attendDao) {
		this.attendDao = attendDao;
	}

	/**
	 * @param typeDao
	 *            the typeDao to set
	 */
	public void setTypeDao(AttendTypeDao typeDao) {
		this.typeDao = typeDao;
	}

	/**
	 * @param checkDao
	 *            the checkDao to set
	 */
	public void setCheckDao(CheckBackDao checkDao) {
		this.checkDao = checkDao;
	}

	/**
	 * @param empDao
	 *            the empDao to set
	 */
	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}

	public void setMgrDao(ManagerDao mgrDao) {
		this.mgrDao = mgrDao;
	}

	/**
	 * @param payDao
	 *            the payDao to set
	 */
	public void setPayDao(PaymentDao payDao) {
		this.payDao = payDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Title: validLogin Description: 以经理身份来验证登录
	 * 
	 * @param mgr
	 *            登录的经理身份
	 * @return 登录后的身份确认：0 为登录失败， 1为登录emp 2为登录mgr
	 */
	public int validLogin(Manager mgr) {
		if (mgrDao.findByNameAndPass(mgr).size() >= 1) {
			return LOGIN_MGR;
		} else if (empDao.findByNameAndPass(mgr).size() >= 1) {
			return LOGIN_EMP;
		} else {
			return LOGIN_FAIL;
		}
	}

	public void autoPunch() {
		System.out.println("自动插入旷工记录");
		List<Employee> emps = empDao.findAll(Employee.class);
		String dutyDay = new java.sql.Date(System.currentTimeMillis()).toString();
		for (Employee e : emps) {
			AttendType atype = typeDao.get(AttendType.class, 6);
			Attend a = new Attend();
			a.setDutyDay(dutyDay);
			a.setType(atype);
			if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < AM_LIMIT) {
				a.setIsCome(true);
			} else {
				a.setIsCome(false);
			}
			a.setEmployee(e);
			attendDao.save(a); // 这里的效率需要考虑到，如果员工很多，遍历每个员工且短时间依次插入数据库的效率肯定低下
		}
	}

	public void autoPay() {
		System.out.println("自动插入工资结算");
		List<Employee> emps = empDao.findAll(Employee.class);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -15);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String payMonth = sdf.format(c.getTime());

		for (Employee e : emps) {
			Payment pay = new Payment();
			double amount = e.getSalary();
			List<Attend> attends = attendDao.findByEmpAndMonth(e, payMonth);
			for (Attend a : attends) {
				amount += a.getType().getAmerce();
			}

			pay.setPayMonth(payMonth);
			pay.setEmployee(e);
			pay.setAmount(amount);
			payDao.save(pay);
		}
	}

	public int validPunch(String user, String dutyDay) {
		Employee emp = empDao.findByName(user);
		if (emp == null) {
			return NO_PUNCH;
		}

		List<Attend> attends = attendDao.findByEmpAndDutyDay(emp, dutyDay);
		if (attends == null || attends.size() <= 0) {
			return NO_PUNCH;
		} else if (attends.size() == 1 && attends.get(0).getIsCome()
				&& attends.get(0).getPunchTime() == null) {
			return COME_PUNCH;
		} else if (attends.size() == 1 && attends.get(0).getPunchTime() == null) {
			return LEAVE_PUNCH;
		} else if (attends.size() == 2) {
			if (attends.get(0).getPunchTime() == null && attends.get(1).getPunchTime() == null) {
				return BOTH_PUNCH;
			} else if (attends.get(1).getPunchTime() == null) {
				return LEAVE_PUNCH;
			} else {
				return NO_PUNCH;
			}
		}
		return NO_PUNCH;
	}

	public int punch(String user, String dutyDay, boolean isCome) {
		Employee emp = empDao.findByName(user);
		if (emp == null) {
			return PUNCH_FAIL;
		}

		Attend attend = attendDao.findByEmpAndDutyDayAndCome(emp, dutyDay, isCome);
		if (attend == null) {
			return PUNCH_FAIL;
		}

		if (attend.getPunchTime() != null) {
			return PUNCHED;
		}

		System.out.println("===========打卡=============");
		int punchHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		attend.setPunchTime(new Date());

		if (isCome) {
			if (punchHour < COME_LIMIT) {
				attend.setType(typeDao.get(AttendType.class, 1));
			}

			else if (punchHour < LATE_LIMIT) {
				attend.setType(typeDao.get(AttendType.class, 4));
			}
		} else {
			if (punchHour >= LEAVE_LIMIT) {
				attend.setType(typeDao.get(AttendType.class, 1));
			} else if (punchHour >= EARLY_LIMIT) {
				attend.setType(typeDao.get(AttendType.class, 5));
			}
		}
		attendDao.update(attend);
		return PUNCH_SUCC;
	}

	public List<PaymentBean> empSalary(String empName) {
		Employee emp = empDao.findByName(empName);
		List<Payment> pays = payDao.findByEmp(emp);
		List<PaymentBean> result = new ArrayList<PaymentBean>();
		for (Payment p : pays) {
			result.add(new PaymentBean(p.getPayMonth(), p.getAmount()));
		}
		return result;
	}

	public List<AttendBean> unAttend(String empName) {
		AttendType type = typeDao.get(AttendType.class, 1);
		Employee emp = empDao.findByName(empName);
		List<Attend> attends = attendDao.findByEmpUnAttend(emp, type);
		List<AttendBean> result = new ArrayList<AttendBean>();
		for (Attend att : attends) {
			result.add(new AttendBean(att.getId(), att.getDutyDay(), att.getType().getName(),
					att.getPunchTime()));
		}
		return result;
	}
	
	public List<AttendType> getAllType() {
		return typeDao.findAll(AttendType.class);
	}
	
	public boolean addApplication(int attId, int typeId, String reason) {
		Application app = new Application();
		Attend attend = attendDao.get(Attend.class, attId);
		AttendType type = typeDao.get(AttendType.class, typeId);
		app.setAttend(attend);
		app.setType(type);
		if (reason != null) {
			app.setReason(reason);
		}
		appDao.save(app);
		return true;
	}
}
