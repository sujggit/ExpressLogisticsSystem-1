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
		
		JLabel Area=new JLabel("ѡ���������");
		JComboBox<String> AreaCombo=new JComboBox<String>();
		areas = StockService.getAreas();

		AreaCombo.addItem("������(������"+areas[0].getEmptyPercent()+")");
		AreaCombo.addItem("������(������"+areas[1].getEmptyPercent()+")");
		AreaCombo.addItem("������(������"+areas[2].getEmptyPercent()+")");
		AreaCombo.addItem("������(������"+areas[3].getEmptyPercent()+")");
		JButton check = new JButton("��ʾ���෶Χ");
		check.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) 
		
		    {                   
                try{
                	
            	    DefaultTableModel model = new DefaultTableModel();
            	    model.addColumn("����λ��");

            	    AreaVO selected = null;
            	    
            	    if(AreaCombo.getSelectedItem().equals("������(������"+areas[0].getEmptyPercent()+")")){
            	    	selected = areas[0];
            	    };
            	    if(AreaCombo.getSelectedItem().equals("������(������"+areas[1].getEmptyPercent()+")")){
            	    	selected = areas[1];
            	    };
            	    if(AreaCombo.getSelectedItem().equals("������(������"+areas[2].getEmptyPercent()+")")){
            	    	selected = areas[2];
            	    };
            	    if(AreaCombo.getSelectedItem().equals("������(������"+areas[3].getEmptyPercent()+")")){
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

		JLabel Range=new JLabel("���������Χ");
		JFormattedTextField Rangefield= new JFormattedTextField(mask);
		JButton confirm = new JButton("ȷ�ϵ���");
		
		confirm.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) 
		
		    {                
				if(StockService.getWarningArea()!=null){
                try{
                	if(Rangefield.getText().length()!=27)
                		JOptionPane.showMessageDialog(null, "�����������Χ��������������λ����Ϊ��λ������001��002��003��");
                	else if(Rangefield.getText().contains("000")){
    		    		JOptionPane.showMessageDialog(null, "������������λ����001��ʼ"); 
    		    	}else{ 
                	try{
                		WarningMessage wm = StockService.range(Rangefield.getText());
                		if(wm==WarningMessage.WARNING)
                			JOptionPane.showMessageDialog(null, "������90%������п�������");
                		if(wm==WarningMessage.NORMAL)
                			JOptionPane.showMessageDialog(null, "�����������");
                		if(wm==WarningMessage.FAIL)
                			JOptionPane.showMessageDialog(null, "��������ʧ��");
                	}catch(Exception e){
                		JOptionPane.showMessageDialog(null, "���ȳ�ʼ�����");
                	}
                	}
                }catch(Exception ex){
                 
                }
                }else{
                	JOptionPane.showMessageDialog(null, "Ŀǰ�����������");
                }

             
		    }
		});
		String ATA = StockService.getWarningArea();
		if(ATA==null){
			ATA="�������";
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
