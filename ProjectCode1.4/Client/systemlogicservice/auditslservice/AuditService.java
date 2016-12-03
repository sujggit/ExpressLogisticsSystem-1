package auditslservice;

import java.util.ArrayList;

import enums.ResultMessage;
import vo.DeliverVO;
import vo.IoputVO;
import vo.OrderVO;
import vo.PaymentVO;
import vo.ReceiptsVO;
import vo.TransportVO;




public interface AuditService {
	public ArrayList<TransportVO> getTransport();
	public ArrayList<DeliverVO> getDeliver();
	public ArrayList<PaymentVO> getPayment();
	public ArrayList<ReceiptsVO> getReceipts();
	public ArrayList<OrderVO> getOrder();
	public ResultMessage saveReceipts();
	public ResultMessage saveAll();
	public ArrayList<IoputVO> getIoput();
	
	
	
}
