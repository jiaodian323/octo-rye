/**  
* Title: LoginAction.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月20日    
*/
package com.justnd.octoryeserver.action;

import com.justnd.octoryeserver.action.base.EmpBaseAction;
import com.justnd.octoryeserver.domain.Manager;
import com.justnd.octoryeserver.service.EmpManager;
import com.opensymphony.xwork2.ActionContext;

/**  
* Title: LoginAction  
* Description:   
* @author JiaoDian 
* @date 2018年11月20日  
*/
public class LoginAction extends EmpBaseAction {
	private static final long serialVersionUID = -4330776020737151291L;
	
	private final String EMP_RESULT = "emp";
	private final String MGR_RESULT = "mgr";
	
	private Manager manager;
	private String vercode;
	
	/**
	 * @return the manager
	 */
	public Manager getManager() {
		return manager;
	}
	
	/**
	 * @param manager the manager to set
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	/**
	 * @return the vercod
	 */
	public String getVercod() {
		return vercode;
	}
	
	/**
	 * @param vercod the vercod to set
	 */
	public void setVercod(String vercod) {
		this.vercode = vercod;
	}
	
	public String execute() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		String ver2 = (String)ctx.getSession().get("rand");
		if (vercode.equalsIgnoreCase(ver2)) {
			int result = mgr.validLogin(getManager());
			if (result == EmpManager.LOGIN_EMP) {
				ctx.getSession().put(WebConstant.USER, manager.getName());
				ctx.getSession().put(WebConstant.LEVEL, WebConstant.EMP_LEVEL);
				addActionMessage("您已经成功登陆系统");
				return EMP_RESULT;
			} else if (result == EmpManager.LOGIN_MGR) {
				ctx.getSession().put(WebConstant.LEVEL, WebConstant.MGR_LEVEL);
				addActionMessage("您已经成功登陆系统");
				return MGR_RESULT;
			} else {
				addActionMessage("用户名/密码不匹配");
				return ERROR;
			}
		}
		addActionMessage("验证码不匹配，请重新输入");
		return ERROR;
	}
}
