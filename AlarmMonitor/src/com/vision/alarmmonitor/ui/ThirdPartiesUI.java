package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class ThirdPartiesUI extends JPanel implements ItemListener{

	JLabel bldgLbl;
	JComboBox chooseBuildingCombo=null;
	ThirPartyContactUI maintenanceUI,enggUI,securityUI;
	
	Vector<BuildingDTO> buildingList=new Vector<BuildingDTO>();
	public ThirdPartiesUI(){
		bldgLbl=new JLabel("Choose Building");
		chooseBuildingCombo=new JComboBox();
		
		maintenanceUI=new ThirPartyContactUI("maintenance");
		enggUI=new ThirPartyContactUI("engineering");
		securityUI=new ThirPartyContactUI("security");
		Border border1=BorderFactory.createTitledBorder("Maintenance Contact");
		Border border2=BorderFactory.createTitledBorder("Engineering Contact");
		Border border3=BorderFactory.createTitledBorder("Security Contact");
		
		//border.
		maintenanceUI.setBorder(border1);
		enggUI.setBorder(border2);
		securityUI.setBorder(border3);
		add(bldgLbl);
		add(chooseBuildingCombo);
		add(maintenanceUI);
		add(enggUI);
		add(securityUI);
		setLayout(null);
		
		System.out.println("Panel width and height:"+AlarmMonitorMainUI.screenWidth+":"+AlarmMonitorMainUI.screenHeight);
		
		bldgLbl.setBounds((AlarmMonitorMainUI.width/2)-150, 20, 150, 20);
		chooseBuildingCombo.setBounds((AlarmMonitorMainUI.width/2), 20, 250, 20);
		AlarmPoint p1topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 10, 10);
		AlarmPoint p1bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 48, 46);
		
		AlarmPoint p2topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 52, 10);
		AlarmPoint p2bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 90, 46);
		
		AlarmPoint p3topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 30, 48);
		AlarmPoint p3bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 70, 86);
		
		maintenanceUI.setBounds(p1topLeft.getX(),p1topLeft.getY(),(p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
		enggUI.setBounds(p2topLeft.getX(),p2topLeft.getY(),(p2bottomRight.getX()-p2topLeft.getX()),(p2bottomRight.getY()-p2topLeft.getY()));
		securityUI.setBounds(p3topLeft.getX(),p3topLeft.getY(),(p3bottomRight.getX()-p3topLeft.getX()),(p3bottomRight.getY()-p3topLeft.getY()));
		
		maintenanceUI.setScreenPosition((p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
		enggUI.setScreenPosition((p2bottomRight.getX()-p2topLeft.getX()),(p2bottomRight.getY()-p2topLeft.getY()));
		securityUI.setScreenPosition((p3bottomRight.getX()-p3topLeft.getX()),(p3bottomRight.getY()-p3topLeft.getY()));
		
		chooseBuildingCombo.addItemListener(this);
		if(AppConstants.BG_COLOR_SHOW){
		setBackground(AppConstants.SCREEN_BG_COLOR); 
		}
		
		
	}
	@Override
	public void itemStateChanged(ItemEvent event) {
		if(event.getSource() == chooseBuildingCombo){
			if(chooseBuildingCombo.getSelectedIndex()>=0 && buildingList!=null && chooseBuildingCombo.getSelectedIndex()<buildingList.size()){
			BuildingDTO selectedBldg=buildingList.get(chooseBuildingCombo.getSelectedIndex());
			maintenanceUI.populateData(selectedBldg);
			securityUI.populateData(selectedBldg);
			enggUI.populateData(selectedBldg);
			}
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

}
