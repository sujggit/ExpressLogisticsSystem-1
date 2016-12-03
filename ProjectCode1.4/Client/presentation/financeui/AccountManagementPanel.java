package financeui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.security.auth.Refreshable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;




import javax.swing.text.MaskFormatter;

import dataserviceimpl.DataFactory;
import enums.ResultMessage;
import pamanagementsl.AManagementController;
import pamanagementsl.PManagementController;
import pamanagementslservice.PManagementService;
import financesl.AccountManagementController;
import financeslservice.AccountManagementService;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;
import twaver.TWaverUtil;
import usersl.LogManagementController;
import usersl.UserManagementController;
import userslservice.LogService;
import userslservice.UserService;
import vo.AccountVO;
import vo.StaffVO;
import vo.UserVO;

public class AccountManagementPanel{
	private static JPanel addAccountPanel,deleteAccountPanel,fixAccountPanel,seekAccountPanel;
	public static JTabbedPane tab;
	private static AccountManagementService ams;
	private static AccountVO deletevo,fixvo;
	private static String userId;
	private static  LogService ls;
	private static  DefaultTableModel model;
	  
	
	
	

	
	public static FreeReportPage  createAccountManagementPage(JTabbedPane tab,String userId){

	    AccountManagementPanel.tab=tab;
	    AccountManagementPanel.userId=userId;
	    
	    UserService um=new UserManagementController();
	    ls=new LogManagementController();
	    
	    UserVO user=um.select(userId);
	    if(user.getPrivileges().equals("��")){
 	    	JOptionPane.showMessageDialog(null, "�˲���������Ȩ�޲�����Աʹ��");
 	    	return null;
	    }
//		JButton addUser,deleteUser,fixUser,seekUser;
//		addUser=new JButton("�����û�");
//		deleteUser=new JButton("ɾ���û�");
//		fixUser=new JButton("�޸��û�");
//		seekUser=new JButton("Ѱ���û�");
//		
//
//		
//		
//		userManagementPanel.add(addUser);
//		userManagementPanel.add(deleteUser);
//		userManagementPanel.add(fixUser);
//		userManagementPanel.add(seekUser);
		
		return createReportPage();
	}
	
