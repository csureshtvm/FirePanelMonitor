package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class ScheduleUI extends JPanel implements ItemListener{

	JLabel bldgLbl;
	ImageIcon icon;
	JTable scheduleTable;
	Logger log = Logger.getLogger("ScheduleUI");
	static Color[] rowColors={Color.red,Color.cyan,Color.cyan};
	JComboBox chooseBuildingCombo=null;
	ScheduleAddUI scheduleAddUI=null;
	
	JPanel jp;
	Vector<BuildingDTO> buildingList=new Vector<BuildingDTO>();
	public ScheduleUI(){
		bldgLbl=new JLabel("Choose Building");
		chooseBuildingCombo=new JComboBox();
		String[] columns = new String[] {
	            "Sl #", "Start Date","End Date", "Status", "Comments"
	        };
		
		 Object[][] data = new Object[][] {
		            {1, "08 Oct 2016","08 Oct 2016", "Completed", "Maintenance Completed Successfully" },
		            {2, "11 Nov 2016","10 Nov 2016", "Completed", "Maintenance Completed Successfully"},
		            {3, "09 Dec 2016","09 Dec 2016", "Completed", "Maintenance Completed Successfully"},
		            {4, "13 Jan 2017","14 Jan 2017", "Completed", "Maintenance Completed Successfully"},
		            {5, "17 Feb 2017","17 Feb 2017", "Pending", "Not Started"},
		        };
		 TableModel model = new DefaultTableModel(data, columns);
		 scheduleTable = new JTable(model);
		 //scheduleTable.setBackground(Color.cyan);
		 scheduleTable.setForeground(Color.blue);
		 scheduleTable.setShowHorizontalLines(true);
		 scheduleTable.setShowVerticalLines(true);
		 scheduleTable.setGridColor(Color.DARK_GRAY);
		 scheduleTable.setCellSelectionEnabled(false);
        JScrollPane jsp=new JScrollPane(scheduleTable);
        
       
        for(int i=0;i<scheduleTable.getColumnCount();i++){
        	TableColumn column=scheduleTable.getColumnModel().getColumn(i);
        	column.setCellRenderer(new ScheduleCustomCellRenderer());
        	
        	//TableRow row=null;
        	
       }
        
        //add the table to the frame
        
        scheduleAddUI=new ScheduleAddUI();
        Border border1=BorderFactory.createTitledBorder("Add Test Schedule");
        scheduleAddUI.setBorder(border1);
        
        this.add(scheduleAddUI);
        jp=new JPanel();
        jp.add(jsp);
        jp.add(bldgLbl);
        jp.add(chooseBuildingCombo);
        jp.setLayout(null);
		setLayout(null);
		add(jp);
		
		
		bldgLbl.setBounds(100,0,100,20);
		chooseBuildingCombo.setBounds(220, 0, 250, 20);
		
		AlarmPoint jp1topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 20, 5);
		AlarmPoint jp1bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 80, 40);
		
		int panelWidth=(jp1bottomRight.getX()-jp1topLeft.getX());
		int panelHeight=(jp1bottomRight.getY()-jp1topLeft.getY());
		jp.setBounds(jp1topLeft.getX(),jp1topLeft.getY(),(jp1bottomRight.getX()-jp1topLeft.getX()),(jp1bottomRight.getY()-jp1topLeft.getY()));
		jsp.setBounds((int)(panelWidth*0.1),(int)(panelHeight*0.2),(int)(panelWidth*0.8),(int)(panelHeight*0.8));
		
		AlarmPoint p1topLeft=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 30, 45);
		AlarmPoint p1bottomRight=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 70, 80);
		
		scheduleAddUI.setBounds(p1topLeft.getX(),p1topLeft.getY(),(p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
		scheduleAddUI.setScreenPosition((p1bottomRight.getX()-p1topLeft.getX()),(p1bottomRight.getY()-p1topLeft.getY()));
		if(AppConstants.BG_COLOR_SHOW){
		setBackground(AppConstants.SCREEN_BG_COLOR); 
		jp.setBackground(AppConstants.SCREEN_BG_COLOR); 
		jsp.getViewport().setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
		}
		chooseBuildingCombo.addItemListener(this);
		
		 int totalTableWidth=(int)(panelWidth*0.8);
		 int[] colSizeArr=new int[]{7,15,15,20,40};
		 for(int i=0;i<scheduleTable.getColumnCount();i++){
	        	TableColumn column=scheduleTable.getColumnModel().getColumn(i);
	        	column.setMinWidth(totalTableWidth*colSizeArr[i]/100);
		 }
	}
	
	@Override
	public void itemStateChanged(ItemEvent event) {
		if(event.getSource() == chooseBuildingCombo){
			if(chooseBuildingCombo.getSelectedIndex()>=0 && buildingList!=null && chooseBuildingCombo.getSelectedIndex()<buildingList.size()){
			BuildingDTO selectedBldg=buildingList.get(chooseBuildingCombo.getSelectedIndex());
			
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
	
	
	class ScheduleCustomCellRenderer extends DefaultTableCellRenderer {
		Border border=new LineBorder(Color.DARK_GRAY);
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
           
            	l.setBackground(Color.WHITE);
            	l.setBorder(border);
        
              // l.setText("Hello");
           // }
            return l;
        }
    }

}
