package com.vision.alarmmonitor.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.vision.alarmmonitor.dto.AlarmEventDTO;
import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class FireAlarmsTabUI extends JPanel{
	JTable alarmEventsTable;
	Logger log = Logger.getLogger("AlarmEventsUI");
	//static Color[] rowColors={Color.red,Color.cyan,Color.cyan};
	DefaultTableModel model =null;
	 JScrollPane jsp=null;
	
	 public List<AlarmEventDTO> alarmEventList=new ArrayList<AlarmEventDTO>();
	 
	 public static List<Integer> alarms=new ArrayList<Integer>();
	 ButtonColumn buttonColumn=null;
	 int totalTableWidth=500;
	public FireAlarmsTabUI(){
		String[] columns = new String[] {
	            "Sl #", "Building", "Event Name", "Status","Date Time",""
	        };
		
		 /*Object[][] data = new Object[][] {
		            {1, "New", new Date(), "Test Bldg1","High","","Notified" },
		            {2, "Last Week", new Date(), "Test Bldg1" ,"High","","Notified"},
		            {3, "Last Week", new Date(), "Test Bldg1","Low","","Notified" },
		        };*/
		Object[][] data = new Object[][] {};
		model = new DefaultTableModel(data, columns);
		alarmEventsTable = new JTable(model);
		//alarmEventsTable.setB
		alarmEventsTable.setBackground(Color.WHITE);
		alarmEventsTable.setShowHorizontalLines(true);
		alarmEventsTable.setShowVerticalLines(true);
		alarmEventsTable.setGridColor(Color.DARK_GRAY);
		//alarmEventsTable.setBorder(new LineBorder(Color.green, 4));
		alarmEventsTable.setCellSelectionEnabled(false);
		//alarmEventsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		alarmEventsTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		
		
		//alarmEventsTable.setForeground(Color.red);
		jsp=new JScrollPane(alarmEventsTable);
        //add the table to the frame
        this.add(jsp);
		
        for(int i=0;i<alarmEventsTable.getColumnCount();i++){
        	TableColumn column=alarmEventsTable.getColumnModel().getColumn(i);
        	column.setCellRenderer(new CustomCellRenderer());
        	
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
        
        buttonColumn = new ButtonColumn(alarmEventsTable, reset, 5,"Reset","fire");
      
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
		
		
	}
	
	private void setScreenPosition(){
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeft=AlarmUtil.getWidowScreenPoint(width,height,5,5);
		AlarmPoint bottomRight=AlarmUtil.getWidowScreenPoint(width,height,80,60);
		jsp.setBounds(topLeft.getX(), topLeft.getY(), (bottomRight.getX()-topLeft.getX()), (bottomRight.getY()-topLeft.getY()));
		totalTableWidth=(bottomRight.getX()-topLeft.getX());
	
		int[] colSizeArr=new int[]{5,40,10,10,25,10};
		 for(int i=0;i<alarmEventsTable.getColumnCount();i++){
	        	TableColumn column=alarmEventsTable.getColumnModel().getColumn(i);
	        	column.setMinWidth(totalTableWidth*colSizeArr[i]/100);
		 }
	
	}
	
	
	
	//@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		//super.getTa
		// TODO Auto-generated method stub
		return null;
	}
	public void populateData(List<AlarmEventDTO> fireAlarmEventList, List<Integer> fireAlarms){
		alarmEventList.clear();
		alarms.clear();
		
		alarmEventList.addAll(fireAlarmEventList);
		alarms.addAll(fireAlarms);
		
		int rowCount=model.getRowCount();
		for(int i=0;i<rowCount;i++){
			model.removeRow(0);
			//System.out.println("Removing-->"+i);
		}
		int i=0;
		for(AlarmEventDTO  eventDTO : alarmEventList){
			
			Object[] data=new String[]{String.valueOf((++i)),eventDTO.getBuildingName(),eventDTO.getEventSignal(),eventDTO.getEventValue(),eventDTO.getEventGeneratedTime()!=null?String.valueOf(eventDTO.getEventGeneratedTime()):"","Reset"};
			model.addRow(data);
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
		 buttonColumn = new ButtonColumn(alarmEventsTable, reset, 5,"Reset","fire");
	      
	     buttonColumn.setMnemonic(KeyEvent.VK_D);
		buttonColumn.initializeTableModel(alarmEventsTable,5);
		// alarmEventsTable.setShowHorizontalLines(true);
 		//alarmEventsTable.setShowVerticalLines(true);
 		alarmEventsTable.repaint();
		
	}
	
	
	
	public void resetAlarm(int row){
		
	}
	
}
class CustomCellRenderer extends DefaultTableCellRenderer {
	Border border=new LineBorder(Color.DARK_GRAY);
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //if (hasFocus) {
        if(FireAlarmsTabUI.alarms.contains(row)){
        	 l.setBackground(Color.RED);
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
