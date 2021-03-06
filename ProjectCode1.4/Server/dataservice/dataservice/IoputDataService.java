
package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import enums.DocumentCondition;
import enums.ResultMessage;
import po.*;

public interface IoputDataService extends Remote{
	
	public IoputPO find(String id) throws RemoteException;
	public ArrayList<IoputPO>  finds(String[]ids)throws RemoteException;
	public ArrayList<IoputPO>  findTime(String[] time)throws RemoteException;//date={"yyyy-MM-dd/HH:mm:ss","yyyy-MM-dd/HH:mm:ss"}
	public ResultMessage insert(IoputPO PO)throws RemoteException;
	public ResultMessage delete(IoputPO PO)throws RemoteException;
	public ResultMessage update(IoputPO PO)throws RemoteException;
	public ArrayList<IoputPO> findWithdCondition(String nameOfWriter, DocumentCondition dCondition)throws RemoteException;
	public ArrayList<IoputPO> findAudit()throws RemoteException;
	public ArrayList<TransportPO> findForIoput(String code,DocumentCondition dCondition,char i) throws RemoteException;

}
