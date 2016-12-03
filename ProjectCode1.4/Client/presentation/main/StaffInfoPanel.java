package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import dataserviceimpl.DataFactory;
import adminui.AdminUI;
import pamanagementsl.PManagement;
import pamanagementsl.PManagementController;
import twaver.TWaverUtil;
import usersl.LogManagementController;
import usersl.UserManagement;
import usersl.UserManagementController;
import userslservice.LogService;
import vo.StaffVO;
import vo.UserVO;
import enums.ResultMessage;
import enums.Sex;
import enums.Work;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;

public class StaffInfoPanel extends JPanel{
	
	public static JTabbedPane tab;
	static JPanel fixCodePanel;
	static String userId;
	static StaffVO staffVO;
	public static UserVO userVO;

	private static ArrayList<Sex> sexlist=new ArrayList<Sex>();
	private static ArrayList<String> sexstrlist=new ArrayList<String>();
	private static ArrayList<Work> worklist=new ArrayList<Work>();
	private static ArrayList<String> workstrlist=new ArrayList<String>();
	

	
	public static FreeReportPage  createStaffInfoPage(JTabbedPane tab,String userId){

	    StaffInfoPanel.tab=tab;
	    StaffInfoPanel.userId=userId;
	    
	    sexlist.add(Sex.male);
	    sexlist.add(Sex.female);
	    sexstrlist.add("��");
	    sexstrlist.add("Ů");
	    
	    workstrlist.add("���Ա");
	    worklist.add(Work.Courier);
	    workstrlist.add("Ӫҵ��ҵ��Ա");
	    worklist.add(Work.Officer);
	    workstrlist.add("��ת����ҵ��Ա");
	    worklist.add(Work.TransOffice);
	    workstrlist.add("˾��");
	    worklist.add(Work.Driver);
	    workstrlist.add("��ת���Ĳֿ����Ա");
	    worklist.add(Work.Stock);
	    workstrlist.add("����Ա");
	    worklist.add(Work.Admin);
	    workstrlist.add("�ܾ���");
	    worklist.add(Work.Manager);
	    workstrlist.add("������Ա");
	    worklist.add(Work.Finance);
	    
		return createReportPage();
	}
	
    private static FreeReportPage createReportPage() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("�˺�");
        model.addColumn("����");
        model.addColumn("�Ա�");
        model.addColumn("������");
        model.addColumn("ְλ");

        

        StaffInfoPanel.findUser(userId);
        
        Vector row=new Vector();
        row.add(userId);
        row.add(staffVO.getName());
        row.add(changeSexToSexstr(staffVO.getSex()));
        row.add(staffVO.getWorkPlaceNumber());
        row.add(changeWorkToWorkstr(staffVO.getWork()));
        model.addRow(row);
        

//        for (int i = 0; i < 1; i++) {
//            Vector row = new Vector();
//            row.add("000000001");
//            row.add("����");
//            row.add("��");
//            row.add("��¥Ӫҵ��");
//            row.add("����Ա");
//            model.addRow(row);
//        }

        FreeReportPage page = new FreeReportPage();
        page.getTable().setModel(model);
        page.setDescription("��������ĸ�����Ϣ") ;
        setupPageToolbar(page);

        return page;
    }

  public static void setupPageToolbar(FreePagePane page) {
	  FreeToolbarButton fixCode;

      fixCode=createButton("/free/test/refresh.png", "�޸�����", true);
 
      page.getRightToolBar().add(fixCode);
      

		
		fixCode.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=fixCode.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(fixCodePanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createFixCodePanel();
             	    tab.addTab(title, AdminUI.createPage(fixCodePanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(fixCodePanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
      
}
  
	private static void createFixCodePanel(){
		fixCodePanel=new JPanel();
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		
		JLabel oldcode=new JLabel("�����룺");
		JTextField oldcodefield=new JTextField(16);
		BaseUI.myAdd(bpanel,oldcode,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,oldcodefield,constraints,1,0,1,1);
		JLabel newcode=new JLabel("�����룺");
		JTextField newcodefield=new JTextField(16);
		BaseUI.myAdd(bpanel,newcode,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,newcodefield,constraints,1,1,1,1);
		JLabel newcodecheck=new JLabel("������ȷ�ϣ�");
		JTextField newcodecheckfield=new JTextField(16);
		BaseUI.myAdd(bpanel,newcodecheck,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,newcodecheckfield,constraints,1,2,1,1);
		
		
		JButton submit=new JButton("ȷ���޸�");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
                   String oldcodestr=oldcodefield.getText();
                   String newcodestr=newcodefield.getText();
                   String newcodecheckstr=newcodecheckfield.getText();
                   
                   if(!oldcodestr.equals(userVO.getCode())){
                	   oldcodefield.setText("�������������");
                   }else if(!newcodestr.equals(newcodecheckstr)){
                	   newcodefield.setText("������ǰ�����벻һ��");
                	   newcodecheckfield.setText("������ǰ�����벻һ��");
                   }else{
                	      userVO.setCode(newcodestr);
                	      UserManagement userManagement=UserManagement.creatUserManagement();
                	      ResultMessage message=userManagement.saveChange(userVO);
                	      if(message==ResultMessage.SUCCESS){
                	    	  JOptionPane.showMessageDialog(null, "�޸ĳɹ�"); 
                	    	  tab.remove(FreeUtil.getPagePane(fixCodePanel));
                	    	  
                 	    	  LogService ls=new LogManagementController();
                 	    	  ls.addMessage(userId, "�޸�����");
                	      }else{
                	    	  JOptionPane.showMessageDialog(null, "�޸�ʧ��"); 
                	      }
                   }
		    }
		});
		
		fixCodePanel.add(bpanel);
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

  private static void findUser(String id){
	  
	  PManagementController pm=null;
		pm = new PManagementController();

      staffVO=pm.select(id);
      UserManagementController userManagement=new UserManagementController();
      userVO=userManagement.select(id);
  }
  
  private static String changeWorkToWorkstr(Work work){
	  int index=worklist.indexOf(work);
	  return workstrlist.get(index);
  }
  
  private static String changeSexToSexstr(Sex sex){
	  int index=sexlist.indexOf(sex);
	  return sexstrlist.get(index);
  }

}
