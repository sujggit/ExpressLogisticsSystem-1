package ioputsl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dataservice.DataFactoryService;
import dataservice.IoputDataService;
import dataservice.PManagementDataService;
import dataservice.TransportDataService;
import po.IoputPO;
import po.TransportPO;
import enums.DocumentCondition;
import enums.Ioput;
import enums.Position;
import enums.ResultMessage;
import enums.TransportType;
import enums.WarningMessage;
import vo.InMessageVO;
import stocksl.IoputStock;

public class InputStock {
	private IoputStock Stock;
	private DataFactoryService Data;
	private IoputDataService IoputData;
	private PManagementDataService PMData;
	private ArrayList<TransportPO> InputList;
	private int row,shelf,seat;
	
	public InputStock(IoputStock stock,DataFactoryService d){
		Stock = stock;
		Data = d;
		try {
			IoputData = Data.getIoputData();
			PMData = Data.getPManagementData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public InMessageVO showInputInfo(String id,String userId) {
		// TODO Auto-generated method stub
		
		InMessageVO vo;
		// get信息
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String idate = df.format(new Date());


		String des=null;
		TransportPO Transpo = null;

		for(int i =0;i<InputList.size();i++){
			Transpo = InputList.get(i);
			if(Transpo.getSign()==TransportType.Reception&&Transpo.getOrder().contains(id)){

				break;
			}
		}
		
		des = Transpo.getDestination();
		
		//打包VO
		vo = new InMessageVO(id,idate,des);
		
		return vo;
	}

	public WarningMessage position(String position, InMessageVO vo,String userId) {
		// TODO Auto-generated method stub
		//解析坐标
		String[] p = position.split(",");
		row = Integer.parseInt(p[0]);
		shelf = Integer.parseInt(p[1]);
		seat = Integer.parseInt(p[2]);
		String userName = "NotFound";
		try {
			userName = PMData.find(userId).getName();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//更改库存
		WarningMessage wm = WarningMessage.FAIL;
		ResultMessage rm;
		rm = Stock.Input(row, shelf, seat,vo.getID(),vo.getInputdate());
		if(rm == ResultMessage.SUCCESS){

			wm = Stock.StockSafe();
			
			SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
			String t = tf.format(new Date());

			//储存入库单(AUDITx
			IoputPO po = new IoputPO(vo.getID(), vo.getInputdate(),t,vo.getDestination(), position, Ioput.in, DocumentCondition.handing,userName);
			
			try{
			IoputData.insert(po);
			}catch(Exception e){
				
			}

		}
			return wm;
	}


	public ArrayList<String[]> getInputList(String userId) {
		// TODO Auto-generated method stub
		ArrayList<String[]> inputlist = new ArrayList<String[]>();
		String id="";
		String traffictype = "";
		IoputPO inpo=null;

		int ordernum;
		try {
			InputList = IoputData.findForIoput(userId.substring(0, 6), DocumentCondition.audited,'1');

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(InputList.size()!=0){
			for(int i=0;i<InputList.size();i++){
				
				ordernum = InputList.get(i).getOrder().size();
				for(int j=0;j<ordernum;j++){
					id = InputList.get(i).getOrder().get(j);
					traffictype = InputList.get(i).getTrafficType().toString();
					try{
						inpo = IoputData.find(id);
					}catch(Exception e){
						
					}
					
					if(inpo==null){
					
					String item[]=new String[2];
					item[0]=id;
					item[1] = traffictype;
					
					inputlist.add(item);

				}
				}


			}
		}


		return inputlist;
	}

}
