package stockui;

import ioputsl.IoputController;
import ioputslservice.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import twaver.TWaverUtil;
import vo.OutMessageVO;
import enums.ResultMessage;
import free.BaseUI;
import free.FreeReportPage;
import free.FreeTable;
import free.FreeUtil;

public class OutputStockPanel extends JPanel{
	public static JPanel OutputPanel;
	public static JTabbedPane tab;
	private static JPanel InfoPanel;
	
    private static FreeTable table = new FreeTable();
    private static JScrollPane scroll = new JScrollPane(table);
    
    private static JTextField lbDescription = new JTextField();
    
    private static Border descriptionBorder = BorderFactory.createEmptyBorder(0, FreeUtil.TABLE_CELL_LEADING_SPACE, 0, 0);
    private static Color descriptionTextColor = new Color(120, 123, 154);
    private static Color descriptionBackgroundColor = new Color(226, 228, 229);
    
    private static IoputService IoputService = null;
	
	private static void createOutputPanel(String userId){
		OutputPanel=new JPanel();
		JLabel ExpressID=new JLabel("出库快递编号");
		MaskFormatter mask=null;
		try {
		mask = new MaskFormatter("##########");
		} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		JFormattedTextField IDfield = new JFormattedTextField(mask);
		
		
        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,1,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,ExpressID,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,IDfield,constraints,1,0,10,1);
		JButton confirm=new JButton("出库");
		confirm.addMouseListener(new MouseAdapter(){@Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	     
                try{
                 JDialog info = new JDialog();
                 OutMessageVO vo = IoputService.showOutputInfo(IDfield.getText(),userId);
                 if(vo!=null)
                 createInfoPanel(vo,info,userId);
                 else
                	 JOptionPane.showMessageDialog(null, "出库失败");
                }catch(Exception e){

             
		}}});
		
		JButton report=new JButton("丢失报告");
		report.addMouseListener(new MouseAdapter(){@Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	     
                try{
                 ResultMessage rm;
                	rm = IoputService.report(IDfield.getText(),userId);
                	if(rm==ResultMessage.FAIL)
                		JOptionPane.showMessageDialog(null, "报告失败");
                	else
                		JOptionPane.showMessageDialog(null, "报告完成");  
                	refreashTable(userId);
                }catch(Exception e){

             
		}}});
		
		BaseUI.myAdd(bpanel,confirm,constraints,0,1,2,1);
		BaseUI.myAdd(bpanel,report,constraints,5,1,2,1);
		
		OutputPanel.add(bpanel);
	}
	
	public static FreeReportPage createOutputManagementPage(JTabbedPane tab, String userId,IoputService ioput){
		IoputService = ioput;
		OutputStockPanel.tab = tab;
		return createManagementPage(userId);
	}
	
    private static FreeReportPage createManagementPage(String userId) {
    	
        FreeReportPage page = new FreeReportPage();
        createOutputPanel(userId);
        
    	
    	 scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	    page.getCenterPane().add(scroll, BorderLayout.SOUTH);
    	    page.getCenterPane().add(OutputPanel,BorderLayout.CENTER);
    	    
    	    page.getCenterPane().add(lbDescription, BorderLayout.NORTH);

    	    JLabel lbCorner = new JLabel();
    	    lbCorner.setOpaque(true);
    	    lbCorner.setBackground(descriptionBackgroundColor);
    	    scroll.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, lbCorner);

    	    lbDescription.setForeground(descriptionTextColor);
    	    lbDescription.setBackground(descriptionBackgroundColor);
    	    lbDescription.setOpaque(true);
    	    lbDescription.setBorder(descriptionBorder);
    	    lbDescription.setEditable(false);
    	    lbDescription.setFont(FreeUtil.FONT_12_BOLD);
    	
        refreashTable(userId);
        lbDescription.setText("待出库快递列表 " + new Date().toString());

        return page;
    }
    
	private static void  createInfoPanel(OutMessageVO vo,JDialog dialog,String userId){
		
		InfoPanel = new JPanel();
		
		JLabel ID = new JLabel("快递编号:"+vo.getID());
		JLabel rID = new JLabel("单据编号"+vo.getReceiptID());
		JLabel transport = new JLabel ("运输方式"+vo.getTransport());
		JLabel Destination = new JLabel("目的地"+vo.getDestination());
		JLabel date = new JLabel("出库日期"+vo.getOutputdate());
		JButton confirm=new JButton("确认");
		
		
		confirm.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	     
                try{
               
                 dialog.dispose();
                 refreashTable(userId);
                }catch(Exception e){

             
		}}});
		

        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,ID,constraints,0,0,10,1);
		BaseUI.myAdd(bpanel, rID, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, transport, constraints, 0,2, 1, 1);
		BaseUI.myAdd(bpanel,Destination,constraints,0,3,2,1);

		BaseUI.myAdd(bpanel,date,constraints,0,4,2,1);

		BaseUI.myAdd(bpanel, confirm, constraints, 0, 5, 2, 1);
		InfoPanel.add(bpanel);

}

	private static void refreashTable(String userId){
	   	
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("快递编号");
        model.addColumn("快递位置");
        ArrayList<String[]> outputList = IoputService.getOutputList(userId);


        for (int i = 0; i < outputList.size(); i++) {
            Vector row = new Vector();
            row.add(outputList.get(i)[0]);
            row.add(outputList.get(i)[1]);
            model.addRow(row);
        }


        table.setModel(model);
	}


}

 
