package officerui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.StaffInfoPanel;
import dataserviceimpl.DataFactory;
import enums.DocumentCondition;
import enums.Sex;
import enums.Work;
import pamanagementsl.DManagementController;
import pamanagementsl.PManagementController;
import pamanagementslservice.DManagementService;
import pamanagementslservice.PManagementService;
import twaver.TWaverUtil;
import usersl.LogManagementController;
import userslservice.LogService;
import vo.DeliverVO;
import vo.DriverVO;
import vo.StaffVO;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;

public class DriverManagementPanel {
	public static JPanel addDriverPanel, deleteDriverPanel, fixDriverPanel,
			seekDriverPanel;
	public static JTabbedPane tab;
	private static DataFactory dataFactory;
	private static DriverVO drivervo;
	private static DManagementService driver;
	private static PManagementService staff;
	private static String userid;

	public static FreeReportPage createDriverManagementPage(JTabbedPane tab) {
		try {
			userid = StaffInfoPanel.userVO.getAccountnumber();
			dataFactory = DataFactory.create();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		driver = new DManagementController(dataFactory);
		staff = new PManagementController();
		DriverManagementPanel.tab = tab;
		return createReportPage();
	}

	private static FreeReportPage createReportPage() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("˾�����");
		model.addColumn("����");
		model.addColumn("��������");
		model.addColumn("���֤��");
		model.addColumn("�ֻ�");
		model.addColumn("�Ա�");
		model.addColumn("��ʻ֤����");
		model.addColumn("������λ");
		model.addColumn("��ͥ��ַ");
		model.addColumn("н��");

		ArrayList<DriverVO> driverList = driver.getAllDriver();
		for (Iterator<DriverVO> i = driverList.iterator(); i.hasNext();) {
			Vector row = new Vector();
			drivervo = i.next();
			row.add(drivervo.getWorkNumber());
			row.add(drivervo.getName());
			row.add(drivervo.getBirthDate());
			row.add(drivervo.getIdNumber());
			row.add(drivervo.getPhoneNumber());
			row.add(drivervo.getSex());
			row.add(drivervo.getDriverYear());
			row.add(drivervo.getWorkPlaceNumber());
			row.add(drivervo.getAddress());
			row.add(drivervo.getPage());
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
		FreeToolbarButton addDriver, deleteDriver, fixDriver, seekDriver;
		addDriver = createButton("/free/test/add.png", "����˾��", true);
		deleteDriver = createButton("/free/test/update.png", "ɾ��˾��", true);
		fixDriver = createButton("/free/test/refresh.png", "�޸�˾��", true);
		seekDriver = createButton("/free/test/print.png", "����˾��", true);
		page.getRightToolBar().add(addDriver);
		page.getRightToolBar().add(deleteDriver);
		page.getRightToolBar().add(fixDriver);
		page.getRightToolBar().add(seekDriver);

		addDriver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = addDriver.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(addDriverPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createAddDriverPanel();
					tab.addTab(title, OfficerUI.createPage(addDriverPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(addDriverPanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		deleteDriver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = deleteDriver.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteDriverPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createDeleteDriverPanel();
					tab.addTab(title, OfficerUI.createPage(deleteDriverPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteDriverPanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		fixDriver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = fixDriver.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixDriverPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createFixDriverPanel();
					tab.addTab(title, OfficerUI.createPage(fixDriverPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixDriverPanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		seekDriver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = seekDriver.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekDriverPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createSeekDriverPanel();
					tab.addTab(title, OfficerUI.createPage(seekDriverPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekDriverPanel);
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

	private static void createAddDriverPanel() {
		addDriverPanel = new JPanel();
		JLabel driverID = new JLabel("˾�����");
		JLabel name = new JLabel("����");
		JLabel birthday = new JLabel("��������");
		JLabel identityCard = new JLabel("���֤��");
		JLabel mobilePhone = new JLabel("�ֻ�");
		JLabel sex = new JLabel("�Ա�");
		JLabel driverYear = new JLabel("��ʻ֤����");
		JLabel hallID = new JLabel("������λ");
		JLabel address = new JLabel("��ͥסַ");
		JLabel page = new JLabel("н��");
		JTextField driverIDfield = new JTextField(10);
		JTextField namefield = new JTextField(10);
		JTextField birthdayfield = new JTextField(10);
		JTextField identityCardfield = new JTextField(10);
		JTextField mobilePhonefield = new JTextField(10);
		JTextField driverYearfield = new JTextField(10);
		JTextField hallIDfield = new JTextField(10);
		JTextField addressfield = new JTextField(10);
		JTextField pagefield = new JTextField(10);
		JComboBox sexCombo = new JComboBox();
		sexCombo.addItem("��");
		sexCombo.addItem("Ů");

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		BaseUI.myAdd(bpanel, driverID, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, name, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, birthday, constraints, 0, 2, 1, 1);
		BaseUI.myAdd(bpanel, identityCard, constraints, 0, 3, 1, 1);
		BaseUI.myAdd(bpanel, mobilePhone, constraints, 0, 4, 1, 1);
		BaseUI.myAdd(bpanel, sex, constraints, 0, 5, 1, 1);
		BaseUI.myAdd(bpanel, driverYear, constraints, 0, 6, 1, 1);
		BaseUI.myAdd(bpanel, hallID, constraints, 0, 7, 1, 1);
		BaseUI.myAdd(bpanel, address, constraints, 0, 8, 1, 1);
		BaseUI.myAdd(bpanel, page, constraints, 0, 9, 1, 1);

		BaseUI.myAdd(bpanel, driverIDfield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, namefield, constraints, 1, 1, 1, 1);
		BaseUI.myAdd(bpanel, birthdayfield, constraints, 1, 2, 1, 1);
		BaseUI.myAdd(bpanel, identityCardfield, constraints, 1, 3, 1, 1);
		BaseUI.myAdd(bpanel, mobilePhonefield, constraints, 1, 4, 1, 1);
		BaseUI.myAdd(bpanel, sexCombo, constraints, 1, 5, 1, 1);
		BaseUI.myAdd(bpanel, driverYearfield, constraints, 1, 6, 1, 1);
		BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 7, 1, 1);
		BaseUI.myAdd(bpanel, addressfield, constraints, 1, 8, 1, 1);
		BaseUI.myAdd(bpanel, pagefield, constraints, 1, 9, 1, 1);

		JButton submit = new JButton("�ύ");
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					drivervo = new DriverVO(namefield.getText(), Work.Driver,
							driverIDfield.getText(), hallIDfield.getText(),
							birthdayfield.getText(), identityCardfield
									.getText(), mobilePhonefield.getText(),
							addressfield.getText(), Sex.male, Integer
									.parseInt(driverYearfield.getText()),
							Integer.parseInt(pagefield.getText()));
					if (sexCombo.getSelectedIndex() == 1)
						drivervo.setSex(Sex.female);
					driver.save(drivervo);
					staff.save(new StaffVO(drivervo.getName(), Work.Driver,
							drivervo.getWorkNumber(), drivervo
									.getWorkPlaceNumber(), drivervo
									.getBirthDate(), drivervo.getIdNumber(),
							drivervo.getPhoneNumber(), drivervo.getAddress(),
							drivervo.getSex(), drivervo.getPage()));
					LogService ls = new LogManagementController();
					ls.addMessage(userid, "����˾����Ϣ");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		/***********************************************/
		BaseUI.myAdd(bpanel, submit, constraints, 0, 10, 2, 1);

		addDriverPanel.add(bpanel);
	}

	private static void createDeleteDriverPanel() {
		deleteDriverPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel driverID = new JLabel("˾�����");
		JTextField driverIDfield = new JTextField(10);
		BaseUI.myAdd(bpanel, driverID, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, driverIDfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("��ѯɾ��˾����Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 10, 2, 1);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				bpanel.remove(submit);
				bpanel.remove(driverID);
				bpanel.remove(driverIDfield);

				drivervo = driver.select(driverIDfield.getText());

				JLabel driverID = new JLabel("˾�����");
				JLabel name = new JLabel("����");
				JLabel birthday = new JLabel("��������");
				JLabel identityCard = new JLabel("���֤��");
				JLabel mobilePhone = new JLabel("�ֻ�");
				JLabel sex = new JLabel("�Ա�");
				JLabel driverYear = new JLabel("��ʻ֤����");
				JLabel hallID = new JLabel("������λ");
				JLabel address = new JLabel("��ͥסַ");
				JLabel page = new JLabel("н��");

				JLabel driverIDfield = new JLabel(drivervo.getWorkNumber());
				JLabel namefield = new JLabel(drivervo.getName());
				JLabel birthdayfield = new JLabel(drivervo.getBirthDate());
				JLabel identityCardfield = new JLabel(drivervo.getIdNumber());
				JLabel mobilePhonefield = new JLabel(drivervo.getPhoneNumber());
				JLabel sexfield = new JLabel(drivervo.getSex().toString());
				JLabel driverYearfield = new JLabel(drivervo.getDriverYear()
						+ "");
				JLabel hallIDfield = new JLabel(drivervo.getWorkPlaceNumber());
				JLabel addressfield = new JLabel(drivervo.getAddress());
				JLabel pagefield = new JLabel(drivervo.getPage() + "");

				BaseUI.myAdd(bpanel, driverID, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, name, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, birthday, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, identityCard, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, mobilePhone, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, sex, constraints, 0, 5, 1, 1);
				BaseUI.myAdd(bpanel, driverYear, constraints, 0, 6, 1, 1);
				BaseUI.myAdd(bpanel, hallID, constraints, 0, 7, 1, 1);
				BaseUI.myAdd(bpanel, address, constraints, 0, 8, 1, 1);
				BaseUI.myAdd(bpanel, page, constraints, 0, 9, 1, 1);

				BaseUI.myAdd(bpanel, driverIDfield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, namefield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, birthdayfield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, identityCardfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, mobilePhonefield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, sexfield, constraints, 1, 5, 1, 1);
				BaseUI.myAdd(bpanel, driverYearfield, constraints, 1, 6, 1, 1);
				BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 7, 1, 1);
				BaseUI.myAdd(bpanel, addressfield, constraints, 1, 8, 1, 1);
				BaseUI.myAdd(bpanel, pagefield, constraints, 1, 9, 1, 1);

				JButton delete = new JButton("ȷ��ɾ��");
				BaseUI.myAdd(bpanel, delete, constraints, 0, 10, 2, 1);
				delete.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							driver.delete(driverIDfield.getText());
							staff.delete(driverIDfield.getText());
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "ɾ��˾����Ϣ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});

			}
		});

		deleteDriverPanel.add(bpanel);
	}

	private static void createFixDriverPanel() {
		fixDriverPanel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel driverID = new JLabel("˾�����");
		JTextField driverIDfield = new JTextField(10);
		BaseUI.myAdd(bpanel, driverID, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, driverIDfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("�޸�˾����Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 10, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				drivervo=driver.select(driverIDfield.getText());
				bpanel.remove(driverID);
				bpanel.remove(driverIDfield);
				bpanel.remove(submit);

				JLabel driverID = new JLabel("˾�����");
				JLabel name = new JLabel("����");
				JLabel birthday = new JLabel("��������");
				JLabel identityCard = new JLabel("���֤��");
				JLabel mobilePhone = new JLabel("�ֻ�");
				JLabel sex = new JLabel("�Ա�");
				JLabel driverYear = new JLabel("��ʻ֤����");
				JLabel hallID = new JLabel("������λ");
				JLabel address = new JLabel("��ͥסַ");
				JLabel page = new JLabel("н��");
				JLabel driverIDfield = new JLabel(drivervo.getWorkNumber());
				JTextField namefield = new JTextField(10);
				JTextField birthdayfield = new JTextField(10);
				JTextField identityCardfield = new JTextField(10);
				JTextField mobilePhonefield = new JTextField(10);
				JTextField driverYearfield = new JTextField(10);
				JTextField hallIDfield = new JTextField(10);
				JTextField addressfield = new JTextField(10);
				JTextField pagefield = new JTextField(10);
				
				namefield.setText(drivervo.getName());
				birthdayfield.setText(drivervo.getBirthDate());
				identityCardfield.setText(drivervo.getIdNumber());
				mobilePhonefield.setText(drivervo.getPhoneNumber());
				driverYearfield.setText(drivervo.getDriverYear()+"");
				hallIDfield.setText(drivervo.getWorkPlaceNumber());
				addressfield.setText(drivervo.getAddress());
				pagefield.setText(drivervo.getPage()+"");
				
				JComboBox sexCombo = new JComboBox();
				sexCombo.addItem("��");
				sexCombo.addItem("Ů");

				BaseUI.myAdd(bpanel, driverID, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, name, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, birthday, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, identityCard, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, mobilePhone, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, sex, constraints, 0, 5, 1, 1);
				BaseUI.myAdd(bpanel, driverYear, constraints, 0, 6, 1, 1);
				BaseUI.myAdd(bpanel, hallID, constraints, 0, 7, 1, 1);
				BaseUI.myAdd(bpanel, address, constraints, 0, 8, 1, 1);
				BaseUI.myAdd(bpanel, page, constraints, 0, 9, 1, 1);

				BaseUI.myAdd(bpanel, driverIDfield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, namefield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, birthdayfield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, identityCardfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, mobilePhonefield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, sexCombo, constraints, 1, 5, 1, 1);
				BaseUI.myAdd(bpanel, driverYearfield, constraints, 1, 6, 1, 1);
				BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 7, 1, 1);
				BaseUI.myAdd(bpanel, addressfield, constraints, 1, 8, 1, 1);
				BaseUI.myAdd(bpanel, pagefield, constraints, 1, 9, 1, 1);

				JButton fix = new JButton("ȷ���޸�");
				fix.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							drivervo = new DriverVO(namefield.getText(),
									Work.Driver, driverIDfield.getText(),
									hallIDfield.getText(), birthdayfield
											.getText(), identityCardfield
											.getText(), mobilePhonefield
											.getText(), addressfield.getText(),
									Sex.male, Integer.parseInt(driverYearfield
											.getText()), Integer
											.parseInt(pagefield.getText()));
							if (sexCombo.getSelectedIndex() == 1)
								drivervo.setSex(Sex.female);
							driver.saveChange(drivervo);
							staff.saveChange(new StaffVO(drivervo.getName(),
									Work.Driver, drivervo.getWorkNumber(),
									drivervo.getWorkPlaceNumber(), drivervo
											.getBirthDate(), drivervo
											.getIdNumber(), drivervo
											.getPhoneNumber(), drivervo
											.getAddress(), drivervo.getSex(),
									drivervo.getPage()));
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "�޸�˾����Ϣ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				/***********************************************/
				BaseUI.myAdd(bpanel, fix, constraints, 0, 10, 2, 1);
			}

		});

		fixDriverPanel.add(bpanel);

	}

	private static void createSeekDriverPanel() {
		seekDriverPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel driverID = new JLabel("˾�����");
		JTextField driverIDfield = new JTextField(10);
		BaseUI.myAdd(bpanel, driverID, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, driverIDfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("��ѯ�޸�˾����Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 10, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				drivervo = driver.select(driverIDfield.getText());
				LogService ls = new LogManagementController();
				ls.addMessage(userid, "��ѯ˾����Ϣ");
				submit.setText("ȷ��");
				bpanel.remove(driverID);
				bpanel.remove(driverIDfield);
				JLabel driverID = new JLabel("˾�����");
				JLabel name = new JLabel("����");
				JLabel birthday = new JLabel("��������");
				JLabel identityCard = new JLabel("���֤��");
				JLabel mobilePhone = new JLabel("�ֻ�");
				JLabel sex = new JLabel("�Ա�");
				JLabel driverYear = new JLabel("��ʻ֤����");
				JLabel hallID = new JLabel("������λ");
				JLabel address = new JLabel("��ͥסַ");
				JLabel page = new JLabel("н��");

				JLabel driverIDfield = new JLabel(drivervo.getWorkNumber());
				JLabel namefield = new JLabel(drivervo.getName());
				JLabel birthdayfield = new JLabel(drivervo.getBirthDate());
				JLabel identityCardfield = new JLabel(drivervo.getIdNumber());
				JLabel mobilePhonefield = new JLabel(drivervo.getPhoneNumber());
				JLabel sexfield = new JLabel(drivervo.getSex().toString());
				JLabel driverYearfield = new JLabel(drivervo.getDriverYear()
						+ "");
				JLabel hallIDfield = new JLabel(drivervo.getWorkPlaceNumber());
				JLabel addressfield = new JLabel(drivervo.getAddress());
				JLabel pagefield = new JLabel(drivervo.getPage() + "");

				BaseUI.myAdd(bpanel, driverID, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, name, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, birthday, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, identityCard, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, mobilePhone, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, sex, constraints, 0, 5, 1, 1);
				BaseUI.myAdd(bpanel, driverYear, constraints, 0, 6, 1, 1);
				BaseUI.myAdd(bpanel, hallID, constraints, 0, 7, 1, 1);
				BaseUI.myAdd(bpanel, address, constraints, 0, 8, 1, 1);
				BaseUI.myAdd(bpanel, page, constraints, 0, 9, 1, 1);

				BaseUI.myAdd(bpanel, driverIDfield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, namefield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, birthdayfield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, identityCardfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, mobilePhonefield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, sexfield, constraints, 1, 5, 1, 1);
				BaseUI.myAdd(bpanel, driverYearfield, constraints, 1, 6, 1, 1);
				BaseUI.myAdd(bpanel, hallIDfield, constraints, 1, 7, 1, 1);
				BaseUI.myAdd(bpanel, addressfield, constraints, 1, 8, 1, 1);
				BaseUI.myAdd(bpanel, pagefield, constraints, 1, 9, 1, 1);
			}
		});

		seekDriverPanel.add(bpanel);
	}
}
