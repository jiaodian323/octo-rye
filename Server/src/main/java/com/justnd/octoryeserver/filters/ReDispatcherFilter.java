/**   
* @Title: ReDispatcherFilter.java 
* @Package www.justnd.hrsystem.filter 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月4日 下午4:00:50  
*/
package com.justnd.octoryeserver.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/** 
* @ClassName: ReDispatcherFilter 当Struts和Servlet需要共存的时候，使用该过滤器重新定义，
* 使Struts核心过滤器不会拦截需要Servlet处理的请求
* @Description: TODO
* @author JD
* @date 2018年12月4日 下午4:00:50 
*  
*/
public class ReDispatcherFilter implements Filter {

	private ArrayList<String> includes = new ArrayList<String>();
	/**  
	 * Title: destroy  
	 * Description:     
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**  
	 * Title: doFilter  
	 * Description:   
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws IOException
	 * @throws ServletException  
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("拦截名单包含：");
		System.out.println(includes.toString());
		
		HttpServletRequest request = (HttpServletRequest)req;
		   String target = request.getRequestURI();
		   target = target.lastIndexOf("?")>0   
		      ?target.substring(target.lastIndexOf("/")+1,target.lastIndexOf("?")-target.lastIndexOf("/"))
		      :target.substring(target.lastIndexOf("/")+1);
		  
		   System.out.println("servlet拦截器拦截到：" + target);
		   if(this.includes.contains(target))
		   {
		    RequestDispatcher rdsp = request.getRequestDispatcher(target);
		   
		    System.out.println("Filter拦截，转发请求至：" + target);
		    rdsp.forward(req, resp);
		   }
		   else {
			   System.out.println("servlet拦截器未做拦截，交由struts核心拦截器处理");
			   chain.doFilter(req, resp);
		   }
	}

	/**  
	 * Title: init  
	 * Description:   
	 * @param arg0
	 * @throws ServletException  
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.includes.addAll( Arrays.asList(config.getInitParameter("includeServlets").split(",")));
	}
}
