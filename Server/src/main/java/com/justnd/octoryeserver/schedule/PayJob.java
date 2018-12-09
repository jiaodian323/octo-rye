/**  
* Title: PayJob.java  
* Description:   
* Copyright: Copyright (c) 2018  
* Company: www.justiniano.com  
* @author JiaoDian  
* @date 2018年11月17日    
*/
package com.justnd.octoryeserver.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.justnd.octoryeserver.service.EmpManager;

/**  
* Title: PayJob  
* Description:   
* @author JiaoDian 
* @date 2018年11月17日  
*/
public class PayJob extends QuartzJobBean {

	private boolean isRunning = false;
	private EmpManager empMgr;
	
	public void setEmpMgr(EmpManager empMgr) {
		this.empMgr = empMgr;
	}
	/**  
	 * Title: executeInternal  
	 * Description:   
	 * @param arg0
	 * @throws JobExecutionException  
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		if (!isRunning) {
			System.out.println("开始调度自动结算工资");
			isRunning = true;
			empMgr.autoPay();
			isRunning = false;
		}
	}
}
