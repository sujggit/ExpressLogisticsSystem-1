package dataserviceimpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import dataservice.IoputDataService;
import enums.Condition;
import enums.DocumentCondition;
import enums.Ioput;
import enums.Position;
import enums.ResultMessage;
import enums.Traffic;
import enums.TransportType;
import link.Helper;
import po.IoputPO;
import po.TransportPO;

public class IoputDataImpl extends UnicastRemoteObject implements IoputDataService {

	private IoputDataImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IoputPO find(String id) {
		// TODO Auto-generated method stub
		String sql = "select *from ioputpo where id='"+id+"';";
		IoputPO po = null;
		ResultSet result = null;
		try{
			result = Helper.find(sql);
			if(result.next())
				if(result.getString("ioput").equals("in"))
					po = new IoputPO(result.getString("id"),result.getString("inputdate"),result.getString("time"),result.getString("destination"),result.getString("position"),Ioput.valueOf(result.getString("ioput")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
				else po = new IoputPO(result.getString("id"),result.getString("outputdate"),result.getString("time"),result.getString("destination"),Traffic.valueOf(result.getString("transport")),result.getString("receiptid"),Ioput.valueOf(result.getString("ioput")),Condition.valueOf(result.getString("condition")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
		}catch (Exception e){
			e.printStackTrace();
		}
		return po;
	}

	
	@Override
	public ResultMessage insert(IoputPO po) {
		String sql1 = "insert into ioputpo(id,inputdate,time,position,destination,ioput,documentcondition,nameofwriter) values('"+po.getID()+"','"+po.getInputDate()+"','"+po.getTime()+"','"+po.getPosition()+"','"+po.getDestination()+"','"+po.getIoput()+"','"+po.getdCondition()+"','"+po.getNameOfWriter()+"');";
//		String sql2 = "insert into ioputpo(id, outputdate, time, destination , receiptid, condition, documentcondition, nameofwriter) values('"+po.getID()+"','"+po.getOutputDate()+"','"+po.getTime()+"','"+po.getDestination()+"','"+po.getReceiptID()+"','"+po.getCondition()+"','"+po.getdCondition()+"','"+po.getNameOfWriter()+"');";
		String sql2 = "insert into ioputpo(id, outputdate, time, destination , receiptid, documentcondition, nameOfwriter , ioput , transport) values('"+po.getID()+"','"+po.getOutputDate()+"','"+po.getTime()+"','"+po.getDestination()+"','" + po.getReceiptID()+"','"+po.getdCondition()+ "','"+po.getNameOfWriter()+"','"+po.getIoput()+"','"+po.getTransport()+"');";
	
		// TODO Auto-generated method stu
		if(po.getIoput()==Ioput.in)
		return	Helper.insert(sql1);
        return Helper.insert(sql2); 
	}

	@Override
	public ResultMessage delete(IoputPO PO) {
		String sql = "delete from ioputpo where id='"+PO.getId()+"';";
		return Helper.delete(sql);
	}

	@Override
	public ResultMessage update(IoputPO PO) {
		// TODO Auto-generated method stub
	    ResultMessage result = delete(PO);
	    if(result==ResultMessage.FAIL)
	    	return result;
	    return insert(PO);

	}

	  public static IoputDataImpl create() throws RemoteException{
			if(ioput == null){
				synchronized(IoputDataImpl.class){
			
			if(ioput == null)
			ioput = new IoputDataImpl();
				}
			}
				
			return ioput;
		}
		
	   private volatile static IoputDataImpl ioput;

	@Override
	public ArrayList<IoputPO> finds(String[] ids) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<IoputPO>pos = new ArrayList<IoputPO>();
		for(String id:ids){
			pos.add(find(id));
		}
		return pos;
	}

	
	
	@Override
	public ArrayList<IoputPO> findWithdCondition(String nameOfWriter, DocumentCondition dCondition) {
		// TODO Auto-generated method stub
		String sql = "select *from ioputpo where nameOfWriter='"+nameOfWriter+"' and DocumentCondition='"+dCondition+"';";
		IoputPO po = null;
		ArrayList<IoputPO>pos = new ArrayList<IoputPO>();
		ResultSet result = null;
		try{
			result = Helper.find(sql);
			while(result.next())
				if(result.getString("ioput").equals("in"))
					po = new IoputPO(result.getString("id"),result.getString("inputdate"),result.getString("time"),result.getString("destination"),result.getString("position"),Ioput.valueOf(result.getString("ioput")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
				else po = new IoputPO(result.getString("id"),result.getString("outputdate"),result.getString("time"),result.getString("destination"),Traffic.valueOf(result.getString("transport")),result.getString("receiptid"),Ioput.valueOf(result.getString("ioput")),Condition.valueOf(result.getString("condition")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
		        pos.add(po);
		}catch (Exception e){
			e.printStackTrace();
		}
		return pos;
	}

	@Override
	public ArrayList<IoputPO> findAudit() throws RemoteException {
		// TODO Auto-generated method stub
		String sql = "select *from ioputpo where DocumentCondition='handing';";
		IoputPO po = null;
		ArrayList<IoputPO>pos = new ArrayList<IoputPO>();
		ResultSet result = null;
		try{
			result = Helper.find(sql);
			while(result.next())
				if(result.getString("ioput").equals("in"))
					po = new IoputPO(result.getString("id"),result.getString("inputdate"),result.getString("time"),result.getString("destination"),result.getString("position"),Ioput.valueOf(result.getString("ioput")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
				else po = new IoputPO(result.getString("id"),result.getString("outputdate"),result.getString("time"),result.getString("destination"),Traffic.valueOf(result.getString("transport")),result.getString("receiptid"),Ioput.valueOf(result.getString("ioput")),Condition.valueOf(result.getString("condition")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
		        pos.add(po);
		}catch (Exception e){
			e.printStackTrace();
		}
		return pos;
	}

	@Override
	public ArrayList<IoputPO> findTime(String[] time) throws RemoteException {
		// TODO Auto-generated method stub
		String sql = "select *from ioputpo";
		IoputPO po = null;
		ArrayList<IoputPO>pos = new ArrayList<IoputPO>();
		ResultSet result = null;
		String iotime = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
		Date data;
		Date begin = null;
		Date enDate = null ;
		 try {
			begin = df.parse(time[0]);
			 enDate = df.parse(time[1]);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			result = Helper.find(sql);
			while(result.next())
				iotime = result.getString("time");
				data = df.parse(iotime);
				if(begin.getTime()<data.getTime()&&data.getTime()<enDate.getTime()){				
				if(result.getString("ioput").equals("in"))
					po = new IoputPO(result.getString("id"),result.getString("inputdate"),result.getString("time"),result.getString("destination"),result.getString("position"),Ioput.valueOf(result.getString("ioput")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
				else po = new IoputPO(result.getString("id"),result.getString("outputdate"),result.getString("time"),result.getString("destination"),Traffic.valueOf(result.getString("transport")),result.getString("receiptid"),Ioput.valueOf(result.getString("ioput")),Condition.valueOf(result.getString("condition")),DocumentCondition.valueOf(result.getString("documentcondition")),result.getString("nameofwriter"));
		        pos.add(po);
				}
		}catch (Exception e){
			e.printStackTrace();
		}
		return pos;
	}

	@Override
	public ArrayList<TransportPO> findForIoput(String code, DocumentCondition dCondition,char i) throws RemoteException {
		// TODO Auto-generated method stub
		
		String sql =null;
		if(i == '1')
			sql = "select *from transportpo where DocumentCondition='audited' and destination='"+code+"';";
		else
			sql= "select *from transportpo where DocumentCondition='audited' and departure='"+code+"';";
		TransportPO po = null;
		ArrayList<String> member = null;
		ArrayList<String> order = null;
		ArrayList<Condition> condition = null;
		ArrayList<TransportPO>pos = new ArrayList<TransportPO>();
		ResultSet result = null;
		try{
			result = Helper.find(sql);
			while(result.next()){
			if(result.getString("id").charAt(14)==i){
				try {
					member = (ArrayList<String>)IOObject.getArray(result.getBytes("member"));
					order =  (ArrayList<String>)IOObject.getArray(result.getBytes("order"));
					condition = (ArrayList<Condition>)IOObject.getArray(result.getBytes("condition"));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				po = new TransportPO(TransportType.valueOf(result.getString("sign")),result.getString("id"),result.getString("departure"),
						result.getString("destination"),result.getString("transportId"),result.getString("time"),
						result.getString("trafficID"),
						Traffic.valueOf(result.getString("traffic")),
						result.getDouble("fare"),member,order,condition,
						DocumentCondition.valueOf(result.getString("DocumentCondition")),result.getString("nameOfWriter"));
				pos.add(po);
				
			}		        
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return pos;
		
	}

	   
}