	private static void createAddAccountPanel(){
		addAccountPanel=new JPanel();
		JLabel name=new JLabel("�˻����ƣ�");
		JLabel balance=new JLabel("�˻���");

		JTextField namefield=new JTextField(10);
		JTextField balancefield=new JTextField(20);


		
		
        
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		BaseUI.myAdd(bpanel,name,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,balance,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,namefield,constraints,1,0,1,1);
		BaseUI.myAdd(bpanel,balancefield,constraints,1,1,1,1);

		
		JButton submit=new JButton("�ύ");
		BaseUI.myAdd(bpanel,submit,constraints,0,2,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {
		    	if(!namefield.getText().equals("")&&!balancefield.getText().equals("")){
		    		ams=new AccountManagementController();
		    		try{
		    			ams.findAccount(namefield.getText());
	         	    	JOptionPane.showMessageDialog(null, "����ʧ��,�Ѵ���ͬ���˻�"); 
		    		}catch(NullPointerException e){
			    		try {
			    			ResultMessage result=null;
			    			double _balance=Double.parseDouble(balancefield.getText());
			    			result=ams.addAccount(namefield.getText(), _balance);
						       if(result==ResultMessage.SUCCESS){
				         	    	  JOptionPane.showMessageDialog(null, "�����ɹ�"); 
				         	    	  tab.remove(FreeUtil.getPagePane(addAccountPanel));
				         	    	  
				         	    	  refresh();
				         	    	  
				         	    	  ls.addMessage(userId, "�˻����������˻�");
							       }else{
					         	    	  JOptionPane.showMessageDialog(null, "����ʧ��"); 
							       }
			    		} catch (Exception ee) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    		}
		    		}

		    		
		    		

		    	}else{
       	    	  JOptionPane.showMessageDialog(null, "�����������˻���Ϣ"); 
		    	}
		    }
		});    
		
		
		addAccountPanel.add(bpanel);
	}
	
	
	
	private static void createDeleteAccountPanel(){
		deleteAccountPanel=new JPanel();
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		
		JLabel account=new JLabel("�˻���");
		JTextField accountfield=new JTextField(10);
		BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
		
		JButton submit=new JButton("��ѯɾ���˻���Ϣ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   

	            if(submit.getText().equals("��ѯɾ���˻���Ϣ")){

	              	if(accountfield.getText().equals("")){
	         	    	  JOptionPane.showMessageDialog(null, "�������˻���"); 
	              	}else{
	              		deletevo=ams.findAccount(accountfield.getText());
			    	    submit.setText("ȷ��ɾ��");
			    	    bpanel.remove(account);
			    	    bpanel.remove(accountfield);
					    JLabel name=new JLabel("�˻�����:");
					    JLabel balance=new JLabel("�˻���");

					    JLabel namefield=new JLabel(deletevo.getName());
					    JLabel balancefield=new JLabel(deletevo.getBalance()+"");
					
					    BaseUI.myAdd(bpanel,name,constraints,0,0,1,1);
					    BaseUI.myAdd(bpanel,balance,constraints,0,1,1,1);
					    BaseUI.myAdd(bpanel,namefield,constraints,1,0,1,1);
					    BaseUI.myAdd(bpanel,balancefield,constraints,1,1,1,1);
	              	}
	            }else if(submit.getText().equals("ȷ��ɾ��")){
	            	  ResultMessage result;
	            	  result=ams.deleteAccount(deletevo);
				       if(result==ResultMessage.SUCCESS){
		         	    	  JOptionPane.showMessageDialog(null, "ɾ���ɹ�"); 
		         	    	  tab.remove(FreeUtil.getPagePane(deleteAccountPanel));
		         	    	  
		         	    	  refresh();
		         	    	 
		         	    	  ls.addMessage(userId, "�˻�����ɾ���˻�");
					       }else{
			         	      JOptionPane.showMessageDialog(null, "ɾ��ʧ��"); 
					       }
	            }
				
		    }
		});
		
		deleteAccountPanel.add(bpanel);
	}
	
	private static void createFixAccountPanel(){
		fixAccountPanel=new JPanel();
		
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		JLabel account=new JLabel("�˻���");
		JTextField accountfield=new JTextField(10);
		BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
		
		JButton submit=new JButton("��ѯ�޸��˻���Ϣ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		
		JLabel name=new JLabel("�˻�����:");
		JTextField namefield=new JTextField(10);
		
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
	            if(submit.getText().equals("��ѯ�޸��˻���Ϣ")){
	              	if(accountfield.getText().equals("")){
	        	    	  JOptionPane.showMessageDialog(null, "�������˻���"); 
	              	}else{
	              	fixvo=ams.findAccount(accountfield.getText());

					JLabel balance=new JLabel("�˻����");

					namefield.setText(fixvo.getName());
					JLabel balancefield=new JLabel();
					balancefield.setText(fixvo.getBalance()+"");
					
					BaseUI.myAdd(bpanel,name,constraints,0,0,1,1);
					BaseUI.myAdd(bpanel,balance,constraints,0,1,1,1);
					BaseUI.myAdd(bpanel,namefield,constraints,1,0,1,1);
					BaseUI.myAdd(bpanel,balancefield,constraints,1,1,1,1);
					
					submit.setText("ȷ���޸�");
	              	}
	            }else if(submit.getText().equals("ȷ���޸�")){
	            	
		    		try{
		    			ams.findAccount(accountfield.getText());
	         	    	JOptionPane.showMessageDialog(null, "�޸�ʧ��,�Ѵ���ͬ���˻�"); 
		    		}catch(NullPointerException e){
		            	   ResultMessage result=null;
		            	   result=ams.fixAccount(fixvo, accountfield.getText());
					       if(result==ResultMessage.SUCCESS){
					    	      ams.deleteAccount(ams.findAccount(namefield.getText()));
			         	    	  JOptionPane.showMessageDialog(null, "�޸ĳɹ�"); 
			         	    	  tab.remove(FreeUtil.getPagePane(fixAccountPanel));
			         	    	  
			         	    	  refresh();
			         	    	  
			         	    	  ls.addMessage(userId, "�˻������޸��˻�");
						       }else{
				         	      JOptionPane.showMessageDialog(null, "�޸�ʧ��"); 
						       }
		    		}
	            	   

				       
		    		 
	            }

		    }
		    
		});
		
		fixAccountPanel.add(bpanel);

		
	}
	
	private static void createSeekAccountPanel(){
		seekAccountPanel=new JPanel();
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(25,0,25,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		
		JLabel account=new JLabel("�˻���");
		JTextField accountfield=new JTextField(10);
		BaseUI.myAdd(bpanel,account,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,accountfield,constraints,1,0,1,1);
		
		JButton submit=new JButton("��ѯ�˻���Ϣ");
		BaseUI.myAdd(bpanel,submit,constraints,0,5,2,1);
		submit.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
	            if(submit.getText().equals("��ѯ�˻���Ϣ")){
	              	if(accountfield.getText().equals("")){
	        	    	  JOptionPane.showMessageDialog(null, "�������˻���"); 
	              	}else{
	              		
	    		    	submit.setText("ȷ��");

	    		    	
	    		    	AccountVO vo=ams.findAccount(accountfield.getText());
	    		    	bpanel.remove(account);
	    		    	bpanel.remove(accountfield);
	    		    	
	    				JLabel name=new JLabel("�˻�����:");
	    				JLabel balance=new JLabel("�˻���");

	    				JLabel namefield=new JLabel(vo.getName());
	    				JLabel balancefield=new JLabel(vo.getBalance()+"");
	    				
	    				BaseUI.myAdd(bpanel,name,constraints,0,0,1,1);
	    				BaseUI.myAdd(bpanel,balance,constraints,0,1,1,1);
	    				BaseUI.myAdd(bpanel,namefield,constraints,1,0,1,1);
	    				BaseUI.myAdd(bpanel,balancefield,constraints,1,1,1,1);  
	              	}
	            }else{
	            	tab.remove(FreeUtil.getPagePane(seekAccountPanel));
	            	
	            	refresh();
	            	
       	    	    ls.addMessage(userId, "�˻�������ѯ�˻�");
	            }
          
		    }
		});
		
		seekAccountPanel.add(bpanel);
	}
	
    private static FreeReportPage createReportPage() {
        model = new DefaultTableModel();
        model.addColumn("�˻�����");
        model.addColumn("�˻����");
        
   
        ArrayList<AccountVO> list=new ArrayList<AccountVO>();
        ams=new AccountManagementController();
        list=ams.getAllAccount();
        for(int i=0;i<list.size();i++){
            Vector row = new Vector();
            row.add(list.get(i).getName());
            row.add(list.get(i).getBalance()+"");
            model.addRow(row);
        }
        
        
//        for (int i = 0; i < 100; i++) {
//            Vector row = new Vector();
//            row.add("����һ");
//            row.add("100000");
//
//            model.addRow(row);
//        }

        FreeReportPage page = new FreeReportPage();
        page.getTable().setModel(model);
        page.setDescription("All Work Order Items by Part Number. Created " + new Date().toString());
        setupPageToolbar(page);

        return page;
    }

  public static void setupPageToolbar(FreePagePane page) {
	  FreeToolbarButton addAccount,deleteAccount,fixAccount,seekAccount;
	  addAccount=createButton("/free/test/add.png", "�����˻�", true);
      deleteAccount=createButton("/free/test/update.png", "ɾ���˻�", true);
      fixAccount=createButton("/free/test/refresh.png", "�޸��˻�", true);
      seekAccount=createButton("/free/test/print.png", "�����˻�", true);
      page.getRightToolBar().add(addAccount);
      page.getRightToolBar().add(deleteAccount);
      page.getRightToolBar().add(fixAccount);
      page.getRightToolBar().add(seekAccount);
      
		addAccount.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=addAccount.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(addAccountPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createAddAccountPanel();
             	    tab.addTab(title, FinanceUI.createPage(addAccountPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(addAccountPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		
		deleteAccount.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=deleteAccount.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(deleteAccountPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createDeleteAccountPanel();
             	    tab.addTab(title, FinanceUI.createPage(deleteAccountPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(deleteAccountPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		
		fixAccount.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=fixAccount.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(fixAccountPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createFixAccountPanel();
             	    tab.addTab(title, FinanceUI.createPage(fixAccountPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(fixAccountPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		seekAccount.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=seekAccount.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(seekAccountPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createSeekAccountPanel();
             	    tab.addTab(title, FinanceUI.createPage(seekAccountPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(seekAccountPanel);
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
  
  public  static void refresh(){
	  while(model.getRowCount()>0){
		  model.removeRow(0);
	  }
	  
      ArrayList<AccountVO> list=new ArrayList<AccountVO>();
      ams=new AccountManagementController();
      list=ams.getAllAccount();
      for(int i=0;i<list.size();i++){
          Vector row = new Vector();
          row.add(list.get(i).getName());
          row.add(list.get(i).getBalance()+"");
          model.addRow(row);
      }
      
	  
	  
  }
}
