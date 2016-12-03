package ioputsl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.IoputPO;
import dataservice.DataFactoryService;
import dataservice.IoputDataService;
import dataserviceimpl.DataFactory;
import enums.Ioput;

public class IoputCal implements IoputCalculation {
	
	DataFactoryService Data;
	IoputDataService ioputData;
	
	int InNum;
	int OutNum;
	
	public IoputCal(){

		try {
			Data = DataFactory.create();
			ioputData = Data.getIoputData();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getIoputs(String[] time){
		
		ArrayList<IoputPO> All = null;

		try {
			All =ioputData.findTime(time);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				System.out.println(All.size());
		
		InNum = 0;
		OutNum = 0;
		if(All.size()!=0){
			for(int i =0;i<All.size();i++){
				if(All.get(i).getIoput() == Ioput.in)
					InNum++;
				if(All.get(i).getIoput() == Ioput.out )
					OutNum++;
			}
		}
		
	}

	@Override
	public int getInputs(String[] time) {
		// TODO Auto-generated method stub
		this.getIoputs(time);
		
		return InNum;
	}

	@Override
	public int getOutputs(String[] time) {
		// TODO Auto-generated method stub
		this.getIoputs(time);
		
		return OutNum;
	}


}
