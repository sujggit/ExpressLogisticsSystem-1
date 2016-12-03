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
		JLabel StartTime=new JLabel("输入开始时间");
		JLabel EndTime=new JLabel("输入结束时间");
		

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
		JButton confirm=new JButton("查询");
		
		
		confirm.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) 
		
		    {                   
                try{
                	
                	String[] time = {STimefield.getText(),ETimefield.getText()};
                	if(time[0].contains(" ")||time[1].contains(" "))
                	JOptionPane.showMessageDialog(null, "请输入查询时间");  
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
                			JOptionPane.showMessageDialog(null, "无相关记录");
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
		

		JLabel InSum = new JLabel("入库总数"+vo.getInSum());
		JLabel OutSum = new JLabel("出库总数"+vo.getOutSum());

		JLabel SeatSum =new JLabel("位置总数"+vo.getSeatSum());
		JLabel ESeatSum = new JLabel("空余位置总数"+vo.getSeatEmptySum());
		JLabel StockSum =new JLabel("库存总数"+vo.getStockSum());
		JButton confirm = new JButton("确认");
		
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
