package vo;

import java.util.ArrayList;

import po.AccountPO;
import po.AgencyPO;
import po.CarPO;
import po.CompanyAccountPO;
import po.StaffPO;



public class CompanyAccountVO {
	int year;
    ArrayList<AgencyVO> agencyList=new ArrayList<AgencyVO>();
    ArrayList<StaffVO> stafflist=new ArrayList<StaffVO>();	
    ArrayList<CarVO> carlist=new ArrayList<CarVO>();
//    ArrayList<stock> stocklist=new ArrayList<String>();
    ArrayList<AccountVO> accountlist=new ArrayList<AccountVO>();
    
    public CompanyAccountVO(){
    	
    }
    
	public CompanyAccountVO(CompanyAccountPO po){
		for(int i=0;i<po.getAgencyList().size();i++){
			agencyList.add(new AgencyVO(po.getAgencyList().get(i)));
		}
		for(int i=0;i<po.getStafflist().size();i++){
			stafflist.add(new StaffVO(po.getStafflist().get(i)));
		}
		for(int i=0;i<po.getCarlist().size();i++){
			carlist.add(new CarVO(po.getCarlist().get(i)));
		}
		for(int i=0;i<po.getAccountlist().size();i++){
			accountlist.add(new AccountVO(po.getAccountlist().get(i).getName(),po.getAccountlist().get(i).getBalance()));
		}
		year=po.getYear();
	}
    public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

    
    
	public ArrayList<AgencyVO> getAgencyList() {
		return agencyList;
	}
	public void setAgencyList(ArrayList<AgencyVO> agencyList) {
		this.agencyList = agencyList;
	}
	public ArrayList<StaffVO> getStafflist() {
		return stafflist;
	}
	public void setStafflist(ArrayList<StaffVO> stafflist) {
		this.stafflist = stafflist;
	}
	public ArrayList<CarVO> getCarlist() {
		return carlist;
	}
	public void setCarlist(ArrayList<CarVO> carlist) {
		this.carlist = carlist;
	}
	public ArrayList<AccountVO> getAccountlist() {
		return accountlist;
	}
	public void setAccountlist(ArrayList<AccountVO> accountlist) {
		this.accountlist = accountlist;
	}
}
