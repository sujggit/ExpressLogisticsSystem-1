package officerui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
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

import pamanagementsl.PManagementController;
import main.StaffInfoPanel;
import auditsl.AuditInfo;
import sendsl.SendController;
import sendslservice.SendService;
import twaver.TWaverUtil;
import usersl.LogManagementController;
import userslservice.LogService;
import vo.ReceiptsVO;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;

public class GatherManagementPanel {
	public static JPanel addGatherPanel, deleteGatherPanel, fixGatherPanel,
			seekGatherPanel;
	public static JTabbedPane tab;
	private static SendService send;
	private static AuditInfo audit;
	private static String userid;
	private static String userplace;
	private static String today;
	private static ReceiptsVO receiptsvo;

	public static FreeReportPage createGatherManagementPage(JTabbedPane tab) {
		send = new SendController();
		audit = new SendController();

		userid = StaffInfoPanel.userVO.getAccountnumber();
		PManagementController pmc = new PManagementController();
		userplace = pmc.select(userid).getWorkPlaceNumber();
		today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		GatherManagementPanel.tab = tab;
		return createReportPage();
	}

	private static FreeReportPage createReportPage() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("�տ�����");
		model.addColumn("�տ���");
		model.addColumn("���Ա");
		model.addColumn("Ӫҵ��");

		ArrayList<ReceiptsVO> voList = send.findReceipts(today, userplace);
		for (Iterator<ReceiptsVO> i = voList.iterator(); i.hasNext();) {
			receiptsvo = i.next();
			Vector row = new Vector();
			row.add(receiptsvo.getDate());
			row.add(receiptsvo.getFee());
			row.add(receiptsvo.getCourier());
			row.add(receiptsvo.getOffice());
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
		FreeToolbarButton addGather, deleteGather, fixGather, seekGather;
		addGather = createButton("/free/test/add.png", "�����տ", true);
		deleteGather = createButton("/free/test/update.png", "ɾ���տ", true);
		fixGather = createButton("/free/test/refresh.png", "�޸��տ", true);
		seekGather = createButton("/free/test/print.png", "�����տ", true);
		page.getRightToolBar().add(addGather);
		page.getRightToolBar().add(deleteGather);
		page.getRightToolBar().add(fixGather);
		page.getRightToolBar().add(seekGather);

		addGather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = addGather.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(addGatherPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createAddGatherPanel();
					tab.addTab(title, OfficerUI.createPage(addGatherPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(addGatherPanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		deleteGather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = deleteGather.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteGatherPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createDeleteGatherPanel();
					tab.addTab(title, OfficerUI.createPage(deleteGatherPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(deleteGatherPanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		fixGather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = fixGather.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixGatherPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createFixGatherPanel();
					tab.addTab(title, OfficerUI.createPage(fixGatherPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(fixGatherPanel);
					tab.setSelectedComponent(pagePane);
				}

			}
		});

		seekGather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String title = seekGather.getToolTipText();
				try {
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekGatherPanel);
					tab.setSelectedComponent(pagePane);
				} catch (Exception ex) {
					createSeekGatherPanel();
					tab.addTab(title, OfficerUI.createPage(seekGatherPanel));
					FreePagePane pagePane = FreeUtil
							.getPagePane(seekGatherPanel);
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

	private static void createAddGatherPanel() {
		addGatherPanel = new JPanel();
		JLabel date = new JLabel("�տ�����");
		JLabel workplace = new JLabel("Ӫҵ��");
		JLabel fee = new JLabel("�տ���");

		JTextField datefield = new JTextField(10);
		JTextField workplacefield = new JTextField(10);
		JTextField feefield = new JTextField(10);

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, workplace, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, fee, constraints, 0, 2, 1, 1);

		BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, workplacefield, constraints, 1, 1, 1, 1);
		BaseUI.myAdd(bpanel, feefield, constraints, 1, 2, 1, 1);

		JButton submit = new JButton("�ύ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 5, 2, 1);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					send.newReceipts(datefield.getText(), workplacefield.getText());
					LogService ls = new LogManagementController();
					ls.addMessage(userid, "�����տ");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		/***********************************************/

		addGatherPanel.add(bpanel);
	}

	private static void createDeleteGatherPanel() {
		deleteGatherPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel date = new JLabel("�տ�����");
		JLabel courier = new JLabel("���Ա");
		JTextField datefield = new JTextField(10);
		JTextField courierfield = new JTextField(10);
		BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, courier, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, courierfield, constraints, 1, 1, 1, 1);

		JButton submit = new JButton("��ѯɾ���տ��Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 5, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					receiptsvo = send.findReceipt(datefield.getText(),
							courierfield.getText());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				bpanel.remove(submit);
				bpanel.remove(date);
				bpanel.remove(courier);
				bpanel.remove(datefield);
				bpanel.remove(courierfield);
				JLabel date = new JLabel("�տ�����");
				JLabel courier = new JLabel("���Ա");
				JLabel fee = new JLabel("�տ���");

				JLabel datefield = new JLabel(receiptsvo.getDate());
				JLabel courierfield = new JLabel(receiptsvo.getCourier());
				JLabel feefield = new JLabel(receiptsvo.getFee() + "");

				BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, courier, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, fee, constraints, 0, 2, 1, 1);

				BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, courierfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, feefield, constraints, 1, 2, 1, 1);

				JButton delete = new JButton("ȷ��ɾ��");
				BaseUI.myAdd(bpanel, delete, constraints, 0, 5, 2, 1);

				delete.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							send.deleteReceipts(receiptsvo.getDate(),
									receiptsvo.getCourier());
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "ɾ���տ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				/***********************************************/
			}
		});

