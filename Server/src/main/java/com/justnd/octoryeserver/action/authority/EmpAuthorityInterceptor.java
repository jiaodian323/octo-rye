/**  
* Title: EmpAuthorityInterceptor.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月23日    
*/
package com.justnd.octoryeserver.action.authority;

import com.justnd.octoryeserver.action.WebConstant;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Title: EmpAuthorityInterceptor Description:
 * 
 * @author JiaoDian
 * @date 2018年11月23日
 */
public class EmpAuthorityInterceptor extends AbstractInterceptor {

	/**
	* 
	*/  
	private static final long serialVersionUID = 3887048971963935584L;

	/**
	 * Title: intercept Description:
	 * 
	 * @param arg0
	 * @return
	 * @throws Exception
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = ActionContext.getContext();
		String level = (String) ctx.getSession().get(WebConstant.LEVEL);
		if (level != null
				&& (level.equals(WebConstant.EMP_LEVEL) || level.equals(WebConstant.MGR_LEVEL))) {
			return invocation.invoke();
		}
		
		return Action.LOGIN;
	}

}
