package stockui;

import ioputsl.IoputCal;
import ioputsl.IoputController;
import ioputslservice.IoputService;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TabbedPaneUI;

import main.StaffInfoPanel;
import main.StaffUI;
import dataserviceimpl.DataFactory;
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
import stocksl.StockController;
import stockslservice.StockService;
import twaver.TWaverUtil;

public class StockUI extends StaffUI {

    private String menuBarXML = "/free/menubar.xml";
    private String toolbarXML = "/free/toolbar.xml";
    private String outlookPaneXML = "/stockui/StockManagement.xml";
    private String userId;
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
    private FreeStatusLabel lbServer = createStatusLabel("218.83.152.220", "/free/test/server.png");
    private FreeStatusLabel lbUser = createStatusLabel("stock", "/free/test/user.png");
    private FreeStatusLabel lbVersion = createStatusLabel("v3.0.0", null);
    private FreeOutlookPane outlookPane = new FreeOutlookPane();
    private FreeTabbedPane tab = new FreeTabbedPane();
    private boolean isUserManagementExist=false;
    private static FreePagePane StaffInfoPage = null;
    private static FreePagePane OutputStockPage=null;
    private static FreePagePane InputStockPage=null;
    private static FreePagePane StockAdjustPage=null;
    private static FreePagePane StockCheckPage=null;
    private static FreePagePane StockInfoPage=null;
    private static FreePagePane StockInitializePage=null;
    private static IoputService ioput=null;
    private static StockService stock=null;
    

    public StockUI(String userId) {
    	this.userId=userId;
    	try {
    		StockController s = new StockController(DataFactory.create(),new IoputCal());
    		stock = s;
			ioput = new IoputController(DataFactory.create(),s);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
                	
                	if(title.equals("�������")){
                		if(stock.StockExist()){
                		
                        try{
                            tab.setSelectedComponent(OutputStockPage);

                           }catch(Exception ex){
                        	   OutputStockPage=OutputStockPanel.createOutputManagementPage(tab,userId,ioput);
                               tab.addTab(title, OutputStockPage);
                               tab.setSelectedComponent(OutputStockPage);
                           }}
                		else
                			JOptionPane.showMessageDialog(null, "���ȳ�ʼ���������");


                	}else if(title.equals("������")){
                		if(stock.StockExist()){
                		  try{
                              tab.setSelectedComponent(InputStockPage);

                             }catch(Exception ex){
                          	   InputStockPage=InputStockPanel.createInputManagementPage(tab,userId,ioput);
                                 tab.addTab(title, InputStockPage);
                                 tab.setSelectedComponent(InputStockPage);
                             }}
                		else
                			JOptionPane.showMessageDialog(null, "���ȳ�ʼ���������");

                	}else if(title.equals("������Ϣ")){
                		
              		  try{
                            tab.setSelectedComponent(StaffInfoPage);

                           }catch(Exception ex){
                        	   StaffInfoPage=StaffInfoPanel.createStaffInfoPage(tab, userId);
                               tab.addTab(title, StaffInfoPage);
                               tab.setSelectedComponent(StaffInfoPage);
                           }

              	}else if(title.equals("���鿴")){
              		if(stock.StockExist()){
              		  try{
                          tab.setSelectedComponent(StockInfoPage);

                         }catch(Exception ex){
                        	 StockInfoPage=StockInfoPanel.createStockInfoPage(tab,stock);
                             tab.addTab(title, StockInfoPage);
                             tab.setSelectedComponent(StockInfoPage);
                         }
              		}else JOptionPane.showMessageDialog(null, "���ȳ�ʼ���������");
          	
                	}else if(title.equals("����̵�")){
                  		if(stock.StockExist()){
              		  try{
                          tab.setSelectedComponent(StockCheckPage);

                         }catch(Exception ex){
                      	   StockCheckPage=StockCheckPanel.createStockCheckPage(tab,stock);
                             tab.addTab(title, StockCheckPage);
                             tab.setSelectedComponent(StockCheckPage);
                         }                         }
              		else JOptionPane.showMessageDialog(null, "���ȳ�ʼ���������");
                		
                	}else if(title.equals("��������")){
                  		if(stock.StockExist()){
              		  try{
                          tab.setSelectedComponent(StockAdjustPage);

                         }catch(Exception ex){
                      	   StockAdjustPage=StockAdjustPanel.createStockAdjustPage(tab,stock);
                             tab.addTab(title, StockAdjustPage);
                             tab.setSelectedComponent(StockAdjustPage);
                         }}
                  		else JOptionPane.showMessageDialog(null, "���ȳ�ʼ���������");
                		
                		
                	}else if(title.equals("�����Ϣ��ʼ��")){
                		
              		  try{
                          tab.setSelectedComponent(StockInitializePage);

                         }catch(Exception ex){
                        	 StockInitializePage=StockInitializePanel.createStockInitializePage(tab,stock);
                             tab.addTab(title, StockInitializePage);
                             tab.setSelectedComponent(StockInitializePage);
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

//      	  tab.addTab("�û���Ϣ��", createReportPage());
//        tab.addTab("Dashboard", createPage(new CompositionDemo()));
//        tab.addTab("Google Stock", createPage(new GoogleStockDemo()));
//        tab.addTab("Global Customers", createPage(new PopulationDemo()));
    }

//    private FreeReportPage createReportPage() {
//        DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("�˺�");
//        model.addColumn("����");
//        model.addColumn("����");
//        model.addColumn("Ȩ��");
//        model.addColumn("������λ");
//
//
//        for (int i = 0; i < 100; i++) {
//            Vector row = new Vector();
//            row.add("000000001");
//            row.add("����");
//            row.add("123456");
//            row.add("��");
//            row.add("���Ա");
//            model.addRow(row);
//        }
//
//        FreeReportPage page = new FreeReportPage();
//        page.getTable().setModel(model);
//        page.setDescription("All Work Order Items by Part Number. Created " + new Date().toString());
//        setupPageToolbar(page);
//
//        return page;
//    }



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

//    public static void setupPageToolbar(FreePagePane page) {
//        page.getLeftToolBar().add(createButton("/free/test/home.png", "Click to go home", false));
//        page.getLeftToolBar().add(createButton("/free/test/left.png", "Click to go left", false));
//        page.getLeftToolBar().add(createButton("/free/test/right.png", "Click to go right", false));
//
//        FreeSearchTextField txtSearch = new FreeSearchTextField();
//        txtSearch.setColumns(10);
//        page.getRightToolBar().add(txtSearch);
//        page.getRightToolBar().add(createButton("/free/test/add.png", "Click to go right", true));
//        page.getRightToolBar().add(createButton("/free/test/update.png", "Click to go right", true));
//        page.getRightToolBar().add(createButton("/free/test/refresh.png", "Click to go right", true));
//        page.getRightToolBar().add(createButton("/free/test/print.png", "Click to go right", true));
//    }

//   public static FreeToolbarButton createButton(String icon, String tooltip, boolean rover) {
//        FreeToolbarButton button = null;
//        if (rover) {
//            button = new FreeToolbarRoverButton();
//        } else {
//            button = new FreeToolbarButton();
//        }
//        button.setIcon(TWaverUtil.getIcon(icon));
//        button.setToolTipText(tooltip);
//        
//        return button;
//    }

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
    }

   
}
