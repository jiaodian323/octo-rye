/**   
* @Title: BaseBean.java 
* @Package com.justnd.octoryeserver.beans.base 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月22日 下午4:13:23  
*/
package com.justnd.octoryeserver.beans.base;

/** 
* @ClassName: BaseBean 
* @Description: TODO
* @author JD
* @date 2019年3月22日 下午4:13:23 
*  
*/
public class BaseBean<T> {
	private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
