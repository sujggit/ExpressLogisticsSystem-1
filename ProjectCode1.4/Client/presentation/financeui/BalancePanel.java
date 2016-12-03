package financeui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dataserviceimpl.DataFactory;
import pamanagementsl.AManagementController;
import pamanagementsl.PManagementController;
import financesl.BalanceController;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;
import twaver.TWaverUtil;
import vo.AgencyVO;
import vo.ReceiptsVO;
import vo.StaffVO;

public class BalancePanel {
	public static JPanel seekReceiptsPanel;
	public static JTabbedPane tab;
	public static ArrayList<ReceiptsVO> volist=new ArrayList<ReceiptsVO>();
	public static ArrayList<String> agencynumberlist=new ArrayList<String>();
	public static ArrayList<String> agencyname=new ArrayList<String>();

	
	

	
	public static FreeReportPage  createBalancePage(JTabbedPane tab){

	    BalancePanel.tab=tab;
	    
	    agencynumberlist.add("025000");
	    agencyname.add("��¥Ӫҵ��");
	    

		return createReportPage();
	}
	

	
	private static void createSeekReceiptsPanel(){
		seekReceiptsPanel=new JPanel();
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		
		JLabel date=new JLabel("�տ����ڣ�");
	    Date dt = new Date();   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    String timeOfReceive=sdf.format(dt); 
		JTextField datefield=new JTextField(timeOfReceive);
		BaseUI.myAdd(bpanel,date,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,datefield,constraints,1,0,1,1);
		JLabel office=new JLabel("�տλ��");
		JComboBox officefield=new JComboBox();
		for(int i=0;i<agencyname.size();i++){
			officefield.addItem(agencyname.get(i));
		}
		BaseUI.myAdd(bpanel,office,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,officefield,constraints,1,1,1,1);
		
		
		JButton submit=new JButton("��ѯ�տ��Ϣ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {       
		    	
		    	if(!datefield.getText().equals("")){
			        BalanceController bc=new BalanceController();
			        volist=bc.getBalanceMessage(datefield.getText(), changeNameToNumber(officefield.getSelectedItem().toString()));
	                tab.remove(FreeUtil.getPagePane(seekReceiptsPanel));
	                
		    	}else{
		    		  JOptionPane.showMessageDialog(null, "������������Ϣ");
		    	}

		    }
		});
		
		seekReceiptsPanel.add(bpanel);
	}
	
    private static FreeReportPage createReportPage() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("�տ�����");
        model.addColumn("�տλ");
        model.addColumn("�տ���");
        model.addColumn("�տ");
        model.addColumn("�տ���");
        model.addColumn("�տ�ص�");
        



        
        
        for (int i = 0; i < volist.size(); i++) {
            Vector row = new Vector();
            PManagementController pmc=new PManagementController();
            StaffVO tempCourier=pmc.select(volist.get(i).getCourier());
            AManagementController amc=null;
			try {
				amc = new AManagementController(DataFactory.create());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            AgencyVO tempAgency=amc.select(tempCourier.getWorkPlaceNumber());
            row.add(volist.get(i).getDate());
            row.add(tempAgency.getName());
            row.add(tempCourier.getName());
            row.add(tempCourier.getName());
            row.add(volist.get(i).getFee()+"");
            row.add(tempAgency.getName());
            model.addRow(row);
        }

        FreeReportPage page = new FreeReportPage();
        page.getTable().setModel(model);
        page.setDescription("All Work Order Items by Part Number. Created " + new Date().toString());
        setupPageToolbar(page);

        return page;
    }

  public static void setupPageToolbar(FreePagePane page) {
	  FreeToolbarButton seekReceipts;

      seekReceipts=createButton("/free/test/print.png", "��ѯ�տ", true);
 
      page.getRightToolBar().add(seekReceipts);
      

		
		seekReceipts.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=seekReceipts.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(seekReceiptsPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createSeekReceiptsPanel();
             	    tab.addTab(title, FinanceUI.createPage(seekReceiptsPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(seekReceiptsPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
      
}
  public static FreeToolbarButton createButton(String icon, String tooltip, boolean rover) {
      FreeToolbarButton button = null;
      if (rover) {
          button = new FreeToolbarRoverButton();
      } else {
          button = new FreeToolbarButton();
      }
      button.setIcon(TWaverUtil.getIcon(icon));
      button.setToolTipText(tooltip);
      
      return button;
  }
  
  public  static String changeNameToNumber(String name){
	  int index=agencyname.indexOf(name);
	  return agencynumberlist.get(index);
  }

}

