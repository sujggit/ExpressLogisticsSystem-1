package financeui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TabbedPaneUI;

import main.FreeLoginUI;
import main.StaffInfoPanel;
import main.StaffUI;
import managerui.LogPanel;
import managerui.ReportPanel;
import free.FreeContentPane;
import free.FreeGarbageCollectButton;
import free.FreeMemoryBar;
import free.FreeMenuBar;
import free.FreeOutlookPane;
import free.FreePagePane;
import free.FreeStatusBar;
import free.FreeStatusLabel;
import free.FreeStatusMessageLabel;
import free.FreeStatusTimeLabel;
import free.FreeTabbedPane;
import free.FreeUtil;
import twaver.TWaverUtil;

public class FinanceUI extends StaffUI {
    private String menuBarXML = "/free/menubar.xml";
    private String toolbarXML = "/free/toolbar.xml";
    private String outlookPaneXML = "/financeui/financeoutlook.xml";
    private static String userId;
    private ActionListener defaultAction = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            command(command);
        }
    };
    private String productName = "�������ϵͳ";
    private String companyName = "33";
    private FreeMenuBar menubar = FreeUtil.loadMenuBar(menuBarXML, defaultAction);
    private FreeContentPane contentPane = new FreeContentPane();
    private FreeStatusBar statusBar = new FreeStatusBar();
    private FreeMemoryBar memoryBar = new FreeMemoryBar();
    private FreeStatusMessageLabel lbStatusMessage = new FreeStatusMessageLabel();
    private FreeStatusTimeLabel lbStatusTime = new FreeStatusTimeLabel();
    private FreeStatusLabel lbServer = createStatusLabel("�����IP��δʵ�֣�", "/free/test/server.png");
    private FreeStatusLabel lbUser = createStatusLabel("������Ա", "/free/test/user.png");
    private FreeStatusLabel lbVersion = createStatusLabel("v1.0.0", null);
    private FreeOutlookPane outlookPane = new FreeOutlookPane();
    private FreeTabbedPane tab = new FreeTabbedPane();
    private static FreePagePane accountManagementPage=null;
    private static FreePagePane balancePage=null;
    private static FreePagePane costPage=null;
    private static FreePagePane userInfoPage=null;
	private static FreePagePane logPage=null;
	private static FreePagePane reportPage=null;
	private static FreePagePane companyaccountPagePane=null;

    public FinanceUI(String Id) {
        this.userId=Id;
        initSwing();
        initOutlookPane();
        initTab();
        initStatusBar();
        initMockers();
    }

    private void initSwing() {
        this.setTitle(productName + " ����by " + companyName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 768);
        this.setIconImage(TWaverUtil.getImage("/free/test/logo.png"));

        this.setContentPane(contentPane);
        TWaverUtil.centerWindow(this);
        contentPane.add(menubar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);

        JPanel centerPane = new JPanel(new BorderLayout());
        centerPane.setOpaque(true);
        centerPane.setBackground(FreeUtil.CONTENT_PANE_BACKGROUND);
        centerPane.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
     //   centerPane.add(shortcutPane, BorderLayout.EAST);
        centerPane.add(outlookPane, BorderLayout.WEST);
        contentPane.add(centerPane, BorderLayout.CENTER);
        centerPane.add(tab, BorderLayout.CENTER);
        lbStatusMessage.setText("Server is connected");
    }

    private void initOutlookPane() {
    	//   FreeUtil.loadOutlookToolBar(toolbarXML, outlookPane.getHeader(), defaultAction);
         MouseListener barActionListener = new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                Object source = e.getSource();
                if(e.getSource() instanceof JLabel){

                	JLabel label=(JLabel) source;
//                	FreeOutlookBar bar=(FreeOutlookBar) label.getParent();
//                	bar.setSelected(true);
                	String title=label.getText();
                	
                	if(title.equals("�˻�����")){
                		
                        try{
                            tab.setSelectedComponent(accountManagementPage);

                           }catch(Exception ex){
                        	   
                        	   accountManagementPage=AccountManagementPanel.createAccountManagementPage(tab,userId);
                        	   if(accountManagementPage!=null){
                                   tab.add(title, accountManagementPage);
                                   tab.setSelectedComponent(accountManagementPage);   
                        	   }
                           }


                	}else if(title.equals("�������")){
                        try{
                            tab.setSelectedComponent(balancePage);

                           }catch(Exception ex){
                        	   balancePage=BalancePanel.createBalancePage(tab);
                               tab.add(title, balancePage);
                               tab.setSelectedComponent(balancePage);
                           }
 
                	}else if(title.equals("�ɱ�����")){

                        try{
                            tab.setSelectedComponent(costPage);

                           }catch(Exception ex){
                        	   costPage=createPage(CostPanel.createCostPanel(userId));
                               tab.add(title, costPage);
                               tab.setSelectedComponent(costPage);
                           }
                		
                	}else if(title.equals("������Ϣ")){
                        try{
                            tab.setSelectedComponent(userInfoPage);

                           }catch(Exception ex){
                        	   userInfoPage=StaffInfoPanel.createStaffInfoPage(tab,userId);
                               tab.add(title, userInfoPage);
                               tab.setSelectedComponent(userInfoPage);
                           }
                	}else if(title.equals("ͳ�Ʊ���")){
						try{
							tab.setSelectedComponent(reportPage);

						}catch(Exception ex){
							reportPage = ReportPanel.createReportPage(tab,userId);
							tab.add(title, reportPage);
							tab.setSelectedComponent(reportPage);
						}

					}else if(title.equals("��־�鿴")){
						try{
							tab.setSelectedComponent(logPage);

						}catch(Exception ex){
							logPage = LogPanel.createLogPage(tab,userId);
							tab.add(title, logPage);
							tab.setSelectedComponent(logPage);
						}

					}else if(title.equals("��Ŀ��ʼ��")){
						try{
							tab.setSelectedComponent(companyaccountPagePane);

						}catch(Exception ex){
							companyaccountPagePane = CompanyAccountPanel.createAccountManagementPage(tab, userId);
							tab.add(title, companyaccountPagePane);
							tab.setSelectedComponent(companyaccountPagePane);
						}

					}


                    
                }
            }
        };

           ActionListener shortcutAction = defaultAction;
          FreeUtil.loadOutlookPane(outlookPaneXML, outlookPane,barActionListener, shortcutAction);
    }
    

	


		
	


    private void initTab() {
        //double click tab title, shink left/right side bars.
        tab.addMouseListener(new MouseAdapter() {

            private boolean isMaximized() {
                return outlookPane.isShrinked();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    TabbedPaneUI ui = tab.getUI();
                    int tabIndex = ui.tabForCoordinate(tab, e.getX(), e.getY());
                    //only when tab header is clicked.
                    if (tabIndex != -1) {
                        boolean maxed = isMaximized();
                        outlookPane.setShrink(!maxed);
                    }
                }
            }
        });
  	   userInfoPage=StaffInfoPanel.createStaffInfoPage(tab,userId);
       tab.add("������Ϣ", userInfoPage);
       tab.setSelectedComponent(userInfoPage);
    }


    private void initStatusBar() {
        statusBar.getLeftPane().add(lbStatusMessage, BorderLayout.CENTER);
        statusBar.addSeparator();
        statusBar.getRightPane().add(memoryBar);
        statusBar.addSeparator();
        statusBar.getRightPane().add(new FreeGarbageCollectButton());
        statusBar.addSeparator();
        statusBar.getRightPane().add(lbServer);
        statusBar.addSeparator();
        statusBar.getRightPane().add(lbUser);
        statusBar.addSeparator();
        statusBar.getRightPane().add(lbStatusTime);
        statusBar.addSeparator();
        statusBar.getRightPane().add(lbVersion);
        statusBar.addSeparator();
        statusBar.getRightPane().add(createStatusLabel("Powered by " + productName, null));
    }

    public void setServer(String server) {
        lbServer.setText(server);
    }

    public void setUser(String username) {
        lbUser.setText(username);
    }

    public void setVersion(String version) {
        lbVersion.setText(version);
    }

    private void initMockers() {

        Thread thread = new Thread() {

            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(5000);
                            if (i == 0) {
                                lbStatusMessage.setGreenLight();
                                lbStatusMessage.setText("Server is connected");
                            }
                            if (i == 1) {
                                lbStatusMessage.setOrangeLight();
                                lbStatusMessage.setText("Server connection is slow");
                            }
                            if (i == 2) {
                                lbStatusMessage.setRedLight();
                                lbStatusMessage.setText("Server connection is broken");
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    public static FreePagePane createPage(JComponent pageContent) {
        FreePagePane page = new FreePagePane(pageContent);
//        setupPageToolbar(page);
        return page;
    }



    private FreeStatusLabel createStatusLabel(String text, String imageURL) {
        if (imageURL != null) {
            return new FreeStatusLabel(text, TWaverUtil.getIcon(imageURL));
        } else {
            return new FreeStatusLabel(text);
        }
    }

    public void command(String action) {
        String message = "Perform action " + action + ".";
        this.lbStatusMessage.setText(message);
        if(action.equals("�ǳ�")){
        	dispose();
            FreeLoginUI ui = new FreeLoginUI();
            ui.setVisible(true);
        }
    }


}
