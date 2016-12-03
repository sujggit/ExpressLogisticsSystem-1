package stockui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import enums.WarningMessage;
import free.BaseUI;
import free.FreePagePane;
import free.FreeTabbedPane;
import free.FreeTable;
import stockslservice.StockService;
import vo.AreaVO;

public class StockAdjustPanel extends JPanel {
	static JPanel StockAdjustPanel;
	private static StockService StockService;
	private static AreaVO[] areas;
	
	
    private static FreeTable table = new FreeTable();
    private static JScrollPane scroll = new JScrollPane(table);
    
	
	public StockAdjustPanel(StockService stock){
		StockService = stock;
	}

	public static FreePagePane createStockAdjustPage(FreeTabbedPane tab,StockService stock) {
		// TODO Auto-generated method stub
		StockService = stock;
		StockAdjustPanel = new JPanel();
		
		JLabel Area=new JLabel("选择调整区域");
		JComboBox<String> AreaCombo=new JComboBox<String>();
		areas = StockService.getAreas();

		AreaCombo.addItem("航运区(空余率"+areas[0].getEmptyPercent()+")");
		AreaCombo.addItem("铁运区(空余率"+areas[1].getEmptyPercent()+")");
		AreaCombo.addItem("汽运区(空余率"+areas[2].getEmptyPercent()+")");
		AreaCombo.addItem("机动区(空余率"+areas[3].getEmptyPercent()+")");
		JButton check = new JButton("显示空余范围");
		check.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) 
		
		    {                   
                try{
                	
            	    DefaultTableModel model = new DefaultTableModel();
            	    model.addColumn("空余位置");

            	    AreaVO selected = null;
            	    
            	    if(AreaCombo.getSelectedItem().equals("航运区(空余率"+areas[0].getEmptyPercent()+")")){
            	    	selected = areas[0];
            	    };
            	    if(AreaCombo.getSelectedItem().equals("铁运区(空余率"+areas[1].getEmptyPercent()+")")){
            	    	selected = areas[1];
            	    };
            	    if(AreaCombo.getSelectedItem().equals("汽运区(空余率"+areas[2].getEmptyPercent()+")")){
            	    	selected = areas[2];
            	    };
            	    if(AreaCombo.getSelectedItem().equals("机动区(空余率"+areas[3].getEmptyPercent()+")")){
            	    	selected = areas[3];
            	    };

            	    

if(selected!=null){
            	    for (int i = 0; i < selected.getEmptySeats().length; i++) {
            	    	Vector<String> row = new Vector();
            	    	row.add(selected.getEmptySeats()[i]);
            	    	model.addRow(row);
            	    }
}

            	    table.setModel(model);
            		
                	
               
                }catch(Exception ex){
                 
                }

             
		    }
		});
		
		
		
		
		MaskFormatter mask=null;
		try {
		mask = new MaskFormatter("(###,###,###)-(###,###,###)");
		} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}

		JLabel Range=new JLabel("输入调整范围");
		JFormattedTextField Rangefield= new JFormattedTextField(mask);
		JButton confirm = new JButton("确认调整");
		
		confirm.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) 
		
		    {                
				if(StockService.getWarningArea()!=null){
                try{
                	if(Rangefield.getText().length()!=27)
                		JOptionPane.showMessageDialog(null, "请输入调整范围，排数、架数、位数均为三位数字如001、002、003等");
                	else if(Rangefield.getText().contains("000")){
    		    		JOptionPane.showMessageDialog(null, "排数、架数、位数从001开始"); 
    		    	}else{ 
                	try{
                		WarningMessage wm = StockService.range(Rangefield.getText());
                		if(wm==WarningMessage.WARNING)
                			JOptionPane.showMessageDialog(null, "库存大于90%，请进行库区调整");
                		if(wm==WarningMessage.NORMAL)
                			JOptionPane.showMessageDialog(null, "库区调整完成");
                		if(wm==WarningMessage.FAIL)
                			JOptionPane.showMessageDialog(null, "库区调整失败");
                	}catch(Exception e){
                		JOptionPane.showMessageDialog(null, "请先初始化库存");
                	}
                	}
                }catch(Exception ex){
                 
                }
                }else{
                	JOptionPane.showMessageDialog(null, "目前无需调整库区");
                }

             
		    }
		});
		String ATA = StockService.getWarningArea();
		if(ATA==null){
			ATA="无需调整";
			confirm.setEnabled(false);
		}
		JLabel AreaToAdjust = new JLabel(ATA);
        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,Area,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,AreaCombo,constraints,1,0,5,1);
		BaseUI.myAdd(bpanel,check,constraints,7,0,20,1);
		
		BaseUI.myAdd(bpanel, AreaToAdjust, constraints, 0, 1, 5, 1);
		
		BaseUI.myAdd(bpanel,Range,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,Rangefield,constraints,1,2,20,1);
		BaseUI.myAdd(bpanel,confirm,constraints,21,2,20,1);
		
		StockAdjustPanel.add(bpanel);
		FreePagePane page = new FreePagePane();
		
   	 scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	    page.getCenterPane().add(scroll, BorderLayout.SOUTH);
	    page.getCenterPane().add(StockAdjustPanel,BorderLayout.NORTH);


        return page;
	}

}
