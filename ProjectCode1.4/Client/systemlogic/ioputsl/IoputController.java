package ioputsl;

import java.util.ArrayList;

import dataservice.DataFactoryService;
import enums.ResultMessage;
import enums.WarningMessage;
import stocksl.IoputStock;
import vo.InMessageVO;
import vo.OutMessageVO;
import ioputslservice.IoputService;

public class IoputController implements IoputService {
	
	private InputStock In;
	private OutputStock Out;
	
	public IoputController(DataFactoryService d,IoputStock Stock){
		In = new InputStock(Stock,d);
		Out = new OutputStock(Stock,d);
	}

	@Override
	public InMessageVO showInputInfo(String id,String userId) {
		// TODO Auto-generated method stub
		return In.showInputInfo(id,userId);
	}

	@Override
	public ResultMessage report(String id,String userId) {
		// TODO Auto-generated method stub
		return Out.report(id,userId);
	}

	@Override
	public WarningMessage position(String position, InMessageVO vo,
			String userId) {
		// TODO Auto-generated method stub
		return In.position(position, vo, userId);
	}

	@Override
	public OutMessageVO showOutputInfo(String id, String userId) {
		// TODO Auto-generated method stub
		return Out.showOutputInfo(id,userId);
	}

	@Override
	public ArrayList<String[]> getOutputList(String userId) {
		// TODO Auto-generated method stub
		return Out.getOutputList(userId);

}

	@Override
	public ArrayList<String[]> getInputList(String userId) {
		// TODO Auto-generated method stub

		return In.getInputList(userId);

		
	}
}