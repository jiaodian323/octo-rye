/**  
* Title: MgrBaseAction.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月20日    
*/
package com.justnd.octoryeserver.action.base;

import com.justnd.octoryeserver.service.MgrManager;
import com.opensymphony.xwork2.ActionSupport;

/**  
* Title: MgrBaseAction  
* Description:   
* @author JiaoDian 
* @date 2018年11月20日  
*/
public class MgrBaseAction extends ActionSupport
{
	private static final long serialVersionUID = 4328266291161814631L;
	
	protected MgrManager mgr;

	public void setMgrManager(MgrManager mgr)
	{
		this.mgr = mgr;
	}
}