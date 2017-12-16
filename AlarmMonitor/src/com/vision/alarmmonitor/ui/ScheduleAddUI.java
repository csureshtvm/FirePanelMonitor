package com.vision.alarmmonitor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vision.alarmmonitor.util.AppConstants;

public class ScheduleAddUI extends JPanel implements ActionListener{

	ImageIcon icon;
	JLabel startDateLbl,endDateLbl,commentsLbl;
	JButton edit;

	JTextField startDateTxt,endDateTxt,commentsTxt;
		
	Logger log = Logger.getLogger("CustomerContactUI");
	private String type=null;
	
	JPanel jp1;
	public ScheduleAddUI(){
		startDateLbl=new JLabel("Test Start Date");
		endDateLbl=new JLabel("Test End Date");
		commentsLbl=new JLabel("Comments");
		edit=new JButton("Edit");
		startDateTxt=new JTextField();
		endDateTxt=new JTextField();
		commentsTxt=new JTextField();
		jp1=new JPanel();
		
		
		jp1.add(startDateLbl);
		jp1.add(endDateLbl);
		jp1.add(commentsLbl);
		jp1.add(edit);
		
		jp1.add(startDateTxt);
		jp1.add(endDateTxt);
		jp1.add(commentsTxt);
		
		
		startDateTxt.setEditable(false);
		endDateTxt.setEditable(false);
		commentsTxt.setEditable(false);
		jp1.setLayout(null);
		setLayout(null);
		add(jp1);
		startDateLbl.setBounds(0, 0, 120, 20);
		endDateLbl.setBounds(0, 40, 120, 20);
		commentsLbl.setBounds(0, 80, 120, 20);
		startDateTxt.setBounds(160, 0,120, 20);
		endDateTxt.setBounds(160, 40,120, 20);
		commentsTxt.setBounds(160, 80, 120, 20);
		edit.setBounds(70, 140,120, 20);
		edit.addActionListener(this);
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
			jp1.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
		}

	}
	public void setScreenPosition(int width,int height){
		int comptTotalWidth=260;
		int comptTotalHeight=140;
		int topX=(width-comptTotalWidth)/2;
		int topY=(height-comptTotalHeight)/2;
		
		jp1.setBounds(topX, topY, (width-topX)-10, (height-topY)-10);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==edit){
			if(edit.getLabel().equalsIgnoreCase("Edit")){
			
				startDateTxt.setEditable(true);
				endDateTxt.setEditable(true);
				commentsTxt.setEditable(true);
			edit.setLabel("Save");
			}else{
				
				startDateTxt.setEditable(false);
				endDateTxt.setEditable(false);
				commentsTxt.setEditable(false);
				edit.setLabel("Edit");
				
			}
		}
	}

}