		deleteGatherPanel.add(bpanel);
	}

	private static void createFixGatherPanel() {
		fixGatherPanel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel date = new JLabel("�տ�����");
		JLabel courier = new JLabel("���Ա");
		JTextField datefield = new JTextField(10);
		JTextField courierfield = new JTextField(10);
		BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, courier, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, courierfield, constraints, 1, 1, 1, 1);

		JButton submit = new JButton("��ѯ�տ��Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 5, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					receiptsvo = send.findReceipt(datefield.getText(),
							courierfield.getText());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				bpanel.remove(date);
				bpanel.remove(datefield);
				bpanel.remove(courier);
				bpanel.remove(courierfield);
				bpanel.remove(submit);

				JLabel date = new JLabel("�տ�����");
				JLabel courier = new JLabel("���Ա");
				JLabel fee = new JLabel("�տ���");

				JTextField datefield = new JTextField(10);
				JTextField courierfield = new JTextField(10);
				JTextField feefield = new JTextField(10);
				
				datefield.setText(receiptsvo.getDate());
				courierfield.setText(receiptsvo.getCourier());
				feefield.setText(receiptsvo.getFee()+"");

				BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, courier, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, fee, constraints, 0, 2, 1, 1);

				BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, courierfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, feefield, constraints, 1, 2, 1, 1);

				JButton fix = new JButton("ȷ���޸�");
				BaseUI.myAdd(bpanel, fix, constraints, 0, 5, 2, 1);
				fix.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						receiptsvo.setData(datefield.getText());
						receiptsvo.setCourier(courierfield.getText());
						receiptsvo.setFee(Double.parseDouble(feefield.getText()));
						try {
							send.updateReceipts(receiptsvo);
							LogService ls = new LogManagementController();
							ls.addMessage(userid, "�޸��տ");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				/***********************************************/
			}

		});

		fixGatherPanel.add(bpanel);

	}

	private static void createSeekGatherPanel() {
		seekGatherPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25, 0, 25, 0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		JLabel date = new JLabel("�տ�����");
		JLabel courier = new JLabel("���Ա");
		JTextField datefield = new JTextField(10);
		JTextField courierfield = new JTextField(10);
		BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
		BaseUI.myAdd(bpanel, courier, constraints, 0, 1, 1, 1);
		BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
		BaseUI.myAdd(bpanel, courierfield, constraints, 1, 1, 1, 1);

		JButton submit = new JButton("��ѯ�տ��Ϣ");
		BaseUI.myAdd(bpanel, submit, constraints, 0, 5, 2, 1);

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					receiptsvo = send.findReceipt(datefield.getText(),
							courierfield.getText());
					LogService ls = new LogManagementController();
					ls.addMessage(userid, "��ѯ�տ");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				submit.setText("ȷ��");
				bpanel.remove(date);
				bpanel.remove(courier);
				bpanel.remove(datefield);
				bpanel.remove(courierfield);
				JLabel date = new JLabel("�տ�����");
				JLabel courier = new JLabel("���Ա");
				JLabel fee = new JLabel("�տ���");

				JLabel datefield = new JLabel(receiptsvo.getDate());
				JLabel courierfield = new JLabel(receiptsvo.getCourier());
				JLabel feefield = new JLabel(receiptsvo.getFee() + "");

				BaseUI.myAdd(bpanel, date, constraints, 0, 0, 1, 1);
				BaseUI.myAdd(bpanel, courier, constraints, 0, 1, 1, 1);
				BaseUI.myAdd(bpanel, fee, constraints, 0, 2, 1, 1);

				BaseUI.myAdd(bpanel, datefield, constraints, 1, 0, 1, 1);
				BaseUI.myAdd(bpanel, courierfield, constraints, 1, 1, 1, 1);
				BaseUI.myAdd(bpanel, feefield, constraints, 1, 2, 1, 1);
			}
		});

		seekGatherPanel.add(bpanel);
	}
}
