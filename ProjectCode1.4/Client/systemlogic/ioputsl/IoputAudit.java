package ioputsl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.IoputPO;
import vo.IoputVO;
import dataservice.IoputDataService;
import dataserviceimpl.DataFactory;
import enums.Ioput;
import enums.ResultMessage;
import auditsl.AuditInfo;

public class IoputAudit  implements AuditInfo {
	private IoputDataService IoputData;
	public IoputAudit(){
		try {
			IoputData = DataFactory.create().getIoputData();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public ArrayList<?> getAuditInfo() {
		// TODO Auto-generated method stub
		ArrayList<IoputVO> auditInfo = new ArrayList<IoputVO>();
		ArrayList<IoputPO> pos = null;
		IoputPO po;
		try {
			pos =  IoputData.findAudit();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for(int i = 1;i<pos.size();i++){
			po = pos.get(i);
			if(po.getIoput()==Ioput.in)
			auditInfo.add(new IoputVO(po.getId(),po.getInputdate(),po.getTime(),po.getDestination(),
					po.getPosition(),po.getIoput(),po.getdCondition(),
					po.getNameOfWriter()));
			else
				auditInfo.add(new IoputVO(po.getId(),po.getOutputdate(),po.getTime(),po.getDestination(),
						po.getTransport(),po.getReceiptID(),po.getIoput(),po.getCondition(),
						po.getdCondition(),po.getNameOfWriter()));
		}
		return auditInfo;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultMessage EndAudit(ArrayList<?> list) {
		// TODO Auto-generated method stub
		ResultMessage rm = ResultMessage.SUCCESS;
		
		
		ArrayList<IoputVO> auditInfo = new ArrayList<IoputVO>();
		ArrayList<IoputPO> pos = new ArrayList<IoputPO>();
		IoputVO vo;
		auditInfo = (ArrayList<IoputVO>) list;
		
		for(int i = 0;i<auditInfo.size();i++){
			vo = auditInfo.get(i);
			if(vo.getIoput()==Ioput.in)
			pos.add(new IoputPO(vo.getId(),vo.getInputdate(),vo.getTime(),vo.getDestination(),
					vo.getPosition(),vo.getIoput(),vo.getdCondition(),
					vo.getNameOfWriter()));
			else
				pos.add(new IoputPO(vo.getId(),vo.getOutputdate(),vo.getTime(),vo.getDestination(),
						vo.getTransport(),vo.getReceiptID(),vo.getIoput(),vo.getCondition(),
						vo.getdCondition(),vo.getNameOfWriter()));
		}
		for(int i =0;i<pos.size();i++){
			try {
				IoputData.update(pos.get(i));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rm = ResultMessage.FAIL;
			}
		}
		return rm;
	

	}

}
