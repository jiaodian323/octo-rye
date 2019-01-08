package com.justnd.octoryeserver.service.impl;

/**  
* Title: MgrManagerImpl  
* Description:   
* @author JiaoDian 
* @date 2018年11月17日  
*/ 
//public class MgrManagerImpl
//	implements MgrManager
//{
//	private ApplicationDao appDao;
//	private AttendDao attendDao;
//	private AttendTypeDao typeDao;
//	private CheckBackDao checkDao;
//	private EmployeeDao empDao;
//	private ManagerDao mgrDao;
//	private PaymentDao payDao;
//	private UserDao userDao;
//
//	public void setAppDao(ApplicationDao appDao)
//	{
//		this.appDao = appDao;
//	}
//
//	public void setAttendDao(AttendDao attendDao)
//	{
//		this.attendDao = attendDao;
//	}
//
//	public void setTypeDao(AttendTypeDao typeDao)
//	{
//		this.typeDao = typeDao;
//	}
//
//	public void setCheckDao(CheckBackDao checkDao)
//	{
//		this.checkDao = checkDao;
//	}
//
//	public void setEmpDao(EmployeeDao empDao)
//	{
//		this.empDao = empDao;
//	}
//
//	public void setMgrDao(ManagerDao mgrDao)
//	{
//		this.mgrDao = mgrDao;
//	}
//
//	public void setPayDao(PaymentDao payDao)
//	{
//		this.payDao = payDao;
//	}
//	
//	public void setUserDao(UserDao userDao) {
//		this.userDao = userDao;
//	}
//
//	public void addEmp(Employee emp , String mgr)throws HrException
//	{
//		Manager m = mgrDao.findByName(mgr);
//		if (m == null)
//		{
//			throw new HrException("���Ǿ����𣿻��㻹δ��¼��");
//		}
//		emp.setManager(m);
//		empDao.save(emp);
//	}
//
//	public List<SalaryBean> getSalaryByMgr(String mgr)throws HrException
//	{
//		Manager m = mgrDao.findByName(mgr);
//		if (m == null)
//		{
//			throw new HrException("���Ǿ����𣿻��㻹δ��¼��");
//		}
//		Set<Employee> emps = m.getEmployees();
//		if (emps == null || emps.size() < 1)
//		{
//			throw new HrException("���Ĳ���û��Ա��");
//		}
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.MONTH , -1);
//		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM");
//		String payMonth = sdf.format(c.getTime());
//		List<SalaryBean> result = new ArrayList<SalaryBean>();
//		for (Employee e : emps)
//		{
//			Payment p = payDao.findByMonthAndEmp(payMonth , e);
//			if (p != null)
//			{
//				result.add(new SalaryBean(e.getName()
//					, p.getAmount()));
//			}
//		}
//		return result;
//	}
//
//	public List<EmpBean> getEmpsByMgr(String mgr)
//		throws HrException
//	{
//		Manager m = mgrDao.findByName(mgr);
//		if (m == null)
//		{
//			throw new HrException("���Ǿ����𣿻��㻹δ��¼��");
//		}
//		Set<Employee> emps = m.getEmployees();
//		if (emps == null || emps.size() < 1)
//		{
//			throw new HrException("���Ĳ���û��Ա��");
//		}
//		List<EmpBean> result = new ArrayList<EmpBean>();
//		for (Employee e : emps)
//		{
//			result.add(new EmpBean(e.getName() ,
//				e.getPass() , e.getSalary()));
//		}
//		return result;
//	}
//
//	public List<AppBean> getAppsByMgr(String mgr)throws HrException
//	{
//		Manager m = mgrDao.findByName(mgr);
//		if (m == null)
//		{
//			throw new HrException("���Ǿ����𣿻��㻹δ��¼��");
//		}
//		Set<Employee> emps = m.getEmployees();
//		if (emps == null || emps.size() < 1)
//		{
//			throw new HrException("���Ĳ���û��Ա��");
//		}
//		List<AppBean> result = new ArrayList<AppBean>();
//		for (Employee e : emps)
//		{
//			List<Application> apps = appDao.findByEmp(e);
//			if (apps != null && apps.size() > 0)
//			{
//				for (Application app : apps)
//				{
//					if (app.getResult() == false)
//					{
//						Attend attend = app.getAttend();
//						result.add(new AppBean(app.getId() ,
//							e.getName(), attend.getType().getName(),
//							app.getType().getName(), app.getReason()));
//					}
//				}
//			}
//		}
//		return result;
//	}
//
//	public void check(int appid, String mgrName, boolean result)
//	{
//		Application app = appDao.get(Application.class , appid);
//		CheckBack check = new CheckBack();
//		check.setApp(app);
//		Manager manager = mgrDao.findByName(mgrName);
//		if (manager == null)
//		{
//			throw new HrException("���Ǿ����𣿻��㻹δ��¼��");
//		}
//		check.setManager(manager);
//		if (result)
//		{
//			check.setResult(true);
//
//			app.setResult(true);
//			appDao.update(app);
//			Attend attend = app.getAttend();
//			attend.setType(app.getType());
//			attendDao.update(attend);
//		}
//		else
//		{
//			check.setResult(false);
//			app.setResult(true);
//			appDao.update(app);
//		}
//		checkDao.save(check);
//	}
//}
