package com.vision.alarmmonitor.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class AlarmDevicesEditUI extends JFrame implements ActionListener{

	
	JTextField deviceIdTxt,deviceNameTxt,buildingNameTxt,deviceLocTxt,floorNoTxt;
	JLabel deviceIdLbl,deviceNameLbl,buildingNameLbl,deviceLocLbl,floorNoLbl;
	String buildingId,deviceId,deviceName,deviceBldgName,deviceLoc,deviceFloorNo;
	JPanel jp=null;
	JButton saveBtn,cancelBtn;
	public AlarmDevicesEditUI(String buildingId,String deviceId,String deviceName,String deviceBldgName,String deviceLoc,String deviceFloorNo){
		this.buildingId=buildingId;
		this.deviceId=deviceId;
		this.deviceName=deviceName;
		this.deviceBldgName=deviceBldgName;
		this.deviceLoc=deviceLoc;
		this.deviceFloorNo=deviceFloorNo;
		
		Container c=getContentPane();
		jp=new JPanel();
		
		deviceIdLbl=new JLabel("Device ID");
		deviceNameLbl=new JLabel("Device Name");
		buildingNameLbl=new JLabel("Building Name");
		deviceLocLbl=new JLabel("Device Location");
		floorNoLbl=new JLabel("Floor No");
		
		deviceIdTxt=new JTextField(deviceId);
		buildingNameTxt=new JTextField(deviceBldgName);
		deviceNameTxt=new JTextField(deviceName);
		deviceLocTxt=new JTextField(deviceLoc);		
		floorNoTxt=new JTextField(deviceFloorNo);
		
		saveBtn=new JButton("Save");
		cancelBtn=new JButton("Close");
		
		
		c.add(jp);
		c.setLayout(null);

		
		jp.setLayout(null);
		jp.add(deviceIdLbl);
		jp.add(deviceNameLbl);
		jp.add(buildingNameLbl);
		jp.add(deviceLocLbl);
		jp.add(floorNoLbl);
		jp.add(deviceIdTxt);
		jp.add(buildingNameTxt);
		jp.add(deviceNameTxt);
		jp.add(deviceLocTxt);
		jp.add(floorNoTxt);
		jp.add(saveBtn);
		jp.add(cancelBtn);
		
		deviceIdLbl.setBounds(10,10,150,20);
		buildingNameLbl.setBounds(10,50,150,20);
		deviceNameLbl.setBounds(10,90,150,20);
		deviceLocLbl.setBounds(10,130,150,20);
		floorNoLbl.setBounds(10,170,150,20);
				
		deviceIdTxt.setBounds(180,10,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		buildingNameTxt.setBounds(180,50,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		deviceNameTxt.setBounds(180,90,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		deviceLocTxt.setBounds(180,130,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		floorNoTxt.setBounds(180,170,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		saveBtn.setBounds(80, 210, 100, 20);
		cancelBtn.setBounds(200, 210, 100, 20);
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		deviceIdTxt.setEditable(false);
		buildingNameTxt.setEditable(false);
		
		jp.setBounds(65,20,500,300);
		setScreenPosition();
		if(AppConstants.BG_COLOR_SHOW){
			c.setBackground(AppConstants.SCREEN_BG_COLOR);
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			jp.setBackground(AppConstants.SCREEN_BG_COLOR); 
			
		}
		setResizable(false);
		setVisible(true);
		
	}
	
	private void setScreenPosition(){
		
		
		AlarmPoint topLeftPanel=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth,AlarmMonitorMainUI.screenHeight,30,30);
		AlarmPoint bottomRightPanel=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth,AlarmMonitorMainUI.screenHeight,70,70);
		int frameWidth=(bottomRightPanel.getX()-topLeftPanel.getX());
		int frameHeight=(bottomRightPanel.getY()-topLeftPanel.getY());
		setBounds(topLeftPanel.getX(), topLeftPanel.getY(),frameWidth , frameHeight);//(bottomRightP1.getY()-topLeftP1.getY())
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==saveBtn){
			if(WSCommUtil.saveAlarmDevice(buildingId, deviceId, deviceNameTxt.getText(), deviceLocTxt.getText(), floorNoTxt.getText())){
				JOptionPane.showMessageDialog(this, "Alarm device Details updated Successfully","Device Update Status",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				AlarmMonitor.getInstance().lastAlarmEventRefreshTime=0;
			}else{
				JOptionPane.showMessageDialog(this, "Device Details updation Failed. Please try again!!!","Device Update Status",JOptionPane.ERROR_MESSAGE);
				
			}
			
		}else if(ae.getSource()==cancelBtn){
			setVisible(false);
		}
		
	}
}
