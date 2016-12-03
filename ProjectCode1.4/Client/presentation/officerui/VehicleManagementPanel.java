package officerui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;




import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.StaffInfoPanel;
import dataserviceimpl.DataFactory;
import enums.Sex;
import enums.Work;
import pamanagementsl.CManagementController;
import pamanagementslservice.CManagementService;
import po.ReceiptsPO;
import twaver.TWaverUtil;
import usersl.LogManagementController;
import userslservice.LogService;
import vo.CarVO;
import vo.DriverVO;
import vo.ReceiptsVO;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;

public class VehicleManagementPanel {
	public static JPanel addVehiclePanel, deleteVehiclePanel, fixVehiclePanel,
			seekVehiclePanel;
	public static JTabbedPane tab;
	private static DataFactory dataFactory;
	private static CarVO carvo;
	private static CManagementService car;
	private static String userid;

	public static FreeReportPage createVehicleManagementPage(JTabbedPane tab) {
		try {
			dataFactory = DataFactory.create();
			userid = StaffInfoPanel.userVO.getAccountnumber();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		car = new CManagementController(dataFactory);
		VehicleManagementPanel.tab = tab;
		return createReportPage();
	}

	private static FreeReportPage createReportPage() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("��������");
		model.addColumn("�������");
		model.addColumn("������λ");
		model.addColumn("����ʱ��");
		model.addColumn("����ʱ��");
		model.addColumn("����ͼƬ");

		ArrayList<CarVO> carList = car.getAllCar();
		for (Iterator<CarVO> i = carList.iterator(); i.hasNext();) {
			Vector row = new Vector();
			carvo = i.next();
			
			String buytime="";
			try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	        Calendar rightNow = Calendar.getInstance();
	        rightNow.add(Calendar.YEAR,-carvo.getWorkYear());
	        Date dt1=rightNow.getTime();
	        buytime = sdf.format(dt1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			row.add(carvo.getIDNumber());
			row.add(carvo.getLicenseNumber());
			row.add(carvo.getWorkPlaceNumber());
			row.add(buytime);
			row.add(carvo.getWorkYear());
			row.add("bmw.jpg");
			model.addRow(row);
		}

		FreeReportPage page = new FreeReportPage();
		page.getTable().setModel(model);
		page.setDescription("All Work Order Items by Part Number. Created "
				+ new Date().toString());
		setupPageToolbar(page);

		return page;
	}

	public static void setupPageToolbar(FreePagePane page) {
		FreeToolbarButton addVehicle, deleteVehicle, fixVehicle, seekVehicle;
		addVehicle = createButton("/free/test/add.png", "���ӳ���", true);
		deleteVehicle = createButton("/free/test/update.png", "ɾ������", true);
		fixVehicle = createButton("/free/test/refresh.png", "�޸ĳ���", true);
		seekVehicle = createButton("/free/test/print.png", "���ҳ���", true);
		page.getRightToolBar().add(addVehicle);
		page.getRightToolBar().add(deleteVehicle);
		page.getRightToolBar().add(fixVehicle);
		page.getRightToolBar().add(seekVehicle);

		addVehicle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = addVehicle.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(addVehiclePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createAddVehiclePanel();
					tab.addTab(title, OfficerUI.createPage(addVehiclePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(addVehiclePanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		deleteVehicle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = deleteVehicle.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteVehiclePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createDeleteVehiclePanel();
					tab.addTab(title, OfficerUI.createPage(deleteVehiclePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteVehiclePanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		fixVehicle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = fixVehicle.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixVehiclePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createFixVehiclePanel();
					tab.addTab(title, OfficerUI.createPage(fixVehiclePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixVehiclePanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		seekVehicle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = seekVehicle.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekVehiclePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createSeekVehiclePanel();
					tab.addTab(title, OfficerUI.createPage(seekVehiclePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekVehiclePanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

	}

	public static FreeToolbarButton createButton(String icon, String tooltip,
			boolean rover) {
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

	private static void createAddVehiclePanel() {
		addVehiclePanel = new JPanel();
		JLabel vehicleMark = new JLabel("��������");
		JLabel vehicleID = new JLabel("�������");
		JLabel picture = new JLabel("����ͼƬ");
		JLabel hallID = new JLabel("������λ");
		JLabel timeOfBuy = new JLabel("����ʱ��");
		JLabel workingTime = new JLabel("����ʱ��");

		JTextField vehicleMarkfield = new JTextField(10);
		JTextField vehicleIDfield = new JTextField(10);
		JTextField picturefield = new JTextField(10);
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
		BaseUI.myAdd(bpanel, picture, constraints, 0, 2, 1, 1);
		BaseUI.myAdd(bpanel, hallID, constraints, 0, 3, 1, 1);
		BaseUI.myAdd(bpanel, timeOfBuy, constraints, 0, 4, 1, 1);
		BaseUI.myAdd(bpanel, workingTime, constraints, 0, 5, 1, 1);

		BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 1, 1, 1);
		BaseUI.myAdd(bpanel, picturefield, constraints, 1, 2, 1, 1);
		BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 3, 1, 1);
		BaseUI.myAdd(bpanel, timeOfBuyfield, constraints, 1, 4, 1, 1);
		BaseUI.myAdd(bpanel, workingTimefield, constraints, 1, 5, 1, 1);

		JButton submit = new JButton("�ύ");
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					carvo = new CarVO(vehicleMarkfield.getText(), hallIDfield
							.getText(), vehicleIDfield.getText(), Integer
							.parseInt(workingTimefield.getText()));
					car.save(carvo);
					LogService ls = new LogManagementController();
					ls.addMessage(userid, "���ӳ�����Ϣ");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		/***********************************************/
		BaseUI.myAdd(bpanel, submit, constraints, 0, 7, 2, 1);

		addVehiclePanel.add(bpanel);
	}

	private static void createDeleteVehiclePanel() {
		deleteVehiclePanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel vehicleMark = new JLabel("��������");
		JTextField vehicleMarkfield = new JTextField(10);
		BaseUI.myAdd(bpanel, vehicleMark, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("��ѯɾ��������Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 7, 2, 1);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				bpanel.remove(submit);
				bpanel.remove(vehicleMark);
				bpanel.remove(vehicleMarkfield);

				carvo = car.select(vehicleMarkfield.getText());

				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar today = Calendar.getInstance();
				today.add(Calendar.DATE, -carvo.getWorkYear());
				String t=sdf.format(today);
				JLabel vehicleMark = new JLabel("��������");
				JLabel vehicleID = new JLabel("�������");
				JLabel picture = new JLabel("����ͼƬ");
				JLabel hallID = new JLabel("������λ");
				JLabel timeOfBuy = new JLabel("����ʱ��");
				JLabel workingTime = new JLabel("����ʱ��");
				JLabel vehicleMarkfield = new JLabel(carvo.getIDNumber());
				JLabel vehicleIDfield = new JLabel(carvo.getLicenseNumber());
				JLabel picturefield = new JLabel("bmw.jpg");
				JLabel hallIDfield = new JLabel(carvo.getWorkPlaceNumber());
				JLabel timeOfBuyfield = new JLabel(t);
				JLabel workingTimefield = new JLabel(carvo.getWorkYear() + "");

				BaseUI.myAdd(bpanel, vehicleMark, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, vehicleID, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, picture, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, hallID, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, timeOfBuy, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, workingTime, constraints, 0, 5, 1, 1);

				BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, picturefield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, timeOfBuyfield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, workingTimefield, constraints, 1, 5, 1, 1);

				JButton delete = new JButton("ȷ��ɾ��");
				delete.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							car.delete(vehicleMarkfield.getText());
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "ɾ��������Ϣ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				/***********************************************/
				BaseUI.myAdd(bpanel, delete, constraints, 0, 7, 2, 1);
			}
		});

		deleteVehiclePanel.add(bpanel);
	}

	private static void createFixVehiclePanel() {
		fixVehiclePanel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel vehicleMark = new JLabel("��������");
		JTextField vehicleMarkfield = new JTextField(10);
		BaseUI.myAdd(bpanel, vehicleMark, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("�޸ĳ�����Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 7, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				carvo = car.select(vehicleMarkfield.getText());
				bpanel.remove(submit);
				bpanel.remove(vehicleMark);
				bpanel.remove(vehicleMarkfield);

				JLabel vehicleMark = new JLabel("��������");
				JLabel vehicleID = new JLabel("�������");
				JLabel picture = new JLabel("����ͼƬ");
				JLabel hallID = new JLabel("������λ");
				JLabel timeOfBuy = new JLabel("����ʱ��");
				JLabel workingTime = new JLabel("����ʱ��");

				JLabel vehicleMarkfield = new JLabel(carvo.getIDNumber());
				JTextField vehicleIDfield = new JTextField(10);
				JTextField picturefield = new JTextField(10);
				JTextField hallIDfield = new JTextField(10);
				JTextField timeOfBuyfield = new JTextField(10);
				JTextField workingTimefield = new JTextField(10);
				
				vehicleIDfield.setText(carvo.getLicenseNumber());
				picturefield.setText("bmw.jpg");
				hallIDfield.setText(carvo.getWorkPlaceNumber());
				String buytime="";
				try{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		        Calendar rightNow = Calendar.getInstance();
		        rightNow.add(Calendar.YEAR,-carvo.getWorkYear());
		        Date dt1=rightNow.getTime();
		        buytime = sdf.format(dt1);
				}catch(Exception e){
					e.printStackTrace();
				}
				timeOfBuyfield.setText(buytime);
				workingTimefield.setText(carvo.getWorkYear()+"");
				

				BaseUI.myAdd(bpanel, vehicleMark, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, vehicleID, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, picture, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, hallID, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, timeOfBuy, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, workingTime, constraints, 0, 5, 1, 1);

				BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, picturefield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, timeOfBuyfield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, workingTimefield, constraints, 1, 5, 1, 1);

				JButton fix = new JButton("ȷ���޸�");
				fix.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							carvo = new CarVO(vehicleMarkfield.getText(),
									hallIDfield.getText(), vehicleIDfield
											.getText(), Integer
											.parseInt(workingTimefield
													.getText()));
							car.saveChange(carvo);
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "�޸ĳ�����Ϣ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				/***********************************************/
				BaseUI.myAdd(bpanel, fix, constraints, 0, 7, 2, 1);
			}

		});

		fixVehiclePanel.add(bpanel);

	}

	private static void createSeekVehiclePanel() {
		seekVehiclePanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel vehicleMark = new JLabel("��������");
		JTextField vehicleMarkfield = new JTextField(10);
		BaseUI.myAdd(bpanel, vehicleMark, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("��ѯ������Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 7, 2, 1);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				carvo = car.select(vehicleMarkfield.getText());
				LogService ls = new LogManagementController();
				ls.addMessage(userid, "��ѯ������Ϣ");
				submit.setText("ȷ��");
				bpanel.remove(vehicleMark);
				bpanel.remove(vehicleMarkfield);
				JLabel vehicleMark = new JLabel("��������");
				JLabel vehicleID = new JLabel("�������");
				JLabel picture = new JLabel("����ͼƬ");
				JLabel hallID = new JLabel("������λ");
				JLabel timeOfBuy = new JLabel("����ʱ��");
				JLabel workingTime = new JLabel("����ʱ��");
				JLabel vehicleMarkfield = new JLabel(carvo.getIDNumber());
				JLabel vehicleIDfield = new JLabel(carvo.getLicenseNumber());
				JLabel picturefield = new JLabel();
				picturefield.setIcon(new ImageIcon("C:\\workspace\\ProjectCode1.4\\bmw.jpg"));
				JLabel hallIDfield = new JLabel(carvo.getWorkPlaceNumber());
				
				String buytime="";
				try{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		        Calendar rightNow = Calendar.getInstance();
		        rightNow.add(Calendar.YEAR,-carvo.getWorkYear());
		        Date dt1=rightNow.getTime();
		        buytime = sdf.format(dt1);
				}catch(Exception e){
					e.printStackTrace();
				}
				JLabel timeOfBuyfield = new JLabel(buytime);
				JLabel workingTimefield = new JLabel(carvo.getWorkYear() + "");

				BaseUI.myAdd(bpanel, vehicleMark, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, vehicleID, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, picture, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, hallID, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, timeOfBuy, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, workingTime, constraints, 0, 5, 1, 1);

				BaseUI.myAdd(bpanel, vehicleMarkfield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, picturefield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, timeOfBuyfield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, workingTimefield, constraints, 1, 5, 1, 1);
			}
		});

		seekVehiclePanel.add(bpanel);
	}
}
