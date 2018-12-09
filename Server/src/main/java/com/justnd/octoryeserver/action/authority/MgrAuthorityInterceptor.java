package com.justnd.octoryeserver.action.authority;

import com.justnd.octoryeserver.action.WebConstant;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class MgrAuthorityInterceptor extends AbstractInterceptor
{
	/**
	* 
	*/  
	private static final long serialVersionUID = 7963811663522209538L;

	public String intercept(ActionInvocation invocation)
		throws Exception
	{
		ActionContext ctx = ActionContext.getContext();
		String level = (String)ctx.getSession()
			.get(WebConstant.LEVEL);
		if ( level != null && level.equals(WebConstant.MGR_LEVEL))
		{
			return invocation.invoke();
		}
		return Action.LOGIN;
	}
}