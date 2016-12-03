package po;

import java.io.Serializable;
import java.util.ArrayList;

import vo.CompanyAccountVO;



public class CompanyAccountPO implements Serializable{
    ArrayList<AgencyPO> agencyList=new ArrayList<AgencyPO>();
    ArrayList<StaffPO> stafflist=new ArrayList<StaffPO>();
    ArrayList<CarPO> carlist=new ArrayList<CarPO>();
//  ArrayList<stock> stocklist=new ArrayList<String>();
    ArrayList<AccountPO> accountlist=new ArrayList<AccountPO>();
    
    public CompanyAccountPO(){
    	
    }
	
	public CompanyAccountPO(CompanyAccountVO vo){
		for(int i=0;i<vo.getAgencyList().size();i++){
			agencyList.add(new AgencyPO(vo.getAgencyList().get(i)));
		}
		for(int i=0;i<vo.getStafflist().size();i++){
			stafflist.add(new StaffPO(vo.getStafflist().get(i)));
		}
		for(int i=0;i<vo.getCarlist().size();i++){
			carlist.add(new CarPO(vo.getCarlist().get(i)));
		}
		for(int i=0;i<vo.getAccountlist().size();i++){
			accountlist.add(new AccountPO(vo.getAccountlist().get(i).getName(),vo.getAccountlist().get(i).getBalance()));
		}
		year=vo.getYear();
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public ArrayList<AgencyPO> getAgencyList() {
		return agencyList;
	}
	public void setAgencyList(ArrayList<AgencyPO> agencyList) {
		this.agencyList = agencyList;
	}
	public ArrayList<StaffPO> getStafflist() {
		return stafflist;
	}
	public void setStafflist(ArrayList<StaffPO> stafflist) {
		this.stafflist = stafflist;
	}
	public ArrayList<CarPO> getCarlist() {
		return carlist;
	}
	public void setCarlist(ArrayList<CarPO> carlist) {
		this.carlist = carlist;
	}
	public ArrayList<AccountPO> getAccountlist() {
		return accountlist;
	}
	public void setAccountlist(ArrayList<AccountPO> accountlist) {
		this.accountlist = accountlist;
	}
	int year;

    
}
