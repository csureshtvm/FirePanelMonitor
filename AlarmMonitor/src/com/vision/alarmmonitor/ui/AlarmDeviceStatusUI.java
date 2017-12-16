package com.vision.alarmmonitor.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.vision.alarmmonitor.dto.AlarmDevicesDto;
import com.vision.alarmmonitor.dto.AlarmEventDTO;
import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class AlarmDeviceStatusUI extends JPanel{
	
	DefaultTableModel model =null;
	JScrollPane jsp=null;
	JTable alarmDevicesTable;
	ButtonColumn buttonColumn=null;
	JLabel jl;
	 public static List<Integer> inactiveDevices=new ArrayList<Integer>();
	public List<AlarmDevicesDto> alarmDevicestList=new ArrayList<AlarmDevicesDto>();
	 public AlarmDeviceStatusUI(){
		 jl=new JLabel("Alarm Devices");
		 Font f=new Font("Verdana", Font.BOLD, 14);
			String[] columns = new String[] {
		            "Sl #", "Building Name","Alarm Device ID", "Device Name", "Device Location","Installed Floor No","Last Online",""
		        };
			
			Object[][] data = new Object[][] {};
			model = new DefaultTableModel(data, columns);
			alarmDevicesTable = new JTable(model);
			//alarmEventsTable.setB
			alarmDevicesTable.setBackground(Color.WHITE);
			alarmDevicesTable.setShowHorizontalLines(true);
			alarmDevicesTable.setShowVerticalLines(true);
			alarmDevicesTable.setGridColor(Color.DARK_GRAY);
			//alarmEventsTable.setBorder(new LineBorder(Color.green, 4));
			alarmDevicesTable.setCellSelectionEnabled(false);
			//alarmEventsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			alarmDevicesTable.getColumnModel().getColumn(2).setPreferredWidth(150);
			
			
			//alarmEventsTable.setForeground(Color.red);
			jsp=new JScrollPane(alarmDevicesTable);
	        //add the table to the frame
	        this.add(jsp);
	        
	        for(int i=0;i<alarmDevicesTable.getColumnCount();i++){
	        	TableColumn column=alarmDevicesTable.getColumnModel().getColumn(i);
	        	column.setCellRenderer(new AlarmDeviceCustomCellRenderer());
	        	//TableRow row=null;
	        	
	       }
	        
	        
	        Action reset = new AbstractAction()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                JTable table = (JTable)e.getSource();
	                int modelRow = Integer.valueOf( e.getActionCommand() );
	               ((DefaultTableModel)table.getModel()).removeRow(modelRow);
	            }
	        };
	        
	        buttonColumn = new ButtonColumn(alarmDevicesTable, reset, 7,"Edit","device");
	        buttonColumn.setMnemonic(KeyEvent.VK_D);
	        
	        
	        
	        
	        //alarmEventsTable.setDefaultRenderer(Object.class, this);
			setLayout(null);
			if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			
			//alarmEventsTable.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
			
			jsp.getViewport().setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
			//jsp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
			}
			setScreenPosition();
			jl.setFont(f);
			add(jl);
	 }
	 private void setScreenPosition(){
			int width=AlarmMonitorMainUI.width;
			int height=AlarmMonitorMainUI.height;
			AlarmPoint topLeft=AlarmUtil.getWidowScreenPoint(width,height,10,10);
			AlarmPoint bottomRight=AlarmUtil.getWidowScreenPoint(width,height,90,85);
			jl.setBounds((width/2)-50,topLeft.getY()-40,200,40);
			jsp.setBounds(topLeft.getX(), topLeft.getY(), (bottomRight.getX()-topLeft.getX()), (bottomRight.getY()-topLeft.getY()));
		
		
		
		}
		//@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			
			//super.getTa
			// TODO Auto-generated method stub
			return null;
		}
		public void populateData(){
			inactiveDevices.clear();
			alarmDevicestList.clear();
			alarmDevicestList.addAll(WSCommUtil.alarmDevicestList);
			int rowCount=model.getRowCount();
			for(int i=0;i<rowCount;i++){
				model.removeRow(0);
				//System.out.println("Removing-->"+i);
			}
			int i=0;
			for(AlarmDevicesDto  devDTO : alarmDevicestList){
				
				Object[] data=new String[]{String.valueOf((++i)),devDTO.getBuildingName(),devDTO.getDeviceId(),devDTO.getDeviceName(),devDTO.getDeviceLoc(),devDTO.getInstalledFloorNo(),devDTO.getLastMessageReceivedTime(),"Edit"};
				model.addRow(data);
				System.out.println("devDTO.getCommunicationStatus()-->"+devDTO.getCommunicationStatus());
				if(!"100".equalsIgnoreCase(devDTO.getCommunicationStatus())){
					inactiveDevices.add((i-1));
				}
				
			}
			
			Action reset = new AbstractAction()
		        {
		            public void actionPerformed(ActionEvent e)
		            {
		                JTable table = (JTable)e.getSource();
		                int modelRow = Integer.valueOf( e.getActionCommand() );
		               // ((DefaultTableModel)table.getModel()).removeRow(modelRow);
		            }
		        };
			 buttonColumn = new ButtonColumn(alarmDevicesTable, reset, 7,"Edit","device");
		      
		     buttonColumn.setMnemonic(KeyEvent.VK_D);
			buttonColumn.initializeTableModel(alarmDevicesTable,7);
			// alarmEventsTable.setShowHorizontalLines(true);
	 		//alarmEventsTable.setShowVerticalLines(true);
			alarmDevicesTable.repaint();
			
		}
		
		
		class AlarmDeviceCustomCellRenderer extends DefaultTableCellRenderer {
			Border border=new LineBorder(Color.DARK_GRAY);
	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            //if (hasFocus) {
	            
	            
	            
	            if(inactiveDevices.contains(row)){
	            	 //l.setBackground(Color.RED);
	            	l.setBackground(Color.ORANGE);
	            	 l.setBorder(border);
	            }else{
	            	l.setBackground(Color.WHITE);
	            	l.setBorder(border);
	            }
	              // l.setText("Hello");
	           // }
	            return l;
	        }
	    }
}
