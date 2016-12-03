package managerui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sun.glass.ui.TouchInputSupport;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DoubleDV;

import dataserviceimpl.DataFactory;
import enums.ResultMessage;
import financeslservice.CostService;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeTabbedPane;
import free.FreeUtil;
import strategysl.Constant;
import strategysl.ConstantController;
import strategyslservice.ConstantService;
import sun.swing.SwingUtilities2.Section;
import twaver.SummingAlarmPropagator;
import twaver.base.A.E.c;
import usersl.LogManagementController;
import userslservice.LogService;
import vo.ConstantVO;

public class ConstantPanel {
	public static FreePagePane fixConstantPanel;
	public static FreeTabbedPane tab;
	public static ConstantService constantService;
	public static FreePagePane constantPage;
	public static String userId;

	public static FreePagePane createConstantPage(FreeTabbedPane tab,String Id) {
		userId=Id;
		constantService=new ConstantController();
		ConstantPanel.tab=tab;
		// TODO Auto-generated method stub
		return createReportPage();
	}

	private static FreePagePane createReportPage() {
		
		ConstantVO constantVO=null;
		ResultMessage resultMessage=ResultMessage.SUCCESS;
		try{
		constantVO=constantService.getConstant();
		}catch(Exception exception){
			exception.printStackTrace();
			resultMessage=ResultMessage.FAIL;
		}
		
		
		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(20,30,10,30);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);
		
		if(resultMessage==ResultMessage.SUCCESS){
		JLabel lengthOfBN=new JLabel("�������Ͼ��ľ���:  "+constantVO.getLengthOfBN());
		JLabel lengthOfBS=new JLabel("�������Ϻ��ľ���:  "+constantVO.getLengthOfBS());
		JLabel lengthOfBG=new JLabel("���������ݵľ���:  "+constantVO.getLengthOfBG());
		JLabel lengthOfSG=new JLabel("�Ϻ������ݵľ���:  "+constantVO.getLengthOfSG());
		JLabel lengthOfSN=new JLabel("�Ϻ����Ͼ��ľ���:  "+constantVO.getLengthOfSN());
		JLabel lengthOfGN=new JLabel("���ݵ��Ͼ��ľ���:  "+constantVO.getLengthOfSN());
		JLabel lengthOfHall=new JLabel("���м�Ӫҵ���ľ���:  "+constantVO.getLengthOfHall());

		JLabel priceOfCheapert=new JLabel("���ÿ��:  "+constantVO.getPriceOfCheapest());
		JLabel priceOfStandard=new JLabel("��׼���:  "+constantVO.getPriceOfStandard());
		JLabel priceOfExpress=new JLabel("�ؿ���:  "+constantVO.getPriceOfExpress());
		JLabel costOfCar=new JLabel("�����ɱ�:  "+constantVO.getCostOfCar());
		JLabel costOfTrain=new JLabel("�𳵳ɱ�:  "+constantVO.getCostOfTrain());
		JLabel costOfAir=new JLabel("�ɻ��ɱ�:  "+constantVO.getCostOfAir());
		JLabel priceOfCarton=new JLabel("ֽ�����:  "+constantVO.getPriceOfCarton());
		JLabel priceOfWood=new JLabel("ľ�����:  "+constantVO.getPriceOfWood());
		JLabel priceOfBag=new JLabel("��װ������:  "+constantVO.getPriceOfBag());

		

		BaseUI.myAdd(bpanel,lengthOfBN,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,lengthOfBS,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,lengthOfBG,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,lengthOfSG,constraints,0,3,1,1);
		BaseUI.myAdd(bpanel,lengthOfSN,constraints,0,4,1,1);
		BaseUI.myAdd(bpanel,lengthOfGN,constraints,0,5,1,1);
		BaseUI.myAdd(bpanel,lengthOfHall,constraints,0,6,1,1);
		BaseUI.myAdd(bpanel, priceOfCheapert, constraints, 2, 0, 1, 1);
		BaseUI.myAdd(bpanel, priceOfStandard, constraints, 2, 1, 1, 1);
		BaseUI.myAdd(bpanel, priceOfExpress, constraints, 2, 2, 1, 1);
		BaseUI.myAdd(bpanel, costOfCar, constraints, 2, 3, 1, 1);
		BaseUI.myAdd(bpanel, costOfTrain, constraints, 2, 4, 1, 1);
		BaseUI.myAdd(bpanel, costOfAir, constraints, 2,5, 1, 1);
		BaseUI.myAdd(bpanel, priceOfCarton, constraints, 2, 6, 1, 1);
		BaseUI.myAdd(bpanel, priceOfWood, constraints, 2, 7, 1, 1);
		BaseUI.myAdd(bpanel, priceOfBag, constraints, 2, 8, 1, 1);
		}else 
			JOptionPane.showMessageDialog(null, "����ʧ��");

