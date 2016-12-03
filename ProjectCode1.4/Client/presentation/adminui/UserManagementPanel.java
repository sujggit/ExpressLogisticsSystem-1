package adminui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import twaver.TWaverUtil;
import usersl.LogManagementController;
import usersl.UserManagementController;
import userslservice.LogService;
import userslservice.UserService;
import vo.AccountVO;
import vo.UserVO;
import enums.ResultMessage;
import enums.Work;
import financesl.AccountManagementController;
import free.*;

public class UserManagementPanel{
	public static JPanel addUserPanel,deleteUserPanel,fixUserPanel,seekUserPanel;
	public static JTabbedPane tab;
    private static ArrayList<String> workstrlist=new ArrayList<String>();
    private static ArrayList<Work>   worklist=new ArrayList<Work>();
    private static    UserService us;
    private static int accountLength=9;
    private static String userId;
	private static LogService ls;
	private static  DefaultTableModel model;
	
	
	

	
	public static FreeReportPage  createUserManagementPage(JTabbedPane tab,String Id){
		userId=Id;
        ls=new LogManagementController();
		
	    UserManagementPanel.tab=tab;
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
	    us=new UserManagementController();
	    
	    
	    
	    
	    
		return createReportPage();

	}
	
	private static void createAddUserPanel(){
		addUserPanel=new JPanel();
		JLabel account=new JLabel("�˺ţ�");
		JLabel name=new JLabel("������");
		JLabel code=new JLabel("���룺");
		JLabel privileges=new JLabel("Ȩ��:");
		JLabel work=new JLabel("������λ:");
		JTextField accountfield=new JTextField(10);
		JTextField namefield=new JTextField(20);
		JTextField codefield=new JTextField(20);
		JComboBox privilegesCombo=new JComboBox();
		privilegesCombo.addItem("��");
		privilegesCombo.addItem("��");
		JComboBox workCombo=new JComboBox();
		workCombo.addItem("���Ա");
		workCombo.addItem("Ӫҵ��ҵ��Ա");
		workCombo.addItem("��ת����ҵ��Ա");
		workCombo.addItem("��ת���Ĳֿ����Ա");
		workCombo.addItem("������Ա");
		workCombo.addItem("����Ա");
		workCombo.addItem("�ܾ���");
		workCombo.addItem("˾��");
		
		
        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,name,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,code,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,privileges,constraints,0,3,1,1);
		BaseUI.myAdd(bpanel,work,constraints,0,4,1,1);
		
		BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
		BaseUI.myAdd(bpanel,namefield,constraints,1,1,1,1);
		BaseUI.myAdd(bpanel,codefield,constraints,1,2,1,1);
		BaseUI.myAdd(bpanel,privilegesCombo,constraints,1,3,1,1);
		BaseUI.myAdd(bpanel,workCombo,constraints,1,4,1,1);
		
