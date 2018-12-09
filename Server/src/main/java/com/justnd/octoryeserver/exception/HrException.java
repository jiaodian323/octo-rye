/**  
* Title: HrException.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月17日    
*/
package com.justnd.octoryeserver.exception;

/**  
* Title: HrException  
* Description:   
* @author JiaoDian 
* @date 2018年11月17日  
*/
public class HrException extends RuntimeException {
	private static final long serialVersionUID = 48L;

	public HrException() {}
	
	public HrException(String msg) {
		super(msg);
	}
}
