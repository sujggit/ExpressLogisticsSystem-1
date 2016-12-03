package driver;

import slstub.ResultMessage;
import stockslservice.StockInfoService;
import vo.StockInfoVO;

public class StockInfoSLServiceDriver {
	
	public void drive(StockInfoService StockInfoSLService ){
		StockInfoSLService.startShow();
		String[] time = {"2015/10/25,0:0:0","2015/10/26,20:00:00"};
		
		StockInfoVO vo = StockInfoSLService.show(time);
		if(vo!=null)
			System.out.print("Stock info shows");
		StockInfoSLService.endShow();
		StockInfoSLService.startInitialize();
		StockInfoSLService.cancelInitialize();
		if(StockInfoSLService.initialize("0-50,51-70,71-90,91-120,�ţ�0-120���ܣ�0-50��λ��0-50)")
				==ResultMessage.True);
		System.out.print("Initialize succeed!");
		if(StockInfoSLService.check()==ResultMessage.True)
			System.out.print("Check succeed!");
		
	}

}
