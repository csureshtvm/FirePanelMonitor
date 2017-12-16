package com.vision.alarmmonitor.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.DateTextField;
import com.vision.alarmmonitor.util.WSCommUtil;

public class MaintenanceScheduleUI extends JPanel implements ItemListener{
	
	
	JPanel ppmPanel,ppmCheckPanel,notifyPanel;
	JPanel mainPanel;
	
	JCheckBox ppmChk[]=new JCheckBox[12];
	//String[] months=new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
	String[] months=new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	//JTextField txtFields[]=new JTextField[12];
	
	DateTextField dateFromTxtFlds[]=new DateTextField[12];
	DateTextField dateToTxtFlds[]=new DateTextField[12];
	
	
	JLabel dueNotfnLbl, notifyLbl;
	JComboBox ppmDueNotificationCombo;
	JCheckBox notifyChk[]=new JCheckBox[4];
	String[] notifyLabels=new String[]{"Customer","Engineering","Maintenance","Security"};
	
	JButton editBtn,saveBtn;
	
	
	JLabel bldgLbl;
	JComboBox chooseBuildingCombo=null;
	Vector<BuildingDTO> buildingList=new Vector<BuildingDTO>();
	JPanel jp;
	public MaintenanceScheduleUI(){
		
		ppmPanel=new JPanel();
		ppmPanel.setLayout(null);
		
		
		ppmCheckPanel=new JPanel();
		ppmCheckPanel.setLayout(null);
		
		notifyPanel=new JPanel();
		notifyPanel.setLayout(null);
		
		
		ppmPanel.add(ppmCheckPanel);
		ppmPanel.add(notifyPanel);
		ppmCheckPanel.setBounds(550, 50, 300, 400);
		notifyPanel.setBounds(10, 200, 450, 60);
		
		bldgLbl=new JLabel("Choose Building");
		chooseBuildingCombo=new JComboBox();
		bldgLbl.setBounds(100,0,100,20);
		chooseBuildingCombo.setBounds(220, 0, 250, 20);
		
		jp=new JPanel();
		jp.add(bldgLbl);
		jp.add(chooseBuildingCombo);
		
		
		int y=30;
		for(int i=0;i<ppmChk.length;i++){
			ppmChk[i]=new JCheckBox(months[i]);
			ppmChk[i].setHorizontalTextPosition(SwingConstants.LEFT);
			ppmChk[i].setHorizontalAlignment(SwingConstants.RIGHT);
			ppmCheckPanel.add(ppmChk[i]);
			ppmChk[i].setBounds(10, y, 60, 25);
			ppmChk[i].addItemListener(this);
			
			dateFromTxtFlds[i]=new DateTextField();
			ppmCheckPanel.add(dateFromTxtFlds[i]);
			dateFromTxtFlds[i].setBounds(80, y, 80, 25);
			dateFromTxtFlds[i].setVisible(false);
			//y+=30;		
			
			dateToTxtFlds[i]=new DateTextField();
			ppmCheckPanel.add(dateToTxtFlds[i]);
			dateToTxtFlds[i].setBounds(170, y, 80, 25);
			dateToTxtFlds[i].setVisible(false);
			y+=30;		
			
		}
		
		ppmDueNotificationCombo=new JComboBox(new String[]{"1","2","3","4","5","6","7"});
		dueNotfnLbl=new JLabel("PPM Due Notification");
		ppmPanel.add(ppmDueNotificationCombo);
		ppmPanel.add(dueNotfnLbl);
		dueNotfnLbl.setBounds(20, 150, 150, 25);
		ppmDueNotificationCombo.setBounds(180, 150, 50, 25);
		
		notifyLbl=new JLabel("Notify");
		notifyPanel.add(notifyLbl);
		notifyLbl.setBounds(20, 200, 60, 25);
		int x=10;
		for(int i=0;i<notifyChk.length;i++){
			notifyChk[i]=new JCheckBox(notifyLabels[i]);
			notifyChk[i].setHorizontalTextPosition(SwingConstants.LEFT);
			notifyChk[i].setHorizontalAlignment(SwingConstants.RIGHT);
			notifyPanel.add(notifyChk[i]);
			notifyChk[i].setBounds(x, 20, 100, 25);
			x+=100;
			
		}
		
		
		editBtn=new JButton("Edit");
		saveBtn=new JButton("Save");
		editBtn.setBounds(250, 450, 100, 20);
		saveBtn.setBounds(370, 450, 100, 20);
		ppmPanel.add(editBtn);
		ppmPanel.add(saveBtn);
		
		
		Border border1=BorderFactory.createLineBorder(Color.GRAY);
		ppmPanel.setBorder(border1);
		setLayout(null);
		add(ppmPanel);
		add(jp);
		ppmPanel.setBounds(100, 100, 900, 500);
		
		Border border2=BorderFactory.createTitledBorder("AMC PPM");
		ppmCheckPanel.setBorder(border2);
		
		
		Border border3=BorderFactory.createTitledBorder("Notify");
		notifyPanel.setBorder(border3);
		//addListeners();
		
		
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			ppmPanel.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
			ppmCheckPanel.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
			notifyPanel.setBackground(AppConstants.SCREEN_BG_COLOR);
			jp.setBackground(AppConstants.SCREEN_BG_COLOR);
		}
		setScreenPosition();
	}
	
	private void setScreenPosition(){
		AlarmPoint jp1topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 20, 5);
		AlarmPoint jp1bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 80, 40);
		
		int panelWidth=(jp1bottomRight.getX()-jp1topLeft.getX());
		int panelHeight=(jp1bottomRight.getY()-jp1topLeft.getY());
		jp.setBounds(jp1topLeft.getX(),jp1topLeft.getY(),(jp1bottomRight.getX()-jp1topLeft.getX()),(jp1bottomRight.getY()-jp1topLeft.getY()));
		//jsp.setBounds((int)(panelWidth*0.1),(int)(panelHeight*0.2),(int)(panelWidth*0.8),(int)(panelHeight*0.8));
		
		AlarmPoint p1topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 30, 45);
		AlarmPoint p1bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 70, 80);
		
		//scheduleAddUI.setBounds(p1topLeft.getX(),p1topLeft.getY(),(p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
		//scheduleAddUI.setScreenPosition((p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
	}
	
	public static void main(String[] args) {
		MaintenanceScheduleUI mainUI=new MaintenanceScheduleUI();
		mainUI.setBounds(0, 0, 1200, 800);
		mainUI.setVisible(true);
		
		JFrame frame=new JFrame();
		frame.add(mainUI);
		frame.setBounds(0, 0, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<ppmChk.length;i++){
			if(ie.getSource() == (ppmChk[i])){
				if(ppmChk[i].isSelected()){
					dateFromTxtFlds[i].setVisible(true);
					dateToTxtFlds[i].setVisible(true);
					
				}else{

					dateFromTxtFlds[i].setVisible(false);
					dateToTxtFlds[i].setVisible(false);
					
				}
			}
		}
		
	}
	private void addListeners() {
		ppmPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent paramMouseEvent) {
            	System.out.println("Clicked");
            	for(DateTextField tf:dateFromTxtFlds){
            		tf.setVisible(false);
            	}
            	for(DateTextField tf:dateToTxtFlds){
            		tf.setVisible(false);
            	}
            	
            }
        });
    }
	
	public void populateData(){
		String selectedBuildingName=chooseBuildingCombo.getSelectedIndex()>=0?chooseBuildingCombo.getSelectedItem().toString():null;
		
		if(buildingList!=null && WSCommUtil.buildingList!=null){
			buildingList.clear();
		}
		if(WSCommUtil.buildingList!=null){
		buildingList.addAll(WSCommUtil.buildingList);
		chooseBuildingCombo.removeAllItems();
		
		for(BuildingDTO bldg : buildingList){
			chooseBuildingCombo.addItem(bldg.getBuildingName());
			if(selectedBuildingName!=null && selectedBuildingName.equalsIgnoreCase(bldg.getBuildingName())){
				chooseBuildingCombo.setSelectedItem(bldg.getBuildingName());
			}
		}
		}
	}

}
