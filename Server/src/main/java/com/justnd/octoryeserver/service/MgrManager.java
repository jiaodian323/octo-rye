/**  
* Title: MgrManager.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月17日    
*/
package com.justnd.octoryeserver.service;

import java.util.List;

import com.justnd.octoryeserver.domain.Employee;
import com.justnd.octoryeserver.exception.HrException;
import com.justnd.octoryeserver.vo.AppBean;
import com.justnd.octoryeserver.vo.EmpBean;
import com.justnd.octoryeserver.vo.SalaryBean;

/**  
* Title: MgrManager  
* Description:   
* @author JiaoDian 
* @date 2018年11月17日  
*/
public interface MgrManager {
	void addEmp(Employee emp , String mgr)
		throws HrException;

	List<SalaryBean> getSalaryByMgr(String mgr);

	List<EmpBean> getEmpsByMgr(String mgr);

	List<AppBean> getAppsByMgr(String mgr);

	void check(int appid, String mgrName, boolean result);
}
