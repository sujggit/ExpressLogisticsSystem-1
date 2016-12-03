package financeui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import twaver.TWaverUtil;
import usersl.LogManagementController;
import usersl.UserManagementController;
import userslservice.LogService;
import userslservice.UserService;
import vo.AccountVO;
import vo.AgencyVO;
import vo.CarVO;
import vo.CompanyAccountVO;
import vo.StaffVO;
import vo.UserVO;
import enums.ResultMessage;
import enums.Sex;
import enums.Work;
import financesl.AccountInitializeController;
import financesl.AccountManagementController;
import financeslservice.AccountInitializeService;
import financeslservice.AccountManagementService;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;

public class CompanyAccountPanel {
	private static JPanel addAgencyPanel,addStaffPanel,addCarPanel,addAccountPanel;
	public static JTabbedPane tab;
	private static AccountInitializeService ais;
	private static AccountManagementService ams;
	private static String userId;
	private static  LogService ls;
	private static  DefaultTableModel model;
	private static CompanyAccountVO companyAccountVO;
	private static MaskFormatter maskWorkPlaceNumber;
	private static MaskFormatter maskPhoneNumber;
	private static MaskFormatter maskLeader;
	private static MaskFormatter maskStaffNumber;
	private static MaskFormatter maskWorkNumber;
	private static MaskFormatter maskIDNumber;
    private static FreeReportPage page = new FreeReportPage();
	
	  
	
	
	

	
	public static FreeReportPage  createAccountManagementPage(JTabbedPane _tab,String Id){

	    tab=_tab;
	    userId=Id;
	    try {
			initMask();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
        ais=new AccountInitializeController();
	    ls=new LogManagementController();
	    companyAccountVO=new CompanyAccountVO();

		
		return createReportPage();
	}
	
	private static void initMask() throws ParseException {
		// TODO Auto-generated method stub
		maskWorkPlaceNumber=new MaskFormatter("######");
		maskWorkPlaceNumber.setPlaceholderCharacter('0');
		maskPhoneNumber=new MaskFormatter("###########");
		maskPhoneNumber.setPlaceholderCharacter('0');
		maskStaffNumber=new MaskFormatter("#########");
		maskStaffNumber.setPlaceholderCharacter('0');
		maskWorkNumber=new MaskFormatter("#########");
		maskWorkNumber.setPlaceholderCharacter('0');
		maskIDNumber=new MaskFormatter("##################");
		maskIDNumber.setPlaceholderCharacter('0');
		
		
	}
	
	private static void createAddAgencyPanel(){
		addAgencyPanel=new FreePagePane();
		JLabel idNumber=new JLabel("�������");
		JLabel name=new JLabel("������");
		JLabel phoneNumber=new JLabel("�绰����");
		JLabel address=new JLabel("��ַ");
		JLabel leader=new JLabel("������");
		JLabel staff=new JLabel("Ա�����");
		JTextField nameField=new JTextField(20);
		JFormattedTextField idNumberField = new JFormattedTextField(maskWorkPlaceNumber);
		idNumberField.setFocusLostBehavior(JFormattedTextField.COMMIT);
		//idField.setInputVerifier(new FormattedTextFieldVerifier());		
		JFormattedTextField phoneNumberField=new JFormattedTextField(maskPhoneNumber);
		phoneNumberField.setFocusLostBehavior(JFormattedTextField.COMMIT);
		JTextField addressField=new JTextField(20);
		JTextField leaderField=new JTextField(20);
		JTextArea staffField=new JTextArea(3, 20);
		staffField.setLineWrap(true);
		JScrollPane scrollPane=new JScrollPane(staffField);
		scrollPane.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scrollPane.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(20,0,5,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		BaseUI.myAdd(bpanel,idNumber,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,name,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,phoneNumber,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,address,constraints,0,3,1,1);
		BaseUI.myAdd(bpanel,leader,constraints,0,4,1,1);
		BaseUI.myAdd(bpanel, staff, constraints, 0, 5, 1, 1);


		BaseUI.myAdd(bpanel,idNumberField,constraints,1,0,1,1);
		BaseUI.myAdd(bpanel,nameField,constraints,1,1,1,1);
		BaseUI.myAdd(bpanel,phoneNumberField,constraints,1,2,1,1);
		BaseUI.myAdd(bpanel,addressField,constraints,1,3,1,1);
		BaseUI.myAdd(bpanel,leaderField,constraints,1,4,1,1);
		BaseUI.myAdd(bpanel, scrollPane, constraints, 1, 5, 1, 1);

		//		JButton addStaff=new JButton("�����Ա");
		//		//addOrder.addActionListener(arg0);
		//		addStaff.addMouseListener(new MouseAdapter(){
		//		    @Override
		//		    public void mouseClicked(MouseEvent arg0) 
		//		    {   
		//		    	String title="�����Ա";                
		//                try{
		//                 FreePagePane pagePane = FreeUtil.getPagePane(addStaffPanel);
		//                 tab.setSelectedComponent(pagePane);
		//                }catch(Exception ex){
		//                    createAddStaffPanel();
		//             	    tab.addTab(title, ManagerUI.createPage(addStaffPanel));
		//                    FreePagePane pagePane = FreeUtil.getPagePane(addStaffPanel);
		//                    tab.setSelectedComponent(pagePane);
		//                }
		//
		//             
		//		    }
		//		});
		JButton submit=new JButton("�ύ");
		//		BaseUI.myAdd(bpanel, addStaff, constraints, 0, 6, 2, 1);
		BaseUI.myAdd(bpanel,submit,constraints,0,6,2,1);
		submit.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(nameField.getText().equals("")||addressField.getText().equals("")||leaderField.getText().equals("")||staffField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "��Ϣ��д��������");
					return;
				}
				
				AgencyVO agencyVO=new AgencyVO();

                try {
					
			
				agencyVO.setIDNumber(idNumberField.getText());
				agencyVO.setAddress(addressField.getText());
				agencyVO.setName(nameField.getText());
				agencyVO.setPhoneNumber(phoneNumberField.getText());
				agencyVO.setLeader(leaderField.getText());
				ArrayList<String>staff=new ArrayList<String>();
				String[]strings=staffField.getText().split("\n");

				for(String string:strings)
					staff.add(string);

				agencyVO.setStaff(staff);
				
				JOptionPane.showMessageDialog(null, "��Ӳ����ɹ�");
				companyAccountVO.getAgencyList().add(agencyVO);
				
				tab.remove(FreeUtil.getPagePane(addAgencyPanel));
				
				refresh();
            	} catch (Exception e) {
					// TODO: handle exception
            		JOptionPane.showMessageDialog(null, "��Ϣ��д��������");
				}





			}
		});

		addAgencyPanel.add(bpanel);
	}
	
	private static void createAddStaffPanel(){
		addStaffPanel=new FreePagePane();
		JLabel workNumber=new JLabel("�������");
		JLabel name=new JLabel("����");
		JLabel work=new JLabel("ְλ");
		JLabel workPlaceNumber=new JLabel("������λ���");
		JLabel idNumber=new JLabel("���֤��");
		JLabel phoneNumber=new JLabel("�ֻ�����");
		JLabel address=new JLabel("��ס��ַ");
		JLabel sex=new JLabel("�Ա�");
		JLabel page=new JLabel("����");
		JFormattedTextField workNumberField=new JFormattedTextField(maskWorkNumber);
		workNumberField.setFocusLostBehavior(JFormattedTextField.COMMIT);		
		JTextField nameField=new JTextField(20);
		JComboBox workCombo=new JComboBox();
		workCombo.addItem(Work.Courier);
		workCombo.addItem(Work.Officer);
		workCombo.addItem(Work.Finance);
		workCombo.addItem(Work.Manager);
		workCombo.addItem(Work.TransOffice);
		workCombo.addItem(Work.Stock);
		workCombo.addItem(Work.Admin);
		workCombo.addItem(Work.Driver);
		//JTextField workPlaceNumberField=new JTextField(6);
		JFormattedTextField workPlaceNumberField=new JFormattedTextField(maskWorkPlaceNumber);
		workPlaceNumberField.setFocusLostBehavior(JFormattedTextField.COMMIT);

		//JTextField idNumberField=new JTextField(23);
		JFormattedTextField idNumberField=new JFormattedTextField(maskIDNumber);
		idNumberField.setFocusLostBehavior(JFormattedTextField.COMMIT);

		//JTextField phoneField=new JTextField(11);
		JFormattedTextField phoneField=new JFormattedTextField(maskPhoneNumber);
		phoneField.setFocusLostBehavior(JFormattedTextField.COMMIT);

		JTextField addressField=new JTextField(20);
		JComboBox sexCombo=new JComboBox();
		sexCombo.addItem(Sex.male);
		sexCombo.addItem(Sex.female);
		JTextField pageField=new JTextField(5);

		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(15,0,5,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		BaseUI.myAdd(bpanel,workNumber,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,name,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,work,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,workPlaceNumber,constraints,0,3,1,1);
		BaseUI.myAdd(bpanel,idNumber,constraints,0,4,1,1);
		BaseUI.myAdd(bpanel,phoneNumber,constraints,0,5,1,1);
		BaseUI.myAdd(bpanel,address,constraints,0,6,1,1);
		BaseUI.myAdd(bpanel,sex,constraints,0,7,1,1);
		BaseUI.myAdd(bpanel,page,constraints,0,8,1,1);


		BaseUI.myAdd(bpanel,workNumberField,constraints,1,0,1,1);
		BaseUI.myAdd(bpanel,nameField,constraints,1,1,1,1);
		BaseUI.myAdd(bpanel,workCombo,constraints,1,2,1,1);
		BaseUI.myAdd(bpanel,workPlaceNumberField,constraints,1,3,1,1);
		BaseUI.myAdd(bpanel,idNumberField,constraints,1,4,1,1);
		BaseUI.myAdd(bpanel,phoneField,constraints,1,5,1,1);
		BaseUI.myAdd(bpanel,addressField,constraints,1,6,1,1);
		BaseUI.myAdd(bpanel,sexCombo,constraints,1,7,1,1);
		BaseUI.myAdd(bpanel,pageField,constraints,1,8,1,1);


		JButton submit=new JButton("�ύ");
		submit.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				ResultMessage resultMessage=null;
				StaffVO staffVO=new StaffVO();
				if(nameField.getText().equals("")||addressField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "��д��Ϣ��������");
					return  ;
				}
				
				
				try{
				staffVO.setWorkNumber(workNumberField.getText());
				staffVO.setName(nameField.getText());
				staffVO.setWork((Work) workCombo.getSelectedItem());
				staffVO.setWorkPlaceNumber(workPlaceNumberField.getText());
				staffVO.setIdNumber(idNumberField.getText());
				staffVO.setPhoneNumber(phoneField.getText());
				staffVO.setAddress(addressField.getText());
				staffVO.setSex((Sex) sexCombo.getSelectedItem());
				staffVO.setPage(Double.valueOf(pageField.getText()));
				

				JOptionPane.showMessageDialog(null, "��Ӳ����ɹ�");
				tab.remove(FreeUtil.getPagePane(addStaffPanel));
				
				companyAccountVO.getStafflist().add(staffVO);
				refresh();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "��д��Ϣ��������");
					return;
				}
           



			}
		});
		BaseUI.myAdd(bpanel,submit,constraints,1,9,1,1);
		addStaffPanel.add(bpanel);
	}
	
	private static void createAddCarPanel() {
		addCarPanel = new JPanel();
		JLabel vehicleMark = new JLabel("��������");
		JLabel vehicleID = new JLabel("�������");
		JLabel engineID = new JLabel("��������");
		JLabel hallID = new JLabel("������λ");
		JLabel timeOfBuy = new JLabel("����ʱ��");
		JLabel workingTime = new JLabel("����ʱ��");
		JLabel picture = new JLabel("����ͼƬ");
		JTextField vehicleMarkfield = new JTextField(10);
		JTextField vehicleIDfield = new JTextField(10);
		JTextField engineIDfield = new JTextField(10);
		JTextField hallIDfield = new JTextField(10);
		JTextField timeOfBuyfield = new JTextField(10);
		JTextField workingTimefield = new JTextField(10);

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		BaseUI.myAdd(bpanel, vehicleMark, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, vehicleID, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, engineID, constraints, 0, 2, 1, 1);
		BaseUI.myAdd(bpanel, hallID, constraints, 0, 3, 1, 1);
		BaseUI.myAdd(bpanel, timeOfBuy, constraints, 0, 4, 1, 1);
		BaseUI.myAdd(bpanel, workingTime, constraints, 0, 5, 1, 1);
		BaseUI.myAdd(bpanel, picture, constraints, 0, 6, 1, 1);

		BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 1, 1, 1);
		BaseUI.myAdd(bpanel, engineIDfield, constraints, 1, 2, 1, 1);
		BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 3, 1, 1);
		BaseUI.myAdd(bpanel, timeOfBuyfield, constraints, 1, 4, 1, 1);
		BaseUI.myAdd(bpanel, workingTimefield, constraints, 1, 5, 1, 1);

		JButton submit = new JButton("�ύ");

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
				 CarVO	carvo = new CarVO(vehicleMarkfield.getText(), hallIDfield
							.getText(), vehicleIDfield.getText(), Integer
							.parseInt(workingTimefield.getText()));
				 companyAccountVO.getCarlist().add(carvo);
				 
		         	JOptionPane.showMessageDialog(null, "��ӳɹ�");
		            tab.remove(FreeUtil.getPagePane(addCarPanel));
		            refresh();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		/***********************************************/
		BaseUI.myAdd(bpanel, submit, constraints, 0, 7, 2, 1);

		addCarPanel.add(bpanel);
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
		    	
			    		try {

			    			double _balance=Double.parseDouble(balancefield.getText());
			    			companyAccountVO.getAccountlist().add(new AccountVO(namefield.getText(), _balance));
				         	JOptionPane.showMessageDialog(null, "�����ɹ�"); 
                            refresh();
                            
                            FreePagePane pagePane=FreeUtil.getPagePane(addAccountPanel);
                            tab.remove(pagePane);
			    		} catch (Exception e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
				         	JOptionPane.showMessageDialog(null, "��������ȷ���"); 
			    		}
		    		

		    		
		    		

		    	}else{
       	    	  JOptionPane.showMessageDialog(null, "�����������˻���Ϣ"); 
		    	}
		    }
		});    
		
		
		addAccountPanel.add(bpanel);
	}
	
	
	

	
    private static FreeReportPage createReportPage() {
        model = new DefaultTableModel();
        model.addColumn("�ڳ���Ŀ��Ϣ");
        
        
        Vector agencyrow = new Vector();
        agencyrow.add("������Ϣ������/�������/�绰/�쵼/��ַ��");
        model.addRow(agencyrow);
        for(int i=0;i<companyAccountVO.getAgencyList().size();i++){
        	AgencyVO agencyVO=companyAccountVO.getAgencyList().get(i);
            Vector row = new Vector();
            row.add(agencyVO.getName()+"/"+agencyVO.getIdNumber()+"/"+agencyVO.getPhoneNumber()+"/"+agencyVO.getLeader()+"/"+agencyVO.getAddress());
            model.addRow(row);
        }
        
        Vector staffrow=new Vector();
        staffrow.add("Ա����Ϣ������/�Ա�/����/��ַ/����/���֤��/�绰����/����/�����ص���/���ʣ�");
        model.addRow(staffrow);  
        for(int i=0;i<companyAccountVO.getStafflist().size();i++){
        	StaffVO staffVO=companyAccountVO.getStafflist().get(i);
        	Vector row = new Vector();
        	row.add(staffVO.getName()+"/"+staffVO.getSex()+"/"+staffVO.getWork()+"/"+staffVO.getAddress()+"/"+staffVO.getBirthDate()+"/"+staffVO.getIdNumber()+"/"
        			+staffVO.getPhoneNumber()+"/"+staffVO.getWorkNumber()+"/"+staffVO.getWorkPlaceNumber()+"/"+staffVO.getPage());
        	model.addRow(row);
        }
        
        
        Vector carrow=new Vector();
        carrow.add("������Ϣ���������/���պ���/�����ص���/���䣩");
        model.addRow(carrow);
        for(int i=0;i<companyAccountVO.getCarlist().size();i++){
        	CarVO carVO=companyAccountVO.getCarlist().get(i);
        	Vector row = new Vector();
        	
        	row.add(carVO.getIDNumber()+"/"+carVO.getLicenseNumber()+"/"+carVO.getWorkPlaceNumber()+"/"+carVO.getWorkYear());
        	model.addRow(row);
        }
        
        
       Vector accountrow=new Vector();
       accountrow.add("�˻���Ϣ���˻�����/�˻���");
       model.addRow(accountrow);
       for(int i=0;i<companyAccountVO.getAccountlist().size();i++){
    	   AccountVO accountVO=companyAccountVO.getAccountlist().get(i);
    	   Vector row=new Vector<>();
    	   row.add(accountVO.getName()+"/"+accountVO.getBalance());
    	   model.addRow(row);
       }


        page.getTable().setModel(model);
        page.setDescription("All Work Order Items by Part Number. Created " + new Date().toString());
        setupPageToolbar(page);

        return page;
    }

  public static void setupPageToolbar(FreePagePane page) {
	  FreeToolbarButton addAccount,addAgency,addStaff,addCar;
	  addAccount=createButton("/free/test/add.png", "�����˻�", true);
      addAgency=createButton("/free/test/add.png", "���ӻ���", true);
      addStaff=createButton("/free/test/add.png", "������Ա", true);
      addCar=createButton("/free/test/add.png", "���ӳ���", true);
      page.getRightToolBar().add(addAgency);
      page.getRightToolBar().add(addStaff);
      page.getRightToolBar().add(addCar);
      page.getRightToolBar().add(addAccount);
      
      FreeToolbarButton submitButton,checkButton;
      submitButton=createButton("/free/test/add.png", "ȷ���½�", true);
      checkButton=createButton("/free/test/print.png", "��ѯ������Ŀ", true);
      page.getLeftToolBar().add(submitButton);
      page.getLeftToolBar().add(checkButton);
      
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
		

		
		
		addAgency.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=addAgency.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(addAgencyPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createAddAgencyPanel();
             	    tab.addTab(title, FinanceUI.createPage(addAgencyPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(addAgencyPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
//		
//		
		addStaff.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=addStaff.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(addStaffPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createAddStaffPanel();;
             	    tab.addTab(title, FinanceUI.createPage(addStaffPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(addStaffPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		addCar.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	String title=addCar.getToolTipText();                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(addCarPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createAddCarPanel();
             	    tab.addTab(title, FinanceUI.createPage(addCarPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(addCarPanel);
                    tab.setSelectedComponent(pagePane);
                }

             
		    }
		});
		
		
		submitButton.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	  int i=JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��������Ϣ�Ѿ����룿��","��ʾ:", JOptionPane.YES_NO_OPTION);
		    	  if(i==JOptionPane.OK_OPTION){
		    	   Calendar a=Calendar.getInstance();
		    	   companyAccountVO.setYear(a.get(a.YEAR));
		    	   System.out.println(a.get(a.YEAR));
		    	   try{
			    	   ais.initialize(companyAccountVO);
		      	       JOptionPane.showMessageDialog(null, "�½��ɹ������ù���Ա�����û���ʼ��"); 
			    	   tab.remove(page);
		    	   }catch(Exception e){
		    		   System.out.println("����ʧ�ܣ�������Ŀ�Ѵ���");
		    	   }
		    	  }
		    }
		});    
		
		checkButton.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    { 
		    	String temp=JOptionPane.showInputDialog("��������Ҫ��ѯ�ڳ���Ŀ�����");
		    	int year=Integer.parseInt(temp);
		    	companyAccountVO=ais.find(year);
		    	refresh();
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
	  
      Vector agencyrow = new Vector();
      agencyrow.add("������Ϣ������/�������/�绰/�쵼/��ַ��");
      model.addRow(agencyrow);
      for(int i=0;i<companyAccountVO.getAgencyList().size();i++){
      	AgencyVO agencyVO=companyAccountVO.getAgencyList().get(i);
          Vector row = new Vector();
          row.add(agencyVO.getName()+"/"+agencyVO.getIdNumber()+"/"+agencyVO.getPhoneNumber()+"/"+agencyVO.getLeader()+"/"+agencyVO.getAddress());
          model.addRow(row);
      }
      
      Vector staffrow=new Vector();
      staffrow.add("Ա����Ϣ������/�Ա�/����/��ַ/����/���֤��/�绰����/����/�����ص���/���ʣ�");
      model.addRow(staffrow);  
      for(int i=0;i<companyAccountVO.getStafflist().size();i++){
      	StaffVO staffVO=companyAccountVO.getStafflist().get(i);
      	Vector row = new Vector();
      	row.add(staffVO.getName()+"/"+staffVO.getSex()+"/"+staffVO.getWork()+"/"+staffVO.getAddress()+"/"+staffVO.getBirthDate()+"/"+staffVO.getIdNumber()+"/"
      			+staffVO.getPhoneNumber()+"/"+staffVO.getWorkNumber()+"/"+staffVO.getWorkPlaceNumber()+"/"+staffVO.getPage());
      	model.addRow(row);
      }
      
      
      Vector carrow=new Vector();
      carrow.add("������Ϣ���������/���պ���/�����ص���/���䣩");
      model.addRow(carrow);
      for(int i=0;i<companyAccountVO.getCarlist().size();i++){
      	CarVO carVO=companyAccountVO.getCarlist().get(i);
      	Vector row = new Vector();
      	
      	row.add(carVO.getIDNumber()+"/"+carVO.getLicenseNumber()+"/"+carVO.getWorkPlaceNumber()+"/"+carVO.getWorkYear());
      	model.addRow(row);
      }
      
      
     Vector accountrow=new Vector();
     accountrow.add("�˻���Ϣ���˻�����/�˻���");
     model.addRow(accountrow);
     for(int i=0;i<companyAccountVO.getAccountlist().size();i++){
  	   AccountVO accountVO=companyAccountVO.getAccountlist().get(i);
  	   Vector row=new Vector<>();
  	   row.add(accountVO.getName()+"/"+accountVO.getBalance());
  	   model.addRow(row);
     }
      
	  
	  
  }
}
