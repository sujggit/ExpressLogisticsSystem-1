package ioputsl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import po.IoputPO;
import po.TransportPO;
import dataservice.DataFactoryService;
import dataservice.IoputDataService;
import dataservice.PManagementDataService;
import dataservice.TransportDataService;
import enums.Condition;
import enums.DocumentCondition;
import enums.Ioput;
import enums.Position;
import enums.ResultMessage;
import enums.Traffic;
import enums.TransportType;
import stocksl.IoputStock;
import vo.OutMessageVO;

public class OutputStock {
	
	private IoputStock Stock;
	private DataFactoryService Data;
	private IoputDataService IoputData;
	private PManagementDataService PMData;
	private ArrayList<TransportPO> OutputList;

	
	
	public OutputStock(IoputStock Stock,DataFactoryService DataFactory){
		this.Stock = Stock;
		Data = DataFactory;
		try {
			IoputData = Data.getIoputData();
			PMData = Data.getPManagementData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public OutMessageVO showOutputInfo(String id,String userId) {
		// TODO Auto-generated method stub
		
		int row=-1,shelf=-1,seat=-1;
		TransportPO transpo = null;
		OutMessageVO vo;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
		String idate = df.format(new Date());
		String t = tf.format(new Date());
		System.out.print(t);
		for(int i = 0;i<OutputList.size();i++){
			if(OutputList.get(i).getOrder().contains(id))
				transpo = OutputList.get(i);
		}

		
		String des = transpo.getDestination();
		Traffic trans = transpo.getTrafficType();
		String rID = transpo.getID();
		String userName = "NotFound";
		try {
			userName = PMData.find(userId).getName();
			String position = IoputData.find(id).getPosition();
			row = Integer.parseInt(position.split(",")[0]);
			shelf = Integer.parseInt(position.split(",")[1]);
			seat = Integer.parseInt(position.split(",")[2]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IoputPO PO = new IoputPO(id, idate, t, des,trans,rID, Ioput.out,null,DocumentCondition.draft,userName);
		
		
		
		vo = new OutMessageVO(PO.getID(),PO.getOutputDate(),PO.getDestination().toString(),PO.getTransport().toString(),PO.getReceiptID());
		if(Stock.Output(row, shelf, seat)==ResultMessage.SUCCESS)
		try{
		IoputData.insert(PO);
		}catch (Exception e){
			vo = null;
		}
		
		return vo;
	}

	public ResultMessage report(String id,String userId) {
		// TODO Auto-generated method stub
		
		int row=-1,shelf=-1,seat=-1;
		TransportPO transpo = null;
		ResultMessage rm=ResultMessage.SUCCESS;


		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
		String idate = df.format(new Date());
		String t = tf.format(new Date());
		
		for(int i = 0;i<OutputList.size();i++){
			if(OutputList.get(i).getOrder().contains(id))
				transpo = OutputList.get(i);
		}

		String des = transpo.getDestination();
		Traffic trans = transpo.getTrafficType();
		String rID = transpo.getID();
		String userName = "NotFound";
		try {
			userName = PMData.find(userId).getName();
			String position = IoputData.find(id).getPosition();
			row = Integer.parseInt(position.split(",")[0]);
			shelf = Integer.parseInt(position.split(",")[1]);
			seat = Integer.parseInt(position.split(",")[2]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IoputPO PO = new IoputPO(id, idate, t, des,trans,rID, Ioput.out,Condition.lost,DocumentCondition.draft,userName);
		
		
		if(Stock.Output(row, shelf, seat)==ResultMessage.SUCCESS)
		try{
			IoputData.insert(PO);
		}catch(Exception e){
			rm = ResultMessage.FAIL;
		}
		
		return rm;
	}

	public ArrayList<String[]> getOutputList(String userId) {
		// TODO Auto-generated method stub
		int i,j;
		String id;
		IoputPO outPO=null;
		ArrayList<String> orders;
		ArrayList<TransportPO> Outs = OutputList(userId);
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		if(Outs.size()!=0){
			for(i=0;i<Outs.size();i++){
				orders =Outs.get(i).getOrder();
				for(j=0;j<orders.size();j++){
					
					id = orders.get(j);
					try{
						outPO = IoputData.find(id);
						if(outPO.getIoput()==Ioput.out)
							outPO=null;
					}catch(Exception e){
						
					}
					if(outPO!=null){
					
					
					String[] item = new String[2];
					item[0]=id;
					try {
						item[1]=IoputData.find(item[0]).getPosition();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputList.add(item);
				
					}
				
				
				}
					
			}
		}

		return outputList;
	}
	
	private ArrayList<TransportPO> OutputList(String userId){

		int i;
		try {
			OutputList = IoputData.findForIoput(userId.substring(0, 6), DocumentCondition.audited,'0');
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(OutputList!=null){
			for(i=OutputList.size()-1;i>=0;i--){
				if(OutputList.get(i).getSign()!=TransportType.Load)
					OutputList.remove(i);
			}
		}
		
		
		
		return OutputList;
	}

}
