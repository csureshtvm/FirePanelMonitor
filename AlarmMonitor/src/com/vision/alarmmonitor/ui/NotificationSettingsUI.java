package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.dto.CustomerDTO;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class NotificationSettingsUI extends JPanel implements ItemListener{

	JLabel bldgLbl;
	JComboBox chooseBuildingCombo=null;
	Vector<BuildingDTO> buildingList=new Vector<BuildingDTO>();
	NotificationSettingsContactUI notfnCustomerUI,notfnMaintenenceUI,notfnEnggUI,notfnSecurityUI;
	public NotificationSettingsUI(){
		bldgLbl=new JLabel("Choose Building");
		chooseBuildingCombo=new JComboBox();
		
		notfnCustomerUI=new NotificationSettingsContactUI("Customer");
		notfnMaintenenceUI=new NotificationSettingsContactUI("maintenance");
		notfnEnggUI=new NotificationSettingsContactUI("engineering");
		notfnSecurityUI=new NotificationSettingsContactUI("security");
		Border border1=BorderFactory.createTitledBorder("Customer Notification");
		Border border2=BorderFactory.createTitledBorder("Maintenance Notification");
		Border border3=BorderFactory.createTitledBorder("Engineering Notification");
		Border border4=BorderFactory.createTitledBorder("Security Notification");
		
		//border.
		notfnCustomerUI.setBorder(border1);
		notfnMaintenenceUI.setBorder(border2);
		notfnEnggUI.setBorder(border3);
		notfnSecurityUI.setBorder(border4);
		add(bldgLbl);
		add(notfnCustomerUI);
		add(notfnMaintenenceUI);
		add(notfnEnggUI);
		add(notfnSecurityUI);
		add(chooseBuildingCombo);
		setLayout(null);
		
		//bldgLbl.setBounds(200, 20, 150, 20);
		//chooseBuildingCombo.setBounds(330, 20, 150, 20);
		bldgLbl.setBounds((AlarmMonitorMainUI.width/2)-150, 20, 150, 20);
		chooseBuildingCombo.setBounds((AlarmMonitorMainUI.width/2), 20, 250, 20);
		chooseBuildingCombo.addItemListener(this);
		
		AlarmPoint p1topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 10, 10);
		AlarmPoint p1bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 48, 46);
		
		AlarmPoint p2topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 52, 10);
		AlarmPoint p2bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 90, 46);
		
		AlarmPoint p3topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 10, 49);
		AlarmPoint p3bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 48, 85);
		
		AlarmPoint p4topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 52, 49);
		AlarmPoint p4bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 90, 85);
		
		notfnCustomerUI.setBounds(p1topLeft.getX(),p1topLeft.getY(),(p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
		notfnMaintenenceUI.setBounds(p2topLeft.getX(),p2topLeft.getY(),(p2bottomRight.getX()-p2topLeft.getX()),(p2bottomRight.getY()-p2topLeft.getY()));
		notfnEnggUI.setBounds(p3topLeft.getX(),p3topLeft.getY(),(p3bottomRight.getX()-p3topLeft.getX()),(p3bottomRight.getY()-p3topLeft.getY()));
		notfnSecurityUI.setBounds(p4topLeft.getX(),p4topLeft.getY(),(p4bottomRight.getX()-p4topLeft.getX()),(p4bottomRight.getY()-p4topLeft.getY()));
		
		notfnCustomerUI.setScreenPosition((p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
		notfnMaintenenceUI.setScreenPosition((p2bottomRight.getX()-p2topLeft.getX()),(p2bottomRight.getY()-p2topLeft.getY()));
		notfnEnggUI.setScreenPosition((p3bottomRight.getX()-p3topLeft.getX()),(p3bottomRight.getY()-p3topLeft.getY()));
		notfnSecurityUI.setScreenPosition((p4bottomRight.getX()-p4topLeft.getX()),(p4bottomRight.getY()-p4topLeft.getY()));
		if(AppConstants.BG_COLOR_SHOW){
		setBackground(AppConstants.SCREEN_BG_COLOR); 
		}
		
		
		
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

	@Override
	public void itemStateChanged(ItemEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == chooseBuildingCombo){
			if(chooseBuildingCombo.getSelectedIndex()>=0 && buildingList!=null && chooseBuildingCombo.getSelectedIndex()<buildingList.size()){
			BuildingDTO selectedBldg=buildingList.get(chooseBuildingCombo.getSelectedIndex());
			CustomerDTO custDTO=WSCommUtil.customerDetails;
			if(custDTO!=null){
			notfnCustomerUI.populateData(custDTO.getContactName(),custDTO.getPhonePrimary(),custDTO.getEmail(),selectedBldg.getNotifyCustomerEmail(),selectedBldg.getNotifyCustomerSMS(),selectedBldg.getBuildingId());
			}
			notfnMaintenenceUI.populateData(selectedBldg.getMaintenanceContactName(),selectedBldg.getMaintenancePhonePrimary(),selectedBldg.getMaintenanceEmail(),selectedBldg.getNotifyMaintenanceContactEmail(),selectedBldg.getNotifyMaintenanceContactSMS(),selectedBldg.getBuildingId());
			notfnEnggUI.populateData(selectedBldg.getEnggContactName(),selectedBldg.getEnggPhonePrimary(),selectedBldg.getEnggEmail(),selectedBldg.getNotifyEnggContactEmail(),selectedBldg.getNotifyEnggContactSMS(),selectedBldg.getBuildingId());
			notfnSecurityUI.populateData(selectedBldg.getSecurityContactName(),selectedBldg.getSecurityPhonePrimary(),selectedBldg.getSecurityEmail(),selectedBldg.getNotifySecurityContactEmail(),selectedBldg.getNotifySecurityContactSMS(),selectedBldg.getBuildingId());
			
			}
		}
		
	}
}
