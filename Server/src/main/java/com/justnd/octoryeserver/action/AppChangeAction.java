/**  
* Title: AppChangeAction.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月21日    
*/
package com.justnd.octoryeserver.action;

import java.util.List;

import com.justnd.octoryeserver.action.base.EmpBaseAction;
import com.justnd.octoryeserver.domain.AttendType;

/**  
* Title: AppChangeAction  
* Description:   
* @author JiaoDian 
* @date 2018年11月21日  
*/
public class AppChangeAction extends EmpBaseAction {
	/**
	* 
	*/  
	private static final long serialVersionUID = -7042479236446782661L;
	
	private List<AttendType> types;
	
	public void setTypes(List<AttendType> types) {
		this.types = types;
	}
	
	public List<AttendType> getTypes() {
		return this.types;
	}
	
	public String execute()  throws Exception {
		setTypes(mgr.getAllType());
		return SUCCESS;
	}
}
