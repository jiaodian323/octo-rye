/**  
* Title: EmpManager.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月16日    
*/
package com.justnd.octoryeserver.service;

import java.util.List;

import com.justnd.octoryeserver.domain.AttendType;
import com.justnd.octoryeserver.domain.Manager;
import com.justnd.octoryeserver.vo.AttendBean;
import com.justnd.octoryeserver.vo.PaymentBean;

/**
 * Title: EmpManager Description:
 * 
 * @author JiaoDian
 * @date 2018年11月16日
 */
public interface EmpManager {
	public static final int LOGIN_FAIL = 0;
	public static final int LOGIN_EMP = 1;
	public static final int LOGIN_MGR = 2;
	public static final int NO_PUNCH = 0;
	public static final int COME_PUNCH = 1;
	public static final int LEAVE_PUNCH = 2;
	public static final int BOTH_PUNCH = 3;
	public static final int PUNCH_FAIL = 0;
	public static final int PUNCHED = 1;
	public static final int PUNCH_SUCC = 2;
	public static final int AM_LIMIT = 11;
	public static final int COME_LIMIT = 9;
	public static final int LATE_LIMIT = 11;
	public static final int LEAVE_LIMIT = 18;
	public static final int EARLY_LIMIT = 16;

	int validLogin(Manager mgr);

	void autoPunch();

	void autoPay();

	int validPunch(String user, String dutyDay);

	public int punch(String user, String dutyDay, boolean isCome);

	List<PaymentBean> empSalary(String empName);

	List<AttendBean> unAttend(String empName);

	List<AttendType> getAllType();

	boolean addApplication(int attId, int typeId, String reason);
}
