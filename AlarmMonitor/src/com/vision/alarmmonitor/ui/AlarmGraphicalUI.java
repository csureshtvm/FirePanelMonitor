package com.vision.alarmmonitor.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.model.AlarmEventGraphicalDetals;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.WSCommUtil;

public class AlarmGraphicalUI extends JPanel{

	 AlarmGraphicalBuildingUI graphicalUI1=null;
	 AlarmGraphicalBuildingUI graphicalUI2=null;
	 
	 public AlarmGraphicalUI(){
		 graphicalUI1=new AlarmGraphicalBuildingUI();
		 graphicalUI2=new AlarmGraphicalBuildingUI();
		 
		 setLayout(null);
		 setScreenPosition();
		 
	 }
	 
	 private void setScreenPosition(){
			int width=AlarmMonitorMainUI.width;
			int height=AlarmMonitorMainUI.height;
			AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,10,10);
			AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,50,90);
			graphicalUI1.setBounds(topLeftP1.getX(), topLeftP1.getY(), (bottomRightP1.getX()-topLeftP1.getX()), bottomRightP1.getY()-topLeftP1.getY());//(bottomRightP1.getY()-topLeftP1.getY())
			
			
			AlarmPoint topLeftP2=AlarmUtil.getWidowScreenPoint(width,height,60,10);
			AlarmPoint bottomRightP2=AlarmUtil.getWidowScreenPoint(width,height,100,90);

			graphicalUI2.setBounds(topLeftP2.getX(), topLeftP2.getY(), (bottomRightP2.getX()-topLeftP2.getX()), bottomRightP2.getY()-topLeftP2.getY());
			add(graphicalUI1);
			add(graphicalUI2);
	 
	 }
	 
	 public void populateData(){
		 
		 List<AlarmEventGraphicalDetals> eventGraphicalList=WSCommUtil.alramEventGrphicalList;
		 
		 System.out.println("eventGraphicalList===="+eventGraphicalList.size());
		 if(eventGraphicalList.size()>0){
			 AlarmEventGraphicalDetals alGraphDet=eventGraphicalList.get(0);
			 System.out.println("Details============"+alGraphDet.getBuildingAddressFirstLine()+":"+alGraphDet.getNoOfFloors()+":"+alGraphDet.getFireAlarmCountMap());
			 graphicalUI1.setBuildingParams(200, 400, 20, 20, 20, 20, alGraphDet.getNoOfFloors()==null?20:alGraphDet.getNoOfFloors(), 5);
			 List<Integer> floors=new ArrayList<Integer>();
			 if(alGraphDet.getFireAlarmCountMap()!=null && alGraphDet.getFireAlarmCountMap().size()>0){
			 floors.addAll(alGraphDet.getFireAlarmCountMap().keySet());
			 }
			 graphicalUI1.setFireFloors(floors);
			 String address="";
			 address+=alGraphDet.getBuildingAddressFirstLine()!=null?alGraphDet.getBuildingAddressFirstLine()+",":"";
			 address+=alGraphDet.getBuildingAddressSecondLine()!=null?alGraphDet.getBuildingAddressSecondLine()+",":"";
			 address+=alGraphDet.getTown()!=null?alGraphDet.getTown()+",":"";
			 address+=alGraphDet.getCountry()!=null?alGraphDet.getCountry():"";
			
				graphicalUI1.setBuildingDetals(alGraphDet.getBuildingName(), address);
			
			 graphicalUI1.setVisible(true);
			 graphicalUI1.repaint();
		 }
		 
		 if(eventGraphicalList.size()>1){
			 AlarmEventGraphicalDetals alGraphDet=eventGraphicalList.get(1);
			 graphicalUI2.setBuildingParams(200, 400, 20, 20, 20, 20, alGraphDet.getNoOfFloors(), 5);
			 List<Integer> floors=new ArrayList<Integer>();
			 if(alGraphDet.getFireAlarmCountMap()!=null && alGraphDet.getFireAlarmCountMap().size()>0){
			 floors.addAll(alGraphDet.getFireAlarmCountMap().keySet());
			 }
			 graphicalUI2.setFireFloors(floors);
			 String address="";
			 address+=alGraphDet.getBuildingAddressFirstLine()!=null?alGraphDet.getBuildingAddressFirstLine()+",":"";
			 address+=alGraphDet.getBuildingAddressSecondLine()!=null?alGraphDet.getBuildingAddressSecondLine()+",":"";
			 address+=alGraphDet.getTown()!=null?alGraphDet.getTown()+",":"";
			 address+=alGraphDet.getCountry()!=null?alGraphDet.getCountry():"";
			 graphicalUI2.setBuildingDetals(alGraphDet.getBuildingName(), address);
			 graphicalUI2.setVisible(true);
			 graphicalUI2.repaint();
		 }
		 
	 }
}
