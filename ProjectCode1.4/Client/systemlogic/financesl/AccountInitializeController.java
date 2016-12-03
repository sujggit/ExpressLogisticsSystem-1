package financesl;

import java.util.ArrayList;

import vo.AccountVO;
import vo.AgencyVO;
import vo.CarVO;
import vo.CompanyAccountVO;
import vo.StaffVO;
import enums.ResultMessage;
import financeslservice.AccountInitializeService;

public class AccountInitializeController implements AccountInitializeService{
	AccountInitialize accountInitialize;
	
	
	 public AccountInitializeController() {
		// TODO Auto-generated constructor stub
		 this.accountInitialize=AccountInitialize.createAccountInitialize();
	}

	@Override
	public void initialize(CompanyAccountVO account) {
		// TODO Auto-generated method stub
		accountInitialize.initialize(account);
		
	}

	@Override
	public ResultMessage setAgency(ArrayList<AgencyVO> volist) {
		// TODO Auto-generated method stub
		return accountInitialize.setAgency(volist);
	}

	@Override
	public ResultMessage setStaff(ArrayList<StaffVO> volist) {
		// TODO Auto-generated method stub
		return accountInitialize.setStaff(volist);
	}

	@Override
	public ResultMessage setCar(ArrayList<CarVO> volist) {
		// TODO Auto-generated method stub
		return accountInitialize.setCar(volist);
	}

	@Override
	public ResultMessage setAccount(ArrayList<AccountVO> volist) {
		// TODO Auto-generated method stub
		return accountInitialize.setAccount(volist);
	}

	@Override
	public CompanyAccountVO find(int year) {
		// TODO Auto-generated method stub
		return accountInitialize.find(year);
	}

}