		JButton fixConstant=new JButton("�޸ĳ���");
		fixConstant.addMouseListener(new MouseListener() {
			
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
				String title="�޸ĳ���";                
                try{
                 FreePagePane pagePane = FreeUtil.getPagePane(fixConstantPanel);
                 tab.setSelectedComponent(pagePane);
                }catch(Exception ex){
                    createFixConstantPanel();
             	    tab.addTab(title, ManagerUI.createPage(fixConstantPanel));
                    FreePagePane pagePane = FreeUtil.getPagePane(fixConstantPanel);
                    tab.setSelectedComponent(pagePane);
		    }	
			}
		});
		BaseUI.myAdd(bpanel,fixConstant,constraints,0,8,1,1);
		constantPage=new FreePagePane();
		constantPage.add(bpanel);
		return constantPage;
	}

	private static void createFixConstantPanel(){
		fixConstantPanel=new FreePagePane();
		JLabel lengthOfBN=new JLabel("�������Ͼ��ľ���");
		JLabel lengthOfBS=new JLabel("�������Ϻ��ľ���");
		JLabel lengthOfBG=new JLabel("���������ݵľ���");
		JLabel lengthOfSG=new JLabel("�Ϻ������ݵľ���");
		JLabel lengthOfSN=new JLabel("�Ϻ����Ͼ��ľ���");
		JLabel lengthOfGN=new JLabel("���ݵ��Ͼ��ľ���");
		JLabel lengthOfHall=new JLabel("���м�Ӫҵ���ľ���");

		JTextField BNField=new JTextField(5);
		JTextField BSField=new JTextField(5);
		JTextField BGField=new JTextField(5);
		JTextField SGField=new JTextField(5);
		JTextField SNField=new JTextField(5);
		JTextField GNField=new JTextField(5);
		JTextField HallField=new JTextField(5);

		JLabel priceOfCheapert=new JLabel("���ÿ��");
		JLabel priceOfStandard=new JLabel("��׼���");
		JLabel priceOfExpress=new JLabel("�ؿ���");
		JLabel costOfCar=new JLabel("�����ɱ�");
		JLabel costOfTrain=new JLabel("�𳵳ɱ�");
		JLabel costOfAir=new JLabel("�ɻ��ɱ�");
		JLabel priceOfCarton=new JLabel("ֽ�����");
		JLabel priceOfWood=new JLabel("ľ�����");
		JLabel priceOfBag=new JLabel("��װ������");

		JTextField pcField=new JTextField(5);
		JTextField psField=new JTextField(5);
		JTextField peField=new JTextField(5);
		JTextField ccField=new JTextField(5);
		JTextField ctField=new JTextField(5);
		JTextField caField=new JTextField(5);
		JTextField ptField=new JTextField(5);
		JTextField pwField=new JTextField(5);
		JTextField pbField=new JTextField(5);

		GridBagLayout gb=new GridBagLayout(); 
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets=new Insets(20,0,10,0);
		JPanel bpanel = new JPanel();
		bpanel.setLayout(gb);

		BaseUI.myAdd(bpanel,lengthOfBN,constraints,0,0,1,1);
		BaseUI.myAdd(bpanel,lengthOfBS,constraints,0,1,1,1);
		BaseUI.myAdd(bpanel,lengthOfBG,constraints,0,2,1,1);
		BaseUI.myAdd(bpanel,lengthOfSG,constraints,0,3,1,1);
		BaseUI.myAdd(bpanel,lengthOfSN,constraints,0,4,1,1);
		BaseUI.myAdd(bpanel,lengthOfGN,constraints,0,5,1,1);
		BaseUI.myAdd(bpanel,lengthOfHall,constraints,0,6,1,1);
		BaseUI.myAdd(bpanel, priceOfCheapert, constraints, 2, 0, 1, 1);
		BaseUI.myAdd(bpanel, priceOfStandard, constraints, 2, 1, 1, 1);
		BaseUI.myAdd(bpanel, priceOfExpress, constraints, 2, 2, 1, 1);
		BaseUI.myAdd(bpanel, costOfCar, constraints, 2, 3, 1, 1);
		BaseUI.myAdd(bpanel, costOfTrain, constraints, 2, 4, 1, 1);
		BaseUI.myAdd(bpanel, costOfAir, constraints, 2,5, 1, 1);
		BaseUI.myAdd(bpanel, priceOfCarton, constraints, 2, 6, 1, 1);
		BaseUI.myAdd(bpanel, priceOfWood, constraints, 2, 7, 1, 1);
		BaseUI.myAdd(bpanel, priceOfBag, constraints, 2, 8, 1, 1);
		BaseUI.myAdd(bpanel,BSField,constraints,1,0,1,1);
		BaseUI.myAdd(bpanel,BNField,constraints,1,1,1,1);
		BaseUI.myAdd(bpanel,BGField,constraints,1,2,1,1);
		BaseUI.myAdd(bpanel,SGField,constraints,1,3,1,1);
		BaseUI.myAdd(bpanel,SNField,constraints,1,4,1,1);
		BaseUI.myAdd(bpanel,GNField,constraints,1,5,1,1);
		BaseUI.myAdd(bpanel,HallField,constraints,1,6,1,1);
		BaseUI.myAdd(bpanel, pcField, constraints, 3, 0, 1, 1);
		BaseUI.myAdd(bpanel, psField, constraints, 3, 1, 1, 1);
		BaseUI.myAdd(bpanel, peField, constraints, 3, 2, 1, 1);
		BaseUI.myAdd(bpanel, ccField, constraints, 3, 3, 1, 1);
		BaseUI.myAdd(bpanel, ctField, constraints, 3, 4, 1, 1);
		BaseUI.myAdd(bpanel, caField, constraints, 3, 5, 1, 1);
		BaseUI.myAdd(bpanel, ptField, constraints, 3, 6, 1, 1);
		BaseUI.myAdd(bpanel, pwField, constraints, 3, 7, 1, 1);
		BaseUI.myAdd(bpanel, pbField, constraints, 3, 8, 1, 1);
		


		JButton submit=new JButton("�ύ");
		submit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				
				
				ConstantVO constantVO=new ConstantVO();
				try{
				constantVO.setLengthOfBS(Double.valueOf(BSField.getText()));
				constantVO.setLengthOfBG(Double.valueOf(BGField.getText()));
				constantVO.setLengthOfBN(Double.valueOf(BNField.getText()));
				constantVO.setLengthOfGN(Double.valueOf(GNField.getText()));
				constantVO.setLengthOfHall(Double.valueOf(HallField.getText()));
				constantVO.setLengthOfSG(Double.valueOf(SGField.getText()));
				constantVO.setLengthOfSN(Double.valueOf(SNField.getText()));
				constantVO.setPriceOfCheapest(Double.valueOf(pcField.getText()));
				constantVO.setPriceOfStandard(Double.valueOf(psField.getText()));
				constantVO.setPriceOfExpress(Double.valueOf(peField.getText()));
				constantVO.setCostOfAir(Double.valueOf(caField.getText()));
				constantVO.setCostOfTrain(Double.valueOf(ctField.getText()));
				constantVO.setCostOfCar(Double.valueOf(ccField.getText()));
				constantVO.setPriceOfBag(Double.valueOf(pbField.getText()));
				constantVO.setPriceOfWood(Double.valueOf(pwField.getText()));
				constantVO.setPriceOfCarton(Double.valueOf(ptField.getText()));
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "��Ϣ��д��������");
					return;
				}
				ResultMessage resultMessage=ResultMessage.SUCCESS;
				try{
					resultMessage =constantService.save(constantVO);
				}catch(Exception exception){
					exception.printStackTrace();
					resultMessage=ResultMessage.FAIL;
				}
				if (resultMessage==ResultMessage.SUCCESS) {
					JOptionPane .showMessageDialog(null, "�޸ĳɹ�");
					tab.remove(FreeUtil.getPagePane(fixConstantPanel));
					//tab.remove(FreeUtil.getPagePane(constantPage));
					
	     	    	  LogService ls=new LogManagementController();
	     	    	  ls.addMessage(userId, "�ƶ�����");
	
				}else{
					JOptionPane.showMessageDialog(null, "�޸�ʧ�ܣ�������������");
				}
			}
		});
		BaseUI.myAdd(bpanel,submit,constraints,0,8,1,1);
		fixConstantPanel.add(bpanel);
	}

}
