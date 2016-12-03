package ioputslservice;

import java.util.ArrayList;

import enums.ResultMessage;
import enums.WarningMessage;
import vo.InMessageVO;
import vo.OutMessageVO;

public interface IoputService {
	public InMessageVO showInputInfo (String id,String userId);
	public WarningMessage position (String position, InMessageVO vo, String userId);
	public ArrayList<String[]> getOutputList(String userId);
	public OutMessageVO showOutputInfo(String id, String userId);
	public ResultMessage report(String id, String userId);
	public ArrayList<String[]> getInputList(String userId);



}