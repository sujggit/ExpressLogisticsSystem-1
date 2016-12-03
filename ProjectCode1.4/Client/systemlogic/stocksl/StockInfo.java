package stocksl;

import ioputsl.IoputCalculation;

import java.io.File;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import po.StockPO;
import dataservice.DataFactoryService;
import dataservice.IoputDataService;
import dataservice.StockDataService;
import enums.ResultMessage;
import vo.StockInfoVO;
import vo.StockInitializeVO;

public class StockInfo {
	
	private StockDataService stock;
	private IoputDataService ioput;
	private StockChange Change;
	private IoputCalculation io;
	
	
	public StockInfo(DataFactoryService d,StockChange Change,IoputCalculation io){
		try {
			stock = d.getStockDate();
			ioput = d.getIoputData();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Change = Change;
		this.io = io;
	}


	public StockInfoVO show(String[] time) {
		// TODO Auto-generated method stub

		int seatEmptySum = Change.getEmptySeats();
		int stockSum = Change.getStockSum();
		int seatSum = Change.getSeatSum();
		int ins = io.getInputs(time);
		int outs = io.getOutputs(time);
		
		
		
		StockInfoVO vo = new StockInfoVO(ins,outs,seatSum,seatEmptySum,stockSum);
		
		return vo;
	}


	public ResultMessage initialize(StockInitializeVO vo) {
		// TODO Auto-generated method stub

		ResultMessage rm = ResultMessage.SUCCESS;
		if(Change.StockExist()){
		try {
			stock.clean();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}}
		
		StockPO po;
		String AreaName = null;
		int Num;
		int row =1;
		int shelf=1;
		int seat=1;
		Num = vo.getRowall()*vo.getSeat()*vo.getShelf();
		

		
		for(int i=1;i<=Num;){
			if(row<=vo.getArea1()[1]&&row>=vo.getArea1()[0]){
				AreaName = "航运区";
			}
			if(row<=vo.getArea2()[1]&&row>=vo.getArea2()[0]){
				AreaName = "铁运区";
			}
			if(row<=vo.getArea3()[1]&&row>=vo.getArea3()[0]){
				AreaName = "汽运区";
			}
			if(row<=vo.getArea4()[1]&&row>=vo.getArea4()[0]){
				AreaName = "机动区";
			}
			
			for(shelf=1;shelf<=vo.getShelf();shelf++)
				for(seat=1;seat<=vo.getSeat();seat++){
					po = new StockPO(i,AreaName,row,shelf,seat,true,null,null);
					i++;
					try{
					stock.insert(po);

					}catch(Exception e){
						rm = ResultMessage.FAIL;
					}
				}

			
			row++;
				
		}
		
		int s1 = (vo.getArea1()[1]-vo.getArea1()[0]+1)*shelf*seat;
		int s2 = (vo.getArea2()[1]-vo.getArea2()[0]+1)*shelf*seat;
		int s3 = (vo.getArea3()[1]-vo.getArea3()[0]+1)*shelf*seat;
		int s4 = (vo.getArea4()[1]-vo.getArea4()[0]+1)*shelf*seat;
		
		
		Change.initialize(s1, s2, s3, s4,vo.getRowall(),vo.getSeat(),vo.getShelf(),Num);
		
		return rm;
	}

	public ResultMessage check(String path) {
		// TODO Auto-generated method stub
		ResultMessage rm = ResultMessage.SUCCESS;
		String[] title = {"区名","排","架","位","快递编号","入库时间","目的地"};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String Date = df.format(new Date());
		ArrayList<StockPO> CheckList = null;
		try {
			CheckList = stock.getAll();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			rm = ResultMessage.FAIL;
		}
		int i,j;
		if(!path.equals(""))
			path = path + '/';

		 String excelName = "库存盘点"+ Date + ".xls";  
		  try {  
		   File excelFile = new File(path+excelName); 
		   WritableWorkbook book = Workbook.createWorkbook(excelFile);  
		   WritableSheet sheet = book.createSheet(" 第一页 ", 0);  
		   // 文字样式  
		   jxl.write.WritableFont wfc = new jxl.write.WritableFont(  
		     WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,  
		     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);  
		  
		   jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(  
		     wfc);  
		  
		   // 设置单元格样式  
		   wcfFC.setBackground(jxl.format.Colour.WHITE);// 单元格颜色  
		  
		   // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)  
		   // 以及单元格内容为  
		   for(i=0;i<title.length;i++){
			   Label label = new Label(i, 0, title[i], wcfFC);  
			   sheet.addCell(label);  
			   if(CheckList!=null){
			   for(j=0;j<CheckList.size();j++){
				   switch(i){
				   
				   case 0:{  
					   Label labelcell = new Label(i,j+1,CheckList.get(j).getArea(), wcfFC);
					   sheet.addCell(labelcell);
					   break;
				   }
				   
				   case 1:{  
					   Label labelcell = new Label(i,j+1,Integer.toString(CheckList.get(j).getRow()), wcfFC);
					   sheet.addCell(labelcell);
					   break;
				   }
				   
				   case 2:{  
					   Label labelcell = new Label(i,j+1,Integer.toString(CheckList.get(j).getShelf()), wcfFC);
					   sheet.addCell(labelcell);
					   break;
				   }
				   
				   case 3:{  
					   Label labelcell = new Label(i,j+1,Integer.toString(CheckList.get(j).getSeat()), wcfFC);
					   sheet.addCell(labelcell);
					   break;
				   }
				   
				   case 4:{  
					   Label labelcell;
					   if(!CheckList.get(j).isEmpty())
						 labelcell = new Label(i,j+1,CheckList.get(j).getID(), wcfFC);
					   else
						   labelcell = new Label(i,j+1,"无", wcfFC);
					   sheet.addCell(labelcell);
					   break;
				   }
				   
				   case 5:{  
					   String id = CheckList.get(j).getID();
					   Label labelcell;
					   if(!CheckList.get(j).isEmpty())
						 labelcell = new Label(i,j+1,ioput.find(id).getTime(), wcfFC);
					   else
						   labelcell = new Label(i,j+1,"无", wcfFC);
					   sheet.addCell(labelcell);
					   break;
				   }
				   
				   case 6:{  
					   String id = CheckList.get(j).getID();
					   Label labelcell;
					   if(!CheckList.get(j).isEmpty())
						   labelcell = new Label(i,j+1,ioput.find(id).getDestination(), wcfFC);
					   else
						   labelcell = new Label(i,j+1,"无", wcfFC);
					   sheet.addCell(labelcell);
					   break;
				   }
			   }
			   }
		   }
		   }
		   // 写入数据并关闭文件  
		   book.write();  
		   book.close();  
		  } catch (Exception e) {  
		   System.out.println(e); 
		   rm = ResultMessage.FAIL;
		  }  
			
		
		return rm;
	}

}
