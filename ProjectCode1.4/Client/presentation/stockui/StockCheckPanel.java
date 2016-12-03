package stockui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stockslservice.StockService;
import vo.OutMessageVO;
import enums.ResultMessage;
import free.BaseUI;
import free.FreePagePane;
import free.FreeTabbedPane;

public class StockCheckPanel extends JPanel {
	private static JPanel StockCheckPage;
	private static StockService stock;

	public static FreePagePane createStockCheckPage(FreeTabbedPane tab,StockService s) {
		// TODO Auto-generated method stub
		stock = s;
		StockCheckPage=new JPanel();
		
		
        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JButton check=new JButton("��ʼ����̵�");
		JTextField path = new JTextField("default path");
		check.addMouseListener(new MouseAdapter(){@Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	     
                try{
                	ResultMessage rm =stock.check(path.getText());
                 if(rm==ResultMessage.SUCCESS)
                 JOptionPane.showMessageDialog(null, "�ѳɹ�����EXCEL�ļ�"); 
                 if(rm == ResultMessage.FAIL)
                 JOptionPane.showMessageDialog(null, "����̵�ʧ��"); 
                }catch(Exception e){

             
		}}});
		BaseUI.myAdd(bpanel,path,constraints,0,0,2,1);
		BaseUI.myAdd(bpanel,check,constraints,0,2,2,1);
		
		StockCheckPage.add(bpanel);
		
		FreePagePane page = new FreePagePane(StockCheckPage);
        return page;
	}

}
