/**  
* Title: PunchAction.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月20日    
*/
package com.justnd.octoryeserver.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.justnd.octoryeserver.action.base.EmpBaseAction;
import com.opensymphony.xwork2.ActionContext;

/**  
* Title: PunchAction  
* Description:   
* @author JiaoDian 
* @date 2018年11月20日  
*/
public class PunchAction extends EmpBaseAction {
	/**
	* 
	*/  
	private static final long serialVersionUID = 2622479477572347411L;
	
	private int punchIsValid;
	
	/**
	 * @return the punchIsValid
	 */
	public int getPunchIsValid() {
		return punchIsValid;
	}

	/**
	 * @param punchIsValid the punchIsValid to set
	 */
	public void setPunchIsValid(int punchIsValid) {
		this.punchIsValid = punchIsValid;
	}
	
	public String execute() {
		ActionContext ctx = ActionContext.getContext();
		String user = (String)ctx.getSession().get(WebConstant.USER);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dutyDay = sdf.format(new Date());
		int result = mgr.validPunch(user, dutyDay);
		setPunchIsValid(result);
		return SUCCESS;
	}
}