		JButton submit=new JButton("�ύ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {
		       String accountstr=accountfield.getText();
		       String namestr=namefield.getText();
		       String codestr=codefield.getText();
		       String privilegesstr=privilegesCombo.getSelectedItem().toString();
		       String workstr=workCombo.getSelectedItem().toString();
		       
		       if(accountstr.length()!=accountLength){
		    	   accountfield.setText("������"+accountLength+"λ�˺�");
		       }else{
		    	   ResultMessage result;
			       result=us.add(namestr, accountstr, codestr, privilegesstr, changeWorkstrToWork(workstr));
			       if(result==ResultMessage.SUCCESS){
         	    	  JOptionPane.showMessageDialog(null, "�����ɹ�"); 
         	    	  tab.remove(FreeUtil.getPagePane(addUserPanel));
         	    	  
         	    	  ls.addMessage(userId, "�û����������û�");
         	    	  
         	    	  refresh();
			       }else{
	         	    	  JOptionPane.showMessageDialog(null, "����ʧ��"); 
			       }
		       }
		       

		    }
		});    
		
		addUserPanel.add(bpanel);
	}
	
	
	
	private static void createDeleteUserPanel(){
		deleteUserPanel=new JPanel();
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		
		JLabel account=new JLabel("�˺�:");
		JTextField accountfield=new JTextField(10);
		BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
		
		JButton submit=new JButton("��ѯɾ���û���Ϣ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
              if(submit.getText().equals("��ѯɾ���û���Ϣ")){
            	if(accountfield.getText().length()!=accountLength){
            		accountfield.setText("������"+accountLength+"λ�˺�");
            	}else{
            		UserVO vo=us.select(accountfield.getText());
      		    	submit.setText("ȷ��ɾ��");
      		    	bpanel.remove(account);
      		    	bpanel.remove(accountfield);
      				JLabel account=new JLabel("�˺�:");
      				JLabel name=new JLabel("����:");
      				JLabel code=new JLabel("����:");
      				JLabel privileges=new JLabel("Ȩ��:");
      				JLabel work=new JLabel("������λ:");
      				JLabel accountfield=new JLabel(vo.getAccountnumber());
      				JLabel namefield=new JLabel(vo.getName());
      				JLabel codefield=new JLabel(vo.getCode());
      				JLabel privilegesfield=new JLabel(vo.getPrivileges());
      				JLabel workfield=new JLabel(changeWorkToWorkstr(vo.getWork()));
      				
      				BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
      				BaseUI.myAdd(bpanel,name,constraints,0,1,1,1);
      				BaseUI.myAdd(bpanel,code,constraints,0,2,1,1);
      				BaseUI.myAdd(bpanel,privileges,constraints,0,3,1,1);
      				BaseUI.myAdd(bpanel,work,constraints,0,4,1,1);
      				
      				BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
      				BaseUI.myAdd(bpanel,namefield,constraints,1,1,1,1);
      				BaseUI.myAdd(bpanel,codefield,constraints,1,2,1,1);
      				BaseUI.myAdd(bpanel,privilegesfield,constraints,1,3,1,1);
      				BaseUI.myAdd(bpanel,workfield,constraints,1,4,1,1); 
            	}

              }else if(submit.getText().equals("ȷ��ɾ��")){
            	  ResultMessage result;
            	  result=us.delete(accountfield.getText());
			       if(result==ResultMessage.SUCCESS){
	         	    	  JOptionPane.showMessageDialog(null, "ɾ���ɹ�"); 
	         	    	  tab.remove(FreeUtil.getPagePane(deleteUserPanel));
	         	    	  
	         	    	  ls.addMessage(userId, "�û�����ɾ���û�");
	         	    	  
	         	    	  refresh();
				       }else{
		         	      JOptionPane.showMessageDialog(null, "ɾ��ʧ��"); 
				       }
              }
		    }
		});
		
		deleteUserPanel.add(bpanel);
	}
	
	private static void createFixUserPanel(){
		fixUserPanel=new JPanel();
		
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		JLabel account=new JLabel("�˺�:");
		JTextField accountfield=new JTextField(10);
		BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
		
		JButton submit=new JButton("��ѯ�޸��û���Ϣ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		

		JLabel name=new JLabel("����:");
		JLabel code=new JLabel("����:");
		JLabel privileges=new JLabel("Ȩ��:");
		JLabel work=new JLabel("������λ:");

		JTextField namefield=new JTextField(20);
		JTextField codefield=new JTextField(20);
		JComboBox privilegesCombo=new JComboBox();
		privilegesCombo.addItem("��");
		privilegesCombo.addItem("��");
		JComboBox workCombo=new JComboBox();
		workCombo.addItem("���Ա");
		workCombo.addItem("Ӫҵ��ҵ��Ա");
		workCombo.addItem("��ת����ҵ��Ա");
		workCombo.addItem("��ת���Ĳֿ����Ա");
		workCombo.addItem("������Ա");
		workCombo.addItem("����Ա");
		workCombo.addItem("�ܾ���");
		workCombo.addItem("˾��");
		
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {                 
	            if(submit.getText().equals("��ѯ�޸��û���Ϣ")){
	              	if(accountfield.getText().length()!=accountLength){
	              		accountfield.setText("������"+accountLength+"λ�˺�");
	              	}else{
	              		UserVO vo=us.select(accountfield.getText());
	    				submit.setText("ȷ���޸�");

	
	    				accountfield.setText(vo.getAccountnumber());
	
	    				namefield.setText(vo.getName());
	  
	    				codefield.setText(vo.getCode());
	

	    				privilegesCombo.setSelectedItem(vo.getPrivileges());

	    				workCombo.setSelectedItem(changeWorkToWorkstr(vo.getWork()));
	    				
	    				BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
	    				BaseUI.myAdd(bpanel,name,constraints,0,1,1,1);
	    				BaseUI.myAdd(bpanel,code,constraints,0,2,1,1);
	    				BaseUI.myAdd(bpanel,privileges,constraints,0,3,1,1);
	    				BaseUI.myAdd(bpanel,work,constraints,0,4,1,1);
	    				
	    				BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
	    				BaseUI.myAdd(bpanel,namefield,constraints,1,1,1,1);
	    				BaseUI.myAdd(bpanel,codefield,constraints,1,2,1,1);
	    				BaseUI.myAdd(bpanel,privilegesCombo,constraints,1,3,1,1);
	    				BaseUI.myAdd(bpanel,workCombo,constraints,1,4,1,1);  
	    				
	              	}
	            } else if(submit.getText().equals("ȷ���޸�")){
	            	UserVO vo=new UserVO(namefield.getText(),accountfield.getText(),codefield.getText(),
	            			privilegesCombo.getSelectedItem().toString(),changeWorkstrToWork(workCombo.getSelectedItem().toString()));
	            	
	            	ResultMessage result=us.saveChange(vo);
				       if(result==ResultMessage.SUCCESS){
		         	    	  JOptionPane.showMessageDialog(null, "�޸ĳɹ�"); 
		         	    	  tab.remove(FreeUtil.getPagePane(fixUserPanel));
		         	    	  
		         	    	  
		         	    	  ls.addMessage(userId, "�û������޸��û�");
		         	    	  
		         	    	  refresh();
					       }else{
			         	      JOptionPane.showMessageDialog(null, "�޸�ʧ��"); 
					       }
	            }

		    }
		    
		});
		
		fixUserPanel.add(bpanel);

		
	}
	
	private static void createSeekUserPanel(){
		seekUserPanel=new JPanel();
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		
		JLabel account=new JLabel("�˺ţ�");
		JTextField accountfield=new JTextField(10);
		BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
		
		JButton submit=new JButton("��ѯ�û���Ϣ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {  
	            if(submit.getText().equals("��ѯ�û���Ϣ")){
	              	if(accountfield.getText().length()!=accountLength){
	              		accountfield.setText("������"+accountLength+"λ�˺�");
	              	}else{
	              		UserVO vo=us.select(accountfield.getText());
	    		    	submit.setText("ȷ��");
	    		    	bpanel.remove(account);
	    		    	bpanel.remove(accountfield);
	    				JLabel account=new JLabel("�˺ţ�");
	    				JLabel name=new JLabel("������");
	    				JLabel code=new JLabel("���룺");
	    				JLabel privileges=new JLabel("Ȩ�ޣ�");
	    				JLabel work=new JLabel("������λ��");
	    				JLabel accountfield=new JLabel(vo.getAccountnumber());
	    				JLabel namefield=new JLabel(vo.getName());
	    				JLabel codefield=new JLabel(vo.getCode());
	    				JLabel privilegesfield=new JLabel(vo.getPrivileges());
	    				JLabel workfield=new JLabel(changeWorkToWorkstr(vo.getWork()));
	    				
	    				BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
	    				BaseUI.myAdd(bpanel,name,constraints,0,1,1,1);
	    				BaseUI.myAdd(bpanel,code,constraints,0,2,1,1);
	    				BaseUI.myAdd(bpanel,privileges,constraints,0,3,1,1);
	    				BaseUI.myAdd(bpanel,work,constraints,0,4,1,1);
	    				
	    				BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
	    				BaseUI.myAdd(bpanel,namefield,constraints,1,1,1,1);
	    				BaseUI.myAdd(bpanel,codefield,constraints,1,2,1,1);
	    				BaseUI.myAdd(bpanel,privilegesfield,constraints,1,3,1,1);
	    				BaseUI.myAdd(bpanel,workfield,constraints,1,4,1,1);    
	              	}
	            }else{
	            	tab.remove(FreeUtil.getPagePane(seekUserPanel));

       	    	  ls.addMessage(userId, "�û�������ѯ�û�");
       	    	  
	            }
                  
		    }
		});
		
		seekUserPanel.add(bpanel);
	}
	
    private static FreeReportPage createReportPage() {
        model = new DefaultTableModel();
        model.addColumn("�˺�");
        model.addColumn("����");
        model.addColumn("����");
        model.addColumn("Ȩ��");
        model.addColumn("������λ");
        
        ArrayList<UserVO> list=us.getAllUser();
        
        for(int i=0;i<list.size();i++){
        	Vector row=new Vector();
        	UserVO tempvo=list.get(i);
        	row.add(tempvo.getAccountnumber());
        	row.add(tempvo.getName());
        	row.add(tempvo.getCode());
        	row.add(tempvo.getPrivileges());
        	row.add(changeWorkToWorkstr(tempvo.getWork()));
        	model.addRow(row);
        }


//        for (int i = 0; i < 100; i++) {
//            Vector row = new Vector();
//            row.add("000000001");
//            row.add("����");
//            row.add("123456");
//            row.add("��");
//            row.add("���Ա");
//            model.addRow(row);
//        }

        FreeReportPage page = new FreeReportPage();
        page.getTable().setModel(model);
        page.setDescription("All Work Order Items by Part Number. Created " + new Date().toString());
        setupPageToolbar(page);

        return page;
    }

  public static void setupPageToolbar(FreePagePane page) {
	  FreeToolbarButton addUser,deleteUser,fixUser,seekUser;
	  addUser=createButton("/free/test/add.png", "�����û�", true);
      deleteUser=createButton("/free/test/update.png", "ɾ���û�", true);
      fixUser=createButton("/free/test/refresh.png", "�޸��û�", true);
      seekUser=createButton("/free/test/print.png", "�����û�", true);
      page.getRightToolBar().add(addUser);
      page.getRightToolBar().add(deleteUser);
      page.getRightToolBar().add(fixUser);
      page.getRightToolBar().add(seekUser);
      
		addUser.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=addUser.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(addUserPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createAddUserPanel();
             	    tab.addTab(title, AdminUI.createPage(addUserPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(addUserPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		
		deleteUser.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=deleteUser.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(deleteUserPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createDeleteUserPanel();
             	    tab.addTab(title, AdminUI.createPage(deleteUserPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(deleteUserPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		
		fixUser.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=fixUser.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(fixUserPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createFixUserPanel();
             	    tab.addTab(title, AdminUI.createPage(fixUserPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(fixUserPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		seekUser.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=seekUser.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(seekUserPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createSeekUserPanel();
             	    tab.addTab(title, AdminUI.createPage(seekUserPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(seekUserPanel);
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
  
  private static Work changeWorkstrToWork(String work){
     
	int index=workstrlist.indexOf(work);
	  	  
	return worklist.get(index);
	  
  }
  
  private static String changeWorkToWorkstr(Work work){
	  int index=worklist.indexOf(work);
	  return workstrlist.get(index);
  }
  
  private static void refresh(){
	  while(model.getRowCount()>0){
		  model.removeRow(0);
	  }
	  
      ArrayList<UserVO> list=us.getAllUser();
      
      for(int i=0;i<list.size();i++){
      	Vector row=new Vector();
      	UserVO tempvo=list.get(i);
      	row.add(tempvo.getAccountnumber());
      	row.add(tempvo.getName());
      	row.add(tempvo.getCode());
      	row.add(tempvo.getPrivileges());
      	row.add(changeWorkToWorkstr(tempvo.getWork()));
      	model.addRow(row);
      }
      
  }
  
  

}
