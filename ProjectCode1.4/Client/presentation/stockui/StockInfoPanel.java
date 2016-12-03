package stockui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import stockslservice.StockService;
import vo.StockInfoVO;
import enums.WarningMessage;
import free.BaseUI;
import free.FreePagePane;
import free.FreeTabbedPane;

public class StockInfoPanel extends JPanel {
	static JPanel StockInfo;
	static JPanel InfoShowPanel;
	private static StockService stock;

	public static FreePagePane createStockInfoPage(FreeTabbedPane tab,StockService s) {
		// TODO Auto-generated method stub
		stock = s;
		StockInfo=new JPanel();
		JLabel StartTime=new JLabel("���뿪ʼʱ��");
		JLabel EndTime=new JLabel("�������ʱ��");
		

		MaskFormatter mask=null;
		try {
		mask = new MaskFormatter("####-##-##/##:##:##");
		} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		
		JTextField STimefield=new JFormattedTextField(mask);
		JTextField ETimefield=new JFormattedTextField(mask);
		
		
        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,StartTime,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,STimefield,constraints,1,0,50,1);
		
		BaseUI.myAdd(bpanel,EndTime,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,ETimefield,constraints,1,1,50,1);
		JButton confirm=new JButton("��ѯ");
		
		
		confirm.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) 
		
		    {                   
                try{
                	
                	String[] time = {STimefield.getText(),ETimefield.getText()};
                	if(time[0].contains(" ")||time[1].contains(" "))
                	JOptionPane.showMessageDialog(null, "�������ѯʱ��");  
                	else{
                		StockInfoVO vo = stock.show(time);
                		if(vo!=null){
                			JDialog dialog = new JDialog();
                			createInfoShowPanel(vo,dialog);
                			dialog.add(InfoShowPanel);
                			dialog.setSize(500,500);
                			dialog.setVisible(true);
                		}
                		else
                			JOptionPane.showMessageDialog(null, "����ؼ�¼");
                	}
               
                }catch(Exception ex){
                 
                }
		    }});

		
        BaseUI.myAdd(bpanel,confirm,constraints,0,2,2,1);
		
		StockInfo.add(bpanel);
		
		FreePagePane page = new FreePagePane(StockInfo);
        return page;
	}
	


	private static void createInfoShowPanel(StockInfoVO vo, JDialog dialog) {
		// TODO Auto-generated method stub
		InfoShowPanel = new JPanel();
		

		JLabel InSum = new JLabel("�������"+vo.getInSum());
		JLabel OutSum = new JLabel("��������"+vo.getOutSum());

		JLabel SeatSum =new JLabel("λ������"+vo.getSeatSum());
		JLabel ESeatSum = new JLabel("����λ������"+vo.getSeatEmptySum());
		JLabel StockSum =new JLabel("�������"+vo.getStockSum());
		JButton confirm = new JButton("ȷ��");
		
		confirm.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	     
                try{
                	dialog.dispose();
                }catch(Exception e){

             
		}}});
		

        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,InSum,constraints,0,0,10,1);
		BaseUI.myAdd(bpanel,OutSum,constraints,0,1,2,1);

		BaseUI.myAdd(bpanel,ESeatSum,constraints,0,2,2,1);
		BaseUI.myAdd(bpanel, SeatSum, constraints, 0, 3, 1, 1);
		BaseUI.myAdd(bpanel, StockSum, constraints, 2, 3, 10, 1);
		BaseUI.myAdd(bpanel, confirm, constraints, 0, 4, 2, 1);
		InfoShowPanel.add(bpanel);
		
	}

}
