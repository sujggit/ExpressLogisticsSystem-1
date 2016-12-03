package stockui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import stockslservice.StockService;
import vo.StockInitializeVO;
import enums.ResultMessage;
import enums.WarningMessage;
import free.BaseUI;
import free.FreePagePane;
import free.FreeTabbedPane;

public class StockInitializePanel extends JPanel  {
	
	static JPanel StockInitializePanel;
	static StockService stock;

	public static FreePagePane createStockInitializePage(FreeTabbedPane tab,StockService s) {
		// TODO Auto-generated method stub
		stock = s;
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		MaskFormatter mask=null;
		try {
		mask = new MaskFormatter("###");
	    mask.setPlaceholderCharacter('0');
		} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		
		
		
		StockInitializePanel=new JPanel();
		JLabel RowAll=new JLabel("总排数");
		JFormattedTextField RowAllField = new JFormattedTextField(mask);
		BaseUI.myAdd(bpanel,RowAll,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,RowAllField,constraints,1,0,5,1);
		
		JLabel Shelfs=new JLabel("每排架数");
		JTextField ShelfsField = new JFormattedTextField(mask);
		BaseUI.myAdd(bpanel,Shelfs,constraints,6,0,1,1);
		BaseUI.myAdd(bpanel,ShelfsField,constraints,7,0,5,1);
		
		JLabel Seats=new JLabel("每架位数");
		JTextField SeatsField = new JFormattedTextField(mask);
		BaseUI.myAdd(bpanel,Seats,constraints,6,1,1,1);
		BaseUI.myAdd(bpanel,SeatsField,constraints,7,1,5,1);
		
		JLabel AreaA=new JLabel("航运区");
		JTextField AStart=new JFormattedTextField(mask);;
		JLabel _A=new JLabel("（排）-");
		JTextField AEnd=new JFormattedTextField(mask);;
		JLabel A_=new JLabel("（排）");
		BaseUI.myAdd(bpanel,AreaA,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,AStart,constraints,1,2,5,1);
		BaseUI.myAdd(bpanel,_A,constraints,6,2,1,1);
		BaseUI.myAdd(bpanel,AEnd,constraints,7,2,5,1);
		BaseUI.myAdd(bpanel,A_,constraints,12,2,1,1);
		
		JLabel AreaB=new JLabel("铁运区");
		JTextField BStart=new JFormattedTextField(mask);;
		JLabel _B=new JLabel("（排）-");
		JTextField BEnd=new JFormattedTextField(mask);;
		JLabel B_=new JLabel("（排）");
		BaseUI.myAdd(bpanel,AreaB,constraints,0,3,1,1);
		BaseUI.myAdd(bpanel,BStart,constraints,1,3,5,1);
		BaseUI.myAdd(bpanel,_B,constraints,6,3,1,1);
		BaseUI.myAdd(bpanel,BEnd,constraints,7,3,5,1);
		BaseUI.myAdd(bpanel,B_,constraints,12,3,1,1);
		
		JLabel AreaC=new JLabel("汽运区");
		JTextField CStart=new JFormattedTextField(mask);;
		JLabel _C=new JLabel("（排）-");
		JTextField CEnd=new JFormattedTextField(mask);;
		JLabel C_=new JLabel("（排）");
		BaseUI.myAdd(bpanel,AreaC,constraints,0,4,1,1);
		BaseUI.myAdd(bpanel,CStart,constraints,1,4,5,1);
		BaseUI.myAdd(bpanel,_C,constraints,6,4,1,1);
		BaseUI.myAdd(bpanel,CEnd,constraints,7,4,5,1);
		BaseUI.myAdd(bpanel,C_,constraints,12,4,1,1);
		
		JLabel AreaD=new JLabel("机动区");
		JTextField DStart=new JFormattedTextField(mask);;
		JLabel _D=new JLabel("（排）-");
		JTextField DEnd=new JFormattedTextField(mask);;
		JLabel D_=new JLabel("（排）");
		BaseUI.myAdd(bpanel,AreaD,constraints,0,5,1,1);
		BaseUI.myAdd(bpanel,DStart,constraints,1,5,5,1);
		BaseUI.myAdd(bpanel,_D,constraints,6,5,1,1);
		BaseUI.myAdd(bpanel,DEnd,constraints,7,5,5,1);
		BaseUI.myAdd(bpanel,D_,constraints,12,5,1,1);

		
		
        

		

		JButton confirm=new JButton("库存信息初始化");
		confirm.addMouseListener(new MouseAdapter(){
			 @Override
			    public void mouseClicked(MouseEvent arg0) 
			    {   
			    	     
	                try{
	                	String InputCheck = "";
	                	if(RowAllField.getText().contains("000"))
	                		InputCheck = InputCheck +"请输入总排数"+'\n';
	                	if(ShelfsField.getText().contains("000"))
	                		InputCheck = InputCheck +"请输入每排架数"+'\n';
	                	if(SeatsField.getText().contains("000"))
	                		InputCheck = InputCheck +"请输入每架位数"+'\n';
	                	
	                	int rA = Integer.parseInt(RowAllField.getText());
	                	int r1[] = {Integer.parseInt(AStart.getText()),Integer.parseInt(AEnd.getText())};
	                	int r2[] = {Integer.parseInt(BStart.getText()),Integer.parseInt(BEnd.getText())};
	                	int r3[] = {Integer.parseInt(CStart.getText()),Integer.parseInt(CEnd.getText())};
	                	int r4[] = {Integer.parseInt(DStart.getText()),Integer.parseInt(DEnd.getText())};
	                	int sh = Integer.parseInt(ShelfsField.getText());
	                	int se = Integer.parseInt(SeatsField.getText());

	                
	                	if(r1[0]<=0||r2[0]<=0||r3[0]<=0||r4[0]<=0)
	                		InputCheck = InputCheck +"起始排数应大于等于1"+'\n';
	                	if(r1[1]<r1[0])
	                		InputCheck = InputCheck +"航运区结束排数应大于等于起始排数"+'\n';
	                	if(r2[1]<r2[0])
	                		InputCheck = InputCheck +"铁运区结束排数应大于等于起始排数"+'\n';
	                	if(r3[1]<r3[0])
	                		InputCheck = InputCheck +"汽运区结束排数应大于等于起始排数"+'\n';
	                	if(r4[1]<r4[0])
	                		InputCheck = InputCheck +"机动区结束排数应大于等于起始排数"+'\n';
	                	if(r1[1]>rA||r2[1]>rA||r3[1]>rA||r4[1]>rA)
	                		InputCheck = InputCheck +"区域排数不能大于总排数"+'\n';
	                	if(r1[1]>=r2[0]||r2[1]>=r3[0]||r3[1]>=r4[0])
	                		InputCheck = InputCheck +"区域范围重合"+'\n';
	                	
	                	if(InputCheck.length()==0){
	                	ResultMessage rm = stock.initialize(new StockInitializeVO(rA, r1, r2, r3, r4, sh, se));
	                	if(rm==ResultMessage.SUCCESS)
	                		JOptionPane.showMessageDialog(null, "库存初始化成功");
	                	else
	                		JOptionPane.showMessageDialog(null, "库存初始化失败");
	                	}
	                	else
	                		JOptionPane.showMessageDialog(new JPanel(), InputCheck, "输入错误",JOptionPane.WARNING_MESSAGE); 

	                		
	                }catch(Exception e){

	             
			}}});

		BaseUI.myAdd(bpanel,confirm,constraints,0,10,2,1);

		
		StockInitializePanel.add(bpanel);
		
		FreePagePane page = new FreePagePane(StockInitializePanel);
        return page;
	}

}
