package stockui;

import ioputslservice.IoputService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import vo.InMessageVO;
import enums.WarningMessage;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeTable;
import free.FreeUtil;



public class InputStockPanel extends JPanel {
	public static JPanel InputPanel;
	public static JTabbedPane tab;
	public static JPanel PositionInputPanel;
	
	private static IoputService Ioput = null;
	

    private static FreeTable table = new FreeTable();
    private static JScrollPane scroll = new JScrollPane(table);
    
    private static JTextField lbDescription = new JTextField();
    
    private static Border descriptionBorder = BorderFactory.createEmptyBorder(0, FreeUtil.TABLE_CELL_LEADING_SPACE, 0, 0);
    private static Color descriptionTextColor = new Color(120, 123, 154);
    private static Color descriptionBackgroundColor = new Color(226, 228, 229);
    
	
	

	
	public static FreePagePane  createInputManagementPage(JTabbedPane tab, String userId,IoputService ioput){
		Ioput = ioput;
		OutputStockPanel.tab = tab;
		return createManagementPage(userId);

	}
	
	private static void  createPositionInputPanel(InMessageVO vo,JDialog dialog,String userId){
		
		PositionInputPanel = new JPanel();
		
		JLabel inputP = new JLabel("输入位置");
		
		
		MaskFormatter mask=null;
		try {
		mask = new MaskFormatter("(###,###,###)");
		mask.setPlaceholderCharacter('0');
		} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		JFormattedTextField position= new JFormattedTextField(mask);
		JLabel ID = new JLabel("快递编号:"+vo.getID());
		JLabel Destination = new JLabel("目的地"+vo.getDestination());
		JLabel Inputdate = new JLabel("入库日期"+vo.getInputdate());
		JButton confirm=new JButton("确认入库");
		
		
		confirm.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	
		    	if(position.getText().contains("000")){
		    		JOptionPane.showMessageDialog(null, "排数、架数、位数从001开始"); 
		    	}else if(position.getText().length()<13){
		    		JOptionPane.showMessageDialog(null, "请输入快递入库位置，排数、架数、位数均为三位数字如001、002、003等"); 
		    	}else{    
                try{
                	WarningMessage wm = Ioput.position(position.getText().substring(1,12),vo,userId);
                    dialog.dispose();
                if(wm==WarningMessage.FAIL)
                   	 JOptionPane.showMessageDialog(null, "入库失败");  
                 if(wm==WarningMessage.NORMAL)
                	 JOptionPane.showMessageDialog(null, "成功入库");  
                 	 refreashTable(userId);
                 if(wm==WarningMessage.WARNING)
                	 JOptionPane.showMessageDialog(null, "该区域库存超过90%，请进行库区调整"); 
                }catch(Exception e){

             
		}}}});
		

        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,ID,constraints,0,0,10,1);
		BaseUI.myAdd(bpanel,Destination,constraints,0,1,2,1);

		BaseUI.myAdd(bpanel,Inputdate,constraints,0,2,2,1);
		BaseUI.myAdd(bpanel, inputP, constraints, 0, 3, 1, 1);
		BaseUI.myAdd(bpanel, position, constraints, 2, 3, 10, 1);
		BaseUI.myAdd(bpanel, confirm, constraints, 0, 4, 2, 1);
		PositionInputPanel.add(bpanel);

}

		
		private static void createInputPanel(String userId){
			InputPanel=new JPanel();
			JLabel ExpressID=new JLabel("入库快递编号");
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
			constraints.insets=new Insets(25,0,25,0);
			JPanel bpanel = new JPanel();
			bpanel.setLayout(gb);
			
			BaseUI.myAdd(bpanel,ExpressID,constraints,0,0,1,1);
			BaseUI.myAdd(bpanel,IDfield,constraints,1,0,20,1);
			JButton confirm=new JButton("入库");
			confirm.addMouseListener(new MouseAdapter(){
			    @Override
			    public void mouseClicked(MouseEvent arg0) 
			    {   
                	if(IDfield.getText().length()==10){  

	                try{

	                 InMessageVO vo = Ioput.showInputInfo(IDfield.getText(),userId);
	                 JDialog position = new JDialog();
	                 createPositionInputPanel(vo,position,userId);
	                 position.setSize(500,500);
	                 position.setAlwaysOnTop(true);
	                 position.add(PositionInputPanel);
	                 position.setVisible(true);
	                	}catch(Exception ex){

	                }}
                	else
                		JOptionPane.showMessageDialog(null, "请输入十位快递编号");  

	             
			    }
			});
			BaseUI.myAdd(bpanel,confirm,constraints,0,2,2,1);
			
			
			InputPanel.add(bpanel,BorderLayout.NORTH);
		}
		
		
	    private static FreeReportPage createManagementPage(String userId) {
	    	
	        FreeReportPage page = new FreeReportPage();
	        createInputPanel(userId);
	        
	    	
	    	 scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	    	    page.getCenterPane().add(scroll, BorderLayout.SOUTH);
	    	    page.getCenterPane().add(InputPanel,BorderLayout.CENTER);
	    	    
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

	        lbDescription.setText("待入库快递列表 " + new Date().toString());
	        return page;

	    }
	    
	    private static void refreashTable(String userId){
	        DefaultTableModel model = new DefaultTableModel();
	        model.addColumn("快递编号");
	        model.addColumn("运输方式");
	        ArrayList<String[]> inputList = Ioput.getInputList(userId);


	        for (int i = 0; i < inputList.size(); i++) {
	            Vector row = new Vector();
	            row.add(inputList.get(i)[0]);
	            row.add(inputList.get(i)[1]);
	            model.addRow(row);
	        }


	        table.setModel(model);
	    }
	    


}
 