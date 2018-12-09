/**  
* Title: EmpBaseAction.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月20日    
*/
package com.justnd.octoryeserver.action.base;

import com.justnd.octoryeserver.service.EmpManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Title: EmpBaseAction Description:
 * 
 * @author JiaoDian
 * @date 2018年11月20日
 */
public class EmpBaseAction extends ActionSupport {
	private static final long serialVersionUID = 7017970959909308740L;
	
	protected EmpManager mgr;
	
	public void setEmpManager(EmpManager mgr) {
		this.mgr = mgr;
	}
}
