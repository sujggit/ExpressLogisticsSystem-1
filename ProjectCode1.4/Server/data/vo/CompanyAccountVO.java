package vo;

import java.util.ArrayList;



public class CompanyAccountVO {
	int year;
    ArrayList<AgencyVO> agencyList=new ArrayList<AgencyVO>();
    ArrayList<StaffVO> stafflist=new ArrayList<StaffVO>();
    public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	ArrayList<CarVO> carlist=new ArrayList<CarVO>();
//    ArrayList<stock> stocklist=new ArrayList<String>();
    ArrayList<AccountVO> accountlist=new ArrayList<AccountVO>();
    
    
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
