package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.TableView.TableRow;

import com.vision.alarmmonitor.dto.AlarmEventDTO;
import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.SoftwareMessageDTO;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class AlarmEventsUI extends JPanel {//implements TableCellRenderer{

	JLabel jl;
	ImageIcon icon;
	Logger log = Logger.getLogger("AlarmEventsUI");
	
	
	 public List<AlarmEventDTO> fireAlarmEventList=new ArrayList<AlarmEventDTO>();
	 public List<AlarmEventDTO> allOtherAlarmEventList=new ArrayList<AlarmEventDTO>();
	 public List<SoftwareMessageDTO> softwareAlarmEventList=new ArrayList<SoftwareMessageDTO>();
	 
	 public static List<Integer> fireAlarms=new ArrayList<Integer>();
	 public static List<Integer> allOtherAlarms=new ArrayList<Integer>();
	 ButtonColumn buttonColumn=null;
	 int totalTableWidth=500;
	 
	 FireAlarmsTabUI fireAlarmTabUI=null;
	 AlarmsOtherTabsUI alarmOtherTabUI=null;
	 SoftwareAlarmsTabUI softwareAlarmTabUI=null;
	 JTabbedPane jtab;
	 JPanel panel1,panel2;
	 
	public AlarmEventsUI(){
		jl=new JLabel("ALARMS");
		jtab = new JTabbedPane();
		fireAlarmTabUI=new FireAlarmsTabUI();
		alarmOtherTabUI=new AlarmsOtherTabsUI();
		softwareAlarmTabUI=new SoftwareAlarmsTabUI();
		
		jtab.addTab("Fire Alarms", null, fireAlarmTabUI, "Fire Alarms");
		jtab.addTab("Software Messages", null, softwareAlarmTabUI, "Software Messages");		
		jtab.addTab("Other Alarms", null, alarmOtherTabUI, "Other Alarms");
		
		panel1=new JPanel();
		panel2=new JPanel();
		setLayout(null);
		add(panel1);
		add(panel2);
		panel2.setLayout(null);
		panel2.add(jtab);
		
		
		Font f=new Font("Verdana", Font.BOLD | Font.ITALIC, 14);
		jl.setForeground(Color.BLUE);
		setLayout(null);
		if(AppConstants.BG_COLOR_SHOW){
		setBackground(AppConstants.SCREEN_BG_COLOR); 
		panel1.setBackground(AppConstants.SCREEN_BG_COLOR); 
		panel2.setBackground(AppConstants.SCREEN_BG_COLOR); 
		
		}
		setScreenPosition();
		jl.setFont(f);
		panel1.add(jl, BorderLayout.CENTER);
		//add(jl);
		
	}
	
	private void setScreenPosition(){
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint panel1TopLeft=AlarmUtil.getWidowScreenPoint(width,height,0,0);
		AlarmPoint panel1BottomRight=AlarmUtil.getWidowScreenPoint(width,height,100,5);
		
		AlarmPoint topLeft=AlarmUtil.getWidowScreenPoint(width,height,0,7);
		AlarmPoint bottomRight=AlarmUtil.getWidowScreenPoint(width,height,100,80);
		jl.setBounds((width/2)-50,topLeft.getY()-40,100,40);
		totalTableWidth=(bottomRight.getX()-topLeft.getX());
		
		panel1.setBounds(panel1TopLeft.getX(), panel1TopLeft.getY(), (panel1BottomRight.getX()-panel1TopLeft.getX()), (panel1BottomRight.getY()-panel1TopLeft.getY()));
		panel2.setBounds(topLeft.getX(), topLeft.getY(), (bottomRight.getX()-topLeft.getX()), (bottomRight.getY()-topLeft.getY()));
		jtab.setBounds(0,0,panel2.getWidth()-10,panel2.getHeight()-10);
	
	}
	public void populateData(){
		fireAlarmEventList.clear();
		allOtherAlarmEventList.clear();
		fireAlarms.clear();
		allOtherAlarms.clear();
		softwareAlarmEventList.clear();
		softwareAlarmEventList.addAll(WSCommUtil.softwareMessagesList);
		List<AlarmEventDTO> alarmEventList=WSCommUtil.alarmEventList;
		int fireAlarmIndex=0;
		int otherAlarmIndex=0;
		for(AlarmEventDTO  eventDTO : alarmEventList){
			
			System.out.println(eventDTO.getEventSystem()+":"+eventDTO.getEventSignal()+":"+eventDTO.getEventValue());
			if("FIRE ALARM".equalsIgnoreCase(eventDTO.getEventSystem())){
				
				fireAlarmEventList.add(eventDTO);
				if("alarm".equalsIgnoreCase(eventDTO.getEventValue())){
					fireAlarms.add(fireAlarmIndex);
					}
				fireAlarmIndex++;
			}else{
				allOtherAlarmEventList.add(eventDTO);
				if("alarm".equalsIgnoreCase(eventDTO.getEventValue())){
					allOtherAlarms.add(otherAlarmIndex);
				}
				otherAlarmIndex++;
			}
			
		}
		
		System.out.println("fireAlarms---->"+fireAlarms);
		System.out.println("allOtherAlarms---->"+allOtherAlarms);
		fireAlarmTabUI.populateData(fireAlarmEventList, fireAlarms);
		alarmOtherTabUI.populateData(allOtherAlarmEventList, allOtherAlarms);
		List<Integer> swMessageAlarmIndex=new ArrayList<Integer>();
		int alarmIndex=0;
		for(SoftwareMessageDTO  eventDTO : softwareAlarmEventList){
			
			if("ALARM".equalsIgnoreCase(eventDTO.getEventValue())){
				swMessageAlarmIndex.add(alarmIndex);
			}	
			alarmIndex++;
			
		}
		
		softwareAlarmTabUI.populateData(softwareAlarmEventList, swMessageAlarmIndex);
			
	}
}
	
	
	
	


