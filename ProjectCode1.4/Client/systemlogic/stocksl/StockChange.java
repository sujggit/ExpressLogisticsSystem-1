package stocksl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.StockPO;
import dataservice.DataFactoryService;
import dataservice.StockDataService;
import dataserviceimpl.DataFactory;
import enums.ResultMessage;
import enums.WarningMessage;

public class StockChange {
	
	private String WarningArea = null;
	private int seats1,seats2,seats3,seats4;
	private int filled1,filled2,filled3,filled4;
	private int rA,sA,shA,seatSum;
	private double fp1,fp2,fp3,fp4;
	private StockDataService StockData;
	private boolean stockExist;
	private StockPO po;
	public StockChange(DataFactoryService d){
			try {
				StockData = d.getStockDate();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rA = 0;
			sA = 0;
			shA = 0;
			seatSum = 0;
			stockExist = false;
		ChangeInitialize();

	}
	public boolean StockExist(){
			return stockExist;
	}
	
	private void ChangeInitialize(){
		ArrayList<StockPO> Stock = null;
		stockExist = false;
		try {
			Stock = StockData.getAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		filled1 =0;filled2=0;filled3=0;filled4=0;
		seats1=0;seats2=0;seats3=0;seats4=0;
		 if(Stock.size()!=0){
			stockExist = true;
			seatSum = Stock.size();
			rA=0;
			sA=0;
			shA=0;
			for(int i =0;i<Stock.size();i++){
					po = Stock.get(i);
					if(po.getArea().equals("航运区")){
					seats1++;
					if(!po.isEmpty())
						filled1++;}
					if(po.getArea().equals("铁运区")){
						seats2++;
						if(!po.isEmpty())
							filled2++;}
					if(po.getArea().equals("汽运区")){
						seats3++;
						if(!po.isEmpty())
							filled3++;}
					if(po.getArea().equals("机动区")){
						seats4++;
						if(!po.isEmpty())
							filled4++;}
					if(po.getRow()>rA)
						rA = po.getRow();
					if(po.getSeat()>sA)
						sA = po.getSeat();
					if(po.getShelf()>shA)
						shA = po.getShelf();
			}
		}
			
		 if(seats1!=0)
			 fp1=(double)filled1/(double)seats1;
		 if(seats2!=0)
			 fp2=(double)filled2/(double)seats2;
		 if(seats3!=0)
			 fp3=(double)filled3/(double)seats3;
		 if(seats4!=0)
			 fp4=(double)filled4/(double)seats4;

	

	}

	public ResultMessage Input(int row,int shelf,int seat,String id,String date) {
		// TODO Auto-generated method stub
		ResultMessage rm = ResultMessage.SUCCESS;
		try{
			po = StockData.findposition(row, shelf, seat);
			if(po!=null){
			if(po.isEmpty()){
				po.setEmpty(false);
				po.setID(id);
				po.setInputDate(date);
				if(po.getArea().equals("航运区"))
					filled1++;
				if(po.getArea().equals("铁运区"))
					filled2++;
				if(po.getArea().equals("汽运区"))
					filled3++;
				if(po.getArea().equals("机动区"))
					filled4++;
				
				
				try{
				StockData.update(po);
				}catch(Exception e){
					rm = ResultMessage.FAIL;
					
				}
			}
			else
				rm = ResultMessage.FAIL;
			}else rm = ResultMessage.FAIL;
		}catch(Exception e){
			rm = ResultMessage.FAIL;
		}
		
		return rm;
	}


	public ResultMessage Output(int row,int shelf,int seat) {
		// TODO Auto-generated method stub
		ResultMessage rm = ResultMessage.SUCCESS;
		try{
			po = StockData.findposition(row, shelf, seat);
			if(!po.isEmpty()){
			
				
				 	po.setEmpty(true);
					if(po.getArea().equals("航运区"))
						filled1--;
					if(po.getArea().equals("铁运区"))
						filled2--;
					if(po.getArea().equals("汽运区"))
						filled3--;
					if(po.getArea().equals("机动区"))
						filled4--;
					po.setID(null);
					po.setInputDate(null);
				
				 
				try{
				StockData.update(po);
				}catch(Exception e){
					rm = ResultMessage.FAIL;
				}
			
			
			}
			else
				rm = ResultMessage.FAIL;
			
		}catch(Exception e){
			rm = ResultMessage.FAIL;
		}
		
		
		return rm;
	}
	
	public ResultMessage initialize(int s1,int s2,int s3,int s4,int ra,int sa,int sha,int ssum){
		seats1 = s1;
		seats2 = s2;
		seats3 = s3;
		seats4 = s4;
		rA = ra;
		sA = sa;
		shA = sha;
		seatSum = ssum;
		stockExist = true;
		

		filled1=0;filled2=0;filled3=0;filled4=0;

		fp1=0;fp2=0;fp3=0;fp4=0;
		
		
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage setArea(int change1,int change2,int change3,int change4){
		
		ResultMessage rm;
		

			seats1 = seats1 + change1;
			seats2 = seats2 + change2;
			seats3 = seats3 + change3;
			seats4 = seats4 + change4;
			
			
			 if(seats1!=0)
				 fp1=(double)filled1/(double)seats1;
			 if(seats2!=0)
				 fp2=(double)filled2/(double)seats2;
			 if(seats3!=0)
				 fp3=(double)filled3/(double)seats3;
			 if(seats4!=0)
				 fp4=(double)filled4/(double)seats4;
			
			if(fp1<=0.9&&fp2<=0.9&&fp3<=0.9&&fp4<=0.9)
				rm =  ResultMessage.SUCCESS;
			else 
				rm = ResultMessage.FAIL;
		
		return rm;
		
	}
	
	public WarningMessage StockSafe(){
		
		WarningMessage wm = WarningMessage.WARNING;
		WarningArea = null;
		
		 if(seats1!=0)
			 fp1=(double)filled1/(double)seats1;
		 if(seats2!=0)
			 fp2=(double)filled2/(double)seats2;
		 if(seats3!=0)
			 fp3=(double)filled3/(double)seats3;
		 if(seats4!=0)
			 fp4=(double)filled4/(double)seats4;
		
		if(fp1<=0.9&&fp2<=0.9&&fp3<=0.9&&fp4<=0.9)
			wm = WarningMessage.NORMAL;
		else if(fp1>0.9)
			WarningArea = "航运区";
		else if(fp2>0.9)
			WarningArea = "铁运区";
		else if(fp3>0.9)
			WarningArea = "汽运区";
		else if(fp4>0.9)
			WarningArea = "机动区";
			
		return wm;
	}
	
	public String getWarningArea(){
		return WarningArea;
	}
	
	public int getStockSum(){
		int StockSum = filled1+filled2+filled3+filled4;
		return StockSum;
	}
	
	public int getEmptySeats(){
		int Emptys= seats1+seats2+seats3+seats4-filled1-filled2-filled3-filled4;
		return Emptys;
	}

	public int getSeatSum() {
		// TODO Auto-generated method stub
		return seatSum;
	}
	
	public int getRowAll(){
		return rA;
	}
	public int getSeatAll(){
		return sA;
	}
	public int getShelfAll(){
		return shA;
	}

}
