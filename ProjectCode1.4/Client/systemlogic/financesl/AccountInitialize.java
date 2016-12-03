package financesl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import pamanagementsl.AManagement;
import pamanagementsl.AManagementController;
import pamanagementsl.CManagement;
import pamanagementsl.CManagementController;
import pamanagementsl.PManagement;
import pamanagementsl.PManagementController;
import pamanagementslservice.AManagementService;
import pamanagementslservice.CManagementService;
import pamanagementslservice.PManagementService;
import po.AccountPO;
import po.AgencyPO;
import po.CarPO;
import po.CompanyAccountPO;
import po.StaffPO;
import dataservice.AManagementDataService;
import dataservice.CManagementDataService;
import dataservice.FinanceDataService;
import dataservice.PManagementDataService;
import dataserviceimpl.DataFactory;
import enums.ResultMessage;
import enums.Work;
import usersl.UserManagementController;
import userslservice.UserService;
import vo.AccountVO;
import vo.AgencyVO;
import vo.CarVO;
import vo.CompanyAccountVO;
import vo.StaffVO;
import financeslservice.AccountInitializeService;
import financeslservice.AccountManagementService;

public class AccountInitialize implements AccountInitializeService{
	
	static AccountInitialize accountinitialize;
	DataFactory datafactory;
	
	private AccountInitialize(){
		try {
			this.datafactory=DataFactory.create();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	
	public static AccountInitialize createAccountInitialize(){
		if(accountinitialize==null){
			accountinitialize=new AccountInitialize();
		}
		
		return accountinitialize;
	}




	@Override
	public void initialize(CompanyAccountVO account) {
		// TODO Auto-generated method stub
		
//		AManagementDataService adService=datafactory.getAManagementData();
//		PManagementDataService pdDataService=datafactory.getPManagementData();
//		CManagementDataService cDataService=datafactory.getCManagementData();
//		FinanceDataService fds=datafactory.getFinanceData();
		
//		for(int i=0;i<account.getAgencyList().size();i++){
//			try {
//				adService.insert(new AgencyPO(account.getAgencyList().get(i)));
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		for(int i=0;i<account.getStafflist().size();i++){
//			try {
//				pdDataService.insert(new StaffPO(account.getStafflist().get(i)));
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		for(int i=0;i<account.getCarlist().size();i++){
//			try {
//				cDataService.insert(new CarPO(account.getCarlist().get(i)));
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		for(int i=0;i<account.getAccountlist().size();i++){
//			try {
//				fds.insertAccountPO(new AccountPO(account.getAccountlist().get(i).getName(), account.getAccountlist().get(i).getBalance()));
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		FinanceDataService fds=datafactory.getFinanceData();
		CompanyAccountPO po=new CompanyAccountPO(account);
		
		try {
			fds.insertCompanyAccountPO(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setAgency(account.getAgencyList());
		setStaff(account.getStafflist());
		setCar(account.getCarlist());
		setAccount(account.getAccountlist());



		UserService userService=new UserManagementController();
		userService.add("管理员", "Admin", "Admin", "高", Work.Admin);
	}




	@Override
	public ResultMessage setAgency(ArrayList<AgencyVO> volist) {
		// TODO Auto-generated method stub
		
		ResultMessage result=ResultMessage.FAIL;
		
		AManagement am=AManagement.creatAManagement(datafactory);
		AgencyVO tempvo=null;
		for(int i=0;i<volist.size();i++){
			tempvo=volist.get(i);
			result=am.add(tempvo.getName(), tempvo.getIdNumber(), tempvo.getStaff(), tempvo.getPhoneNumber(), tempvo.getAddress(), tempvo.getLeader());
		    if(result==ResultMessage.FAIL){
		    	break;
		    }
		}
		System.out.println("hh");
		return result;
	}




	@Override
	public ResultMessage setStaff(ArrayList<StaffVO> volist) {
		// TODO Auto-generated method stub
		
		ResultMessage result=ResultMessage.FAIL;
		PManagement pm=PManagement.createPManagement(datafactory);
		StaffVO tempvo=null;
		for(int i=0;i<volist.size();i++){
			tempvo=volist.get(i);
			result=pm.add(tempvo.getName(), tempvo.getWork(), tempvo.getWorkNumber(), tempvo.getWorkPlaceNumber(), tempvo.getBirthDate(), tempvo.getIdNumber(), tempvo.getPhoneNumber(), tempvo.getAddress(), tempvo.getSex(), tempvo.getPage());
			if(result==ResultMessage.FAIL){
				break;
			}
		}
		
		return result;
	}




	@Override
	public ResultMessage setCar(ArrayList<CarVO> volist) {
		// TODO Auto-generated method stub
		ResultMessage result=ResultMessage.FAIL;
		
		CManagement cm=CManagement.createCManagement(datafactory);
		CarVO tempvo=null;
		
		for(int i=0;i<volist.size();i++){
			tempvo=volist.get(i);
			result=cm.add(tempvo.getIDNumber(), tempvo.getWorkPlaceNumber(), tempvo.getLicenseNumber(), tempvo.getWorkYear());
			if(result==ResultMessage.FAIL){
				break;
			}
		}
		
		
		
		return result;
	}




	@Override
	public ResultMessage setAccount(ArrayList<AccountVO> volist) {
		// TODO Auto-generated method stub
		ResultMessage result=ResultMessage.FAIL;
		
		AccountVO tempvo=null;
		AccountManagement am=AccountManagement.creatAccountManagement(datafactory);
		for(int i=0;i<volist.size();i++){
			tempvo=volist.get(i);
			result=am.addAccount(tempvo.getName(), tempvo.getBalance());
			if(result==ResultMessage.FAIL){
				break;
			}
		}
				
		
		
		return result;
	}




	@Override
	public CompanyAccountVO find(int year) {
		// TODO Auto-generated method stub
		FinanceDataService fds=datafactory.getFinanceData();
		CompanyAccountPO po=null;
		try {
			 po=fds.findCompanyAccountPO(year);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new CompanyAccountVO(po);
	}

}
