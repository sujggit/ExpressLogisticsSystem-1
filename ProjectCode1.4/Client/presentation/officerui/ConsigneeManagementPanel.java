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

import auditsl.AuditInfo;
import main.StaffInfoPanel;
import dataserviceimpl.DataFactory;
import enums.Condition;
import enums.DocumentCondition;
import enums.Position;
import enums.Traffic;
import enums.TransportType;
import strategysl.ConstantController;
import transportsl.Transport;
import transportsl.TransportController;
import transportslservice.TransportService;
import twaver.TWaverUtil;
import usersl.LogManagementController;
import userslservice.LogService;
import vo.TransportVO;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;

public class ConsigneeManagementPanel {
	public static JPanel addConsigneePanel, deleteConsigneePanel,
			fixConsigneePanel, seekConsigneePanel;
	public static JTabbedPane tab;
	private static FreePagePane seekExpressPage;
	private static TransportService transportController;
	private static AuditInfo audit;
	private static DataFactory dataFactory;
	private static ConstantController constantController;
	private static Transport transport;
	private static String userid;
	private static TransportVO transportvo;

	public static FreeReportPage createConsigneeManagementPage(JTabbedPane tab) {
		ConsigneeManagementPanel.tab = tab;
		try {
			dataFactory = DataFactory.create();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		constantController = new ConstantController();
		transport = new Transport(dataFactory, constantController);
		transportController = new TransportController(transport);
		userid = StaffInfoPanel.userVO.getAccountnumber();
		audit = new TransportController(transport);
		return createReportPage();
	}

	private static FreeReportPage createReportPage() {
		DefaultTableModel model = new DefaultTableModel();
		ArrayList<TransportVO> transportList = new ArrayList<TransportVO>();
		try {
			transportList = transportController.getTransportList(userid,
					DocumentCondition.handing);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		model.addColumn("��������");
		model.addColumn("Ӫҵ�����ﵥ���");
		model.addColumn("װ�������");
		// ���ﵥ�����˱�ű���װ�������
		model.addColumn("������");
		model.addColumn("�������");
		model.addColumn("����Ա");
		model.addColumn("�˷�");

		for (Iterator<TransportVO> i = transportList.iterator(); i.hasNext();) {
			transportvo = i.next();
			Vector row = new Vector();
			row.add(transportvo.getTime());
			row.add(transportvo.getID());
			row.add(transportvo.getTransportID());
			row.add(transportvo.getDeparture());
			row.add(transportvo.getTrafficID());
			row.add(transportvo.getWriter());
			row.add(transportvo.getfare());
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
		FreeToolbarButton addConsignee, deleteConsignee, fixConsignee, seekConsignee;
		addConsignee = createButton("/free/test/add.png", "����Ӫҵ�����ﵥ", true);
		deleteConsignee = createButton("/free/test/update.png", "ɾ��Ӫҵ�����ﵥ",
				true);
		fixConsignee = createButton("/free/test/refresh.png", "�޸�Ӫҵ�����ﵥ", true);
		seekConsignee = createButton("/free/test/print.png", "����Ӫҵ�����ﵥ", true);
		page.getRightToolBar().add(addConsignee);
		page.getRightToolBar().add(deleteConsignee);
		page.getRightToolBar().add(fixConsignee);
		page.getRightToolBar().add(seekConsignee);

		addConsignee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = addConsignee.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(addConsigneePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createAddConsigneePanel();
					tab.addTab(title, OfficerUI.createPage(addConsigneePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(addConsigneePanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		deleteConsignee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = deleteConsignee.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteConsigneePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createDeleteConsigneePanel();
					tab.addTab(title,
							OfficerUI.createPage(deleteConsigneePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteConsigneePanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		fixConsignee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = fixConsignee.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixConsigneePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createFixConsigneePanel();
					tab.addTab(title, OfficerUI.createPage(fixConsigneePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixConsigneePanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		seekConsignee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = seekConsignee.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekConsigneePanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createSeekConsigneePanel();
					tab.addTab(title, OfficerUI.createPage(seekConsigneePanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekConsigneePanel);
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

	private static void createAddConsigneePanel() {
		addConsigneePanel = new JPanel();
		JLabel date = new JLabel("��������");
		JLabel consigneeID = new JLabel("Ӫҵ�����ﵥ");
		JLabel entruckID = new JLabel("װ�������");
		JLabel officer = new JLabel("����Ա");

		JTextField datefield = new JTextField(10);
		JTextField consigneeIDfield = new JTextField(10);
		JTextField entruckIDfield = new JTextField(10);
		JComboBox entruckCombo = new JComboBox();
		entruckCombo.addItem("Ӫҵ��װ����");
		entruckCombo.addItem("��ת������ת��");
		JTextField officerfield = new JTextField(20);

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, consigneeID, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, entruckID, constraints, 0, 2, 1, 1);
		BaseUI.myAdd(bpanel, officer, constraints, 0, 3, 1, 1);

		BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, consigneeIDfield, constraints, 1, 1, 1, 1);
		BaseUI.myAdd(bpanel, entruckIDfield, constraints, 1, 2, 1, 1);
		BaseUI.myAdd(bpanel, entruckCombo, constraints, 2, 2, 1, 1);
		BaseUI.myAdd(bpanel, officerfield, constraints, 1, 3, 1, 1);

		JButton submit = new JButton("�ύ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 4, 2, 1);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					TransportVO entruckvo = transportController
							.getTransport(entruckIDfield.getText());
					transportvo = transportController.newTransport(
							TransportType.Reception,
							consigneeIDfield.getText(),
							entruckvo.getDeparture(),
							entruckvo.getDestination(), entruckvo.getID(),
							datefield.getText(), entruckvo.getTrafficID(),
							entruckvo.getTrafficType(), entruckvo.getfare(),
							entruckvo.getMember(), entruckvo.getOrder(),
							entruckvo.getCondition(),
							DocumentCondition.handing, userid);
					transportController.saveTransport(transportvo);
					LogService ls = new LogManagementController();
					ls.addMessage(userid, "����Ӫҵ�����ﵥ");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		/***********************************************/
		addConsigneePanel.add(bpanel);
	}

	private static void createDeleteConsigneePanel() {
		deleteConsigneePanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel consigneeID = new JLabel("Ӫҵ�����ﵥ���");
		JTextField consigneeIDfield = new JTextField(10);
		BaseUI.myAdd(bpanel, consigneeID, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, consigneeIDfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("��ѯɾ��Ӫҵ�����ﵥ��Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 8, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					transportvo = transportController
							.getTransport(consigneeIDfield.getText());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				bpanel.remove(submit);
				bpanel.remove(consigneeID);
				bpanel.remove(consigneeIDfield);
				JLabel date = new JLabel("��������");
				JLabel consigneeID = new JLabel("Ӫҵ�����ﵥ");
				JLabel entruckID = new JLabel("װ����");
				JLabel vehicleID = new JLabel("�������");
				JLabel departure = new JLabel("������");
				JLabel officer = new JLabel("����Ա");
				JLabel fee = new JLabel("�˷�");
				JButton seekExpress = new JButton("��ѯ���");

				JLabel datefield = new JLabel(transportvo.getTime());
				JLabel consigneeIDfield = new JLabel(transportvo.getID());
				JLabel entruckIDfield = new JLabel(transportvo.getTransportID());
				JLabel vehicleIDfield = new JLabel(transportvo.getTrafficID());
				JLabel departurefield = new JLabel(transportvo.getDeparture());
				JLabel officerfield = new JLabel(transportvo.getWriter());
				JLabel feefield = new JLabel(transportvo.getfare() + "");

				BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, consigneeID, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, entruckID, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, vehicleID, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, departure, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, officer, constraints, 0, 5, 1, 1);
				BaseUI.myAdd(bpanel, fee, constraints, 0, 6, 1, 1);
				BaseUI.myAdd(bpanel, seekExpress, constraints, 0, 7, 1, 1);

				BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, consigneeIDfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, entruckIDfield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, departurefield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, officerfield, constraints, 1, 5, 1, 1);
				BaseUI.myAdd(bpanel, feefield, constraints, 1, 6, 1, 1);

				JButton delete = new JButton("��ѯɾ��Ӫҵ�����ﵥ��Ϣ");
				BaseUI.myAdd(bpanel, delete, constraints, 0, 8, 2, 1);

				seekExpress.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						String title = "��ѯ���";
						createSeekExpressPage();
						try {
							FreePagePane pagePane = FreeUtil
									.getPagePane(seekExpressPage);
							tab.setSelectedComponent(pagePane);
						} catch (Exception ex) {
							tab.addTab(title,
									OfficerUI.createPage(seekExpressPage));
							FreePagePane pagePane = FreeUtil
									.getPagePane(seekExpressPage);
							tab.setSelectedComponent(pagePane);
						}
					}
				});
				/***********************************************/

				delete.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							transportController.deleteTransport(transportvo
									.getID());
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "ɾ��Ӫҵ�����ﵥ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				/***********************************************/
			}
		});

		deleteConsigneePanel.add(bpanel);
	}

	private static void createFixConsigneePanel() {
		fixConsigneePanel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel consigneeID = new JLabel("Ӫҵ�����ﵥ���");
		JTextField consigneeIDfield = new JTextField(10);
		BaseUI.myAdd(bpanel, consigneeID, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, consigneeIDfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("��ѯ�޸�Ӫҵ�����ﵥ��Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 8, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<String> memberList = new ArrayList<String>();
				ArrayList<String> orderList = new ArrayList<String>();
				ArrayList<Condition> conditionList = new ArrayList<Condition>();

				bpanel.remove(submit);
				bpanel.remove(consigneeID);
				bpanel.remove(consigneeIDfield);

				JLabel date = new JLabel("��������");
				JLabel consigneeID = new JLabel("Ӫҵ�����ﵥ");
				JLabel entruckID = new JLabel("װ�������");
				JLabel vehicleID = new JLabel("�������");
				JLabel departure = new JLabel("������");
				JLabel officer = new JLabel("����Ա");
				JLabel fee = new JLabel("�˷�");
				JButton addExpress = new JButton("��ӿ��");
				JButton addMember = new JButton("��ӳ�Ա");
				JButton addCondition = new JButton("���״̬");

				JTextField datefield = new JTextField(10);
				JTextField consigneeIDfield = new JTextField(10);
				JTextField entruckIDfield = new JTextField(10);
				JComboBox entruckCombo = new JComboBox();
				entruckCombo.addItem("Ӫҵ��װ����");
				entruckCombo.addItem("��ת������ת��");
				JTextField vehicleIDfield = new JTextField(10);
				JTextField departurefield = new JTextField(20);
				JTextField officerfield = new JTextField(20);
				JTextField feefield = new JTextField(10);
				JTextField addExpressfield = new JTextField(10);
				JTextField addMemberfield = new JTextField(10);
				JComboBox addConditionfield = new JComboBox();
				addConditionfield.addItem("����");
				addConditionfield.addItem("��");
				addConditionfield.addItem("��ʧ");

				BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, consigneeID, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, entruckID, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, vehicleID, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, departure, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, officer, constraints, 0, 5, 1, 1);
				BaseUI.myAdd(bpanel, fee, constraints, 0, 6, 1, 1);
				BaseUI.myAdd(bpanel, addExpress, constraints, 0, 7, 1, 1);
				BaseUI.myAdd(bpanel, addMember, constraints, 0, 8, 1, 1);
				BaseUI.myAdd(bpanel, addCondition, constraints, 0, 9, 1, 1);

				BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, consigneeIDfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, entruckIDfield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, entruckCombo, constraints, 2, 2, 1, 1);
				BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, departurefield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, officerfield, constraints, 1, 5, 1, 1);
				BaseUI.myAdd(bpanel, feefield, constraints, 1, 6, 1, 1);
				BaseUI.myAdd(bpanel, addExpressfield, constraints, 1, 7, 1, 1);
				BaseUI.myAdd(bpanel, addMemberfield, constraints, 1, 8, 1, 1);
				BaseUI.myAdd(bpanel, addConditionfield, constraints, 1, 9, 1, 1);

				addExpress.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (!orderList.contains(addExpressfield.getText()))
							orderList.add(addExpressfield.getText());
					}
				});
				/***********************************************/

				addMember.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (!memberList.contains(addMemberfield.getText()))
							memberList.add(addMemberfield.getText());
					}
				});
				/***********************************************/

