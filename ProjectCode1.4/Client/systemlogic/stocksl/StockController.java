package stocksl;
import ioputsl.IoputCalculation;
import dataservice.DataFactoryService;
import enums.ResultMessage;
import enums.WarningMessage;
import stockslservice.StockService;
import vo.AreaVO;
import vo.StockInfoVO;
import vo.StockInitializeVO;

public class StockController implements StockService ,IoputStock {
	
	private StockAdjust Adjust = null;
	private DataFactoryService Data;
	private StockInfo Info;
	private StockChange Change;
	
	public StockController(DataFactoryService d,IoputCalculation io){

		Change = new StockChange(d);
		Info = new StockInfo(d,Change,io);
		Data = d;
		Adjust = new StockAdjust(Data,Change);
		
	}
	@Override
	public String getWarningArea(){
		return Change.getWarningArea();
	}

	@Override
	public AreaVO[] getAreas() {
		// TODO Auto-generated method stub
		return Adjust.getAreas();
	}

	@Override
	public AreaVO selectArea(String name) {
		// TODO Auto-generated method stub
		return Adjust.selectArea(name);
	}

	@Override
	public WarningMessage range(String adjustrange) {
		// TODO Auto-generated method stub
		return Adjust.range(adjustrange);
	}

	@Override
	public StockInfoVO show(String[] time) {
		// TODO Auto-generated method stub
		return Info.show(time);
	}

	@Override
	public ResultMessage initialize(StockInitializeVO vo) {
		// TODO Auto-generated method stub
		Adjust = new StockAdjust(Data,vo.getRowall()*vo.getSeat()*vo.getSeat(),vo.getRowall(),vo.getShelf(),vo.getSeat(),Change);
		return Info.initialize(vo);
	}

	@Override
	public ResultMessage check(String path) {
		// TODO Auto-generated method stub
		return Info.check(path);
	}

	@Override
	public ResultMessage Input(int row, int shelf, int seat, String id,
			String date) {
		// TODO Auto-generated method stub
		return Change.Input(row, shelf, seat, id, date);
		
	}

	@Override
	public ResultMessage Output(int row, int shelf, int seat) {
		// TODO Auto-generated method stub
		return Change.Output(row, shelf, seat);
	}

	@Override
	public WarningMessage StockSafe() {
		// TODO Auto-generated method stub
		return Change.StockSafe();
	}
	
	@Override
	public boolean StockExist() {
		// TODO Auto-generated method stub
		return Change.StockExist();
	}

}
