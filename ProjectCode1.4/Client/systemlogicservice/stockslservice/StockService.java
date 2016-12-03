package stockslservice;

import enums.ResultMessage;
import enums.WarningMessage;
import stocksl.IoputStock;
import vo.AreaVO;
import vo.StockInfoVO;
import vo.StockInitializeVO;

public interface StockService {

	

	public AreaVO[] getAreas();
	public AreaVO selectArea(String name);
	public WarningMessage range(String adjustrange);
	
	public  StockInfoVO show (String[] time);
	public ResultMessage initialize(StockInitializeVO vo);
	public ResultMessage check(String path);
	public boolean StockExist();
	public String getWarningArea();




}
