/**  
* Title: ProcessPunchAction.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月21日    
*/
package com.justnd.octoryeserver.action;

import com.justnd.octoryeserver.service.EmpManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**  
* Title: ProcessPunchAction  
* Description:   
* @author JiaoDian 
* @date 2018年11月21日  
*/
public class ProcessPunchAction extends ActionSupport {
	/**
	* 
	*/  
	private static final long serialVersionUID = -2991676924089826655L;
	
	private EmpManager empMgr;
	
	public void setEmpManager(EmpManager empMgr) {
		this.empMgr = empMgr;
	}
	
	public String come() throws Exception {
		return process(true);
	}
	
	public String leave() throws Exception{
		return process(false);
	}
	
	private String process(boolean isCome) {
		ActionContext ctx = ActionContext.getContext();
		String user = (String)ctx.getSession().get(WebConstant.USER);
		System.out.println("------打卡------");
		String dutyDay = new java.sql.Date(System.currentTimeMillis()).toString();
		
		int result = empMgr.punch(user, dutyDay, isCome);
		switch (result) {
		case EmpManager.PUNCH_FAIL:
			addActionMessage("打卡失败");
			break;
		case EmpManager.PUNCHED:
			addActionMessage("您已经打过卡了，不要重复打卡");
			break;
		case EmpManager.PUNCH_SUCC:
			addActionMessage("打卡成功");
			break;
		}
		
		return SUCCESS;
	}
}
