/**   
* @Title: SignUpCheckBean.java 
* @Package com.justnd.octoryeserver.beans 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月12日 下午6:52:09  
*/
package com.justnd.octoryeserver.beans;

/**
 * @ClassName: SignUpCheckBean
 * @Description: TODO
 * @author JD
 * @date 2019年3月12日 下午6:52:09
 * 
 */
public class SignUpCheckBean {
	private int result;
	private String msg;

	/**
	 * @return the status
	 */
	public int getResult() {
		return result;
	}

	/**
	 * @param status the status to set
	 */
	public void setResult(int status) {
		this.result = status;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
