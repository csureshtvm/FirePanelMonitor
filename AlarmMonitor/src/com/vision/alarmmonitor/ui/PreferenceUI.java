package com.vision.alarmmonitor.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class PreferenceUI extends JPanel implements ItemListener{

	JLabel chooseCustomerLbl ;
	JComboBox chooseCustomerCombo;
	JPanel jp;
	
	public PreferenceUI(){
		chooseCustomerLbl=new JLabel("Choose Customer ");
		chooseCustomerCombo=new JComboBox();
		jp=new JPanel();
		Border border=BorderFactory.createTitledBorder("Settings");
		jp.setBorder(border);
		
		setLayout(null);
		add(jp);
		jp.setLayout(null);
		jp.add(chooseCustomerLbl);
		jp.add(chooseCustomerCombo);
		
		chooseCustomerLbl.setBounds(80, 30, 130, 25);
		chooseCustomerCombo.setBounds(220, 30, 250, 25);
		
		
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 20, 20);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 70, 50);
		
		jp.setBounds(topLeftP1.getX(), topLeftP1.getY(),(bottomRightP1.getX()-topLeftP1.getX()) , (bottomRightP1.getY()-topLeftP1.getY()));
		
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
		}
		chooseCustomerCombo.addItemListener(this);
		initializeData();
		
	}
	private void initializeData(){
		chooseCustomerCombo.removeAllItems();
		//chooseCustomerCombo.addItem("Select Customer");
		chooseCustomerCombo.addItem("  ");
		
		if(WSCommUtil.customerMap!=null && WSCommUtil.customerMap.size()>0){
			
			Iterator<Map.Entry<String,Integer>> iter=WSCommUtil.customerMap.entrySet().iterator();
			
			while(iter.hasNext()){
				Map.Entry<String,Integer> entry=iter.next();
				chooseCustomerCombo.addItem(entry.getKey());
			}
			
		}
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == chooseCustomerCombo){
			String itemName=(String)chooseCustomerCombo.getSelectedItem();
			if(!itemName.trim().isEmpty()){
			WSCommUtil.customerId=WSCommUtil.customerMap.get(itemName)+"";
			AlarmMonitor.dataUpdated=true;
			}
			//JOptionPane.showMessageDialog(null, "Selected Customer - "+itemName +"- "+WSCommUtil.customerMap.get(itemName));

		}
	}
}