				addCondition.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (conditionList.size() < orderList.size()) {
							switch (addConditionfield.getSelectedIndex()) {
							case 0:
								conditionList.add(Condition.perfect);
								break;
							case 1:
								conditionList.add(Condition.damage);
								break;
							case 2:
								conditionList.add(Condition.lost);
								break;
							}
						}
					}
				});
				/***********************************************/

				JButton fix = new JButton("ȷ���޸�");
				BaseUI.myAdd(bpanel, submit, constraints, 0, 10, 2, 1);
				fix.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							TransportVO entruckvo = transportController
									.getTransport(entruckIDfield.getText());
							transportvo = transportController.newTransport(
									TransportType.Reception,
									consigneeIDfield.getText(),
									entruckvo.getDeparture(),
									entruckvo.getDestination(),
									entruckvo.getID(), datefield.getText(),
									vehicleIDfield.getText(),
									entruckvo.getTrafficType(),
									entruckvo.getfare(), entruckvo.getMember(),
									entruckvo.getOrder(),
									entruckvo.getCondition(),
									DocumentCondition.handing, userid);
							transportController.updateTransport(transportvo);
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "�޸�Ӫҵ�����ﵥ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				/***********************************************/
			}

		});

		fixConsigneePanel.add(bpanel);

	}

	private static void createSeekConsigneePanel() {
		seekConsigneePanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel consigneeID = new JLabel("Ӫҵ�����ﵥ���");
		JTextField consigneeIDfield = new JTextField(10);
		BaseUI.myAdd(bpanel, consigneeID, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, consigneeIDfield, constraints, 1, 0, 1, 1);

		JButton submit = new JButton("��ѯӪҵ�����ﵥ��Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 8, 2, 1);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					transportvo = transportController
							.getTransport(consigneeIDfield.getText());
					LogService ls = new LogManagementController();
					ls.addMessage(userid, "��ѯӪҵ�����ﵥ");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				submit.setText("ȷ��");
				bpanel.remove(consigneeID);
				bpanel.remove(consigneeIDfield);
				JLabel date = new JLabel("��������");
				JLabel consigneeID = new JLabel("Ӫҵ�����ﵥ");
				JLabel entruckID = new JLabel("װ�������");
				JLabel vehicleID = new JLabel("�������");
				JLabel departure = new JLabel("������");
				JLabel officer = new JLabel("����Ա");
				JLabel fee = new JLabel("�˷�");
				JButton seekExpress = new JButton("��ѯ���");
				seekExpress.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						String title = "��ѯ���";
						createSeekExpressPage();
						try {
							FreePagePane pagePane = FreeUtil
									.getPagePane(seekExpressPage);
							tab.setSelectedComponent(pagePane);
						} catch (Exception ex) {
							tab.addTab(title,
									OfficerUI.createPage(seekExpressPage));
							FreePagePane pagePane = FreeUtil
									.getPagePane(seekExpressPage);
							tab.setSelectedComponent(pagePane);
						}
					}
				});
				/***********************************************/

				JLabel datefield = new JLabel(transportvo.getTime());
				JLabel consigneeIDfield = new JLabel(transportvo.getID());
				JLabel entruckIDfield = new JLabel(transportvo.getTransportID());
				JLabel vehicleIDfield = new JLabel(transportvo.getTrafficID());
				JLabel departurefield = new JLabel(transportvo.getDeparture());
				JLabel officerfield = new JLabel(transportvo.getWriter());
				JLabel feefield = new JLabel(transportvo.getfare() + "");

				BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, consigneeID, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, entruckID, constraints, 0, 2, 1, 1);
				BaseUI.myAdd(bpanel, vehicleID, constraints, 0, 3, 1, 1);
				BaseUI.myAdd(bpanel, departure, constraints, 0, 4, 1, 1);
				BaseUI.myAdd(bpanel, officer, constraints, 0, 5, 1, 1);
				BaseUI.myAdd(bpanel, fee, constraints, 0, 6, 1, 1);
				BaseUI.myAdd(bpanel, seekExpress, constraints, 0, 7, 1, 1);

				BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, consigneeIDfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, entruckIDfield, constraints, 1, 2, 1, 1);
				BaseUI.myAdd(bpanel, vehicleIDfield, constraints, 1, 3, 1, 1);
				BaseUI.myAdd(bpanel, departurefield, constraints, 1, 4, 1, 1);
				BaseUI.myAdd(bpanel, officerfield, constraints, 1, 5, 1, 1);
				BaseUI.myAdd(bpanel, feefield, constraints, 1, 6, 1, 1);
			}
		});

		seekConsigneePanel.add(bpanel);
	}

	private static void createSeekExpressPage() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("��������");
		model.addColumn("Ӫҵ�����ﵥ���");
		model.addColumn("װ�������");
		model.addColumn("�������");
		model.addColumn("������");
		model.addColumn("����Ա");
		model.addColumn("�˷�");

		ArrayList<String> orderList = transportvo.getOrder();

		for (Iterator<String> i = orderList.iterator(); i.hasNext();) {
			Vector row = new Vector();
			row.add(transportvo.getTime());
			row.add(transportvo.getID());
			row.add(transportvo.getTransportID());
			row.add(transportvo.getTrafficID());
			row.add(i.next());
			row.add(transportvo.getDestination());
			row.add(transportvo.getWriter());
			row.add(transportvo.getfare());
			model.addRow(row);
		}

		FreeReportPage page = new FreeReportPage();
		page.getTable().setModel(model);
		page.setDescription("All Work Order Items by Part Number. Created "
				+ new Date().toString());
		setupPageToolbar(page);

		seekExpressPage = page;
	}
}
