/**  
* Title: AttendDao.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月15日    
*/
package com.justnd.octoryeserver.dao;

import java.util.List;

import com.justnd.octoryeserver.domain.Attend;
import com.justnd.octoryeserver.domain.AttendType;
import com.justnd.octoryeserver.domain.Employee;

/**  
* Title: AttendDao  
* Description:   
* @author JiaoDian 
* @date 2018年11月15日  
*/
public interface AttendDao extends BaseDao<Attend> {

	/**  
	 * Title: findByEmpAndMonth  
	 * Description:   根据员工、月份查询该员工该月份的出勤记录
	 * @param emp 员工
	 * @param month 月份，月份是形如“2018-11-15”格式的字符串
	 * @return  该员工、制定月份的全部出勤记录
	 */ 
	List<Attend> findByEmpAndMonth(Employee emp, String month);
	
	/**  
	 * Title: findByEmpAndDutyDay  
	 * Description:   根据员工、日期查询员工的打卡记录集合
	 * @param emp 员工
	 * @param dutyDay dutyDay 日期
	 * @return  该员工某天的打卡记录集合
	 */ 
	List<Attend> findByEmpAndDutyDay(Employee emp, String dutyDay);
	
	/**  
	 * Title: findByEmpAndDuttyDayAndCom  
	 * Description:   根据员工、日期、上下班查询该员工的打卡记录集合
	 * @param emp 员工
	 * @param dutyDay 日期
	 * @param isCome 是否上班
	 * @return  该员工某天上班或下班的打卡记录
	 */ 
	Attend findByEmpAndDutyDayAndCome(Employee emp, String dutyDay, boolean isCome);
	
	/**  
	 * Title: findByEmpUnAttend  
	 * Description:   查看员工前三天的非正常打卡
	 * @param emp 员工
	 * @param type 该员工前三天的非正常打卡
	 * @return  
	 */ 
	List<Attend> findByEmpUnAttend(Employee emp, AttendType type);
}
