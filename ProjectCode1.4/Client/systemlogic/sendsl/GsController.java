package sendsl;

import java.util.ArrayList;

import enums.ResultMessage;
import auditsl.AuditInfo;

public class GsController implements AuditInfo{
	private GatheringSend Gathering;
	
	public GsController(){
		Gathering = new GatheringSend();
	}
	@Override
	public ArrayList<?> getAuditInfo() {
		return Gathering.getAuditInfo();
	}

	@Override
	public ResultMessage EndAudit(ArrayList<?> list) {
		return Gathering.EndAudit(list);
	}

}
