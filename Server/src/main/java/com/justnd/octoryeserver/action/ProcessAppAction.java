/**  
* Title: ProcessAppAction.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月23日    
*/
package com.justnd.octoryeserver.action;

import com.justnd.octoryeserver.action.base.EmpBaseAction;

/**  
* Title: ProcessAppAction  
* Description:   
* @author JiaoDian 
* @date 2018年11月23日  
*/
public class ProcessAppAction extends EmpBaseAction {

	/**
	* 
	*/  
	private static final long serialVersionUID = 1921477258908033011L;
	
	private int attId;
	private int typeId;
	private String reason;
	/**
	 * @return the attId
	 */
	public int getAttId() {
		return attId;
	}
	/**
	 * @param attId the attId to set
	 */
	public void setAttId(int attId) {
		this.attId = attId;
	}
	/**
	 * @return the typeId
	 */
	public int getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String execute() throws Exception{
		boolean result = mgr.addApplication(attId, typeId, reason);
		if (result) {
			addActionMessage("您已经申请成功，等待经理的审阅");
		} else {
			addActionMessage("申请失败，请注意不要重复申请");
		}
		return SUCCESS;
	}

}
