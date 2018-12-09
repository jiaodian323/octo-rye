/**   
* @Title: UserTest.java 
* @Package www.justnd.hrsystem.domain 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2018年12月4日 上午12:29:03  
*/
package com.justnd.octoryeserver.domain;

/**
 * @ClassName: UserTest
 * @Description: TODO
 * @author JD
 * @date 2018年12月4日 上午12:29:03
 * 
 */
public class UserTest {

	public String id; // 用户编号
	public String name; // 用户名称
	public int passwords; // <span style="white-space:pre"> </span> 用户密码

	public UserTest() {
	}

	public UserTest(String id, String name, int passwords) {
		this.id = id;
		this.name = name;
		this.passwords = passwords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPasswords() {
		return passwords;
	}

	public void setPasswords(int passwords) {
		this.passwords = passwords;
	}
}
