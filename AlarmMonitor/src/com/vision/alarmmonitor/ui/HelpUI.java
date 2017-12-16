package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;

public class HelpUI extends JFrame{

	JLabel jl;
	ImageIcon icon;
	Logger log = Logger.getLogger("HelpUI");
	public HelpUI(){
		icon=new ImageIcon("resources/facility.png");
		jl=new JLabel("Alarm Monitor v3.4",icon,JLabel.CENTER);
		Font titleFont = new Font("Arial", Font.BOLD, 18);
		
		Container c=getContentPane();
		//c.setLayout(new );
		jl.setFont(titleFont);
		c.add(jl,BorderLayout.CENTER);
		if(AppConstants.BG_COLOR_SHOW){
			c.setBackground(AppConstants.SCREEN_BG_COLOR); 
			jl.setBackground(AppConstants.SCREEN_BG_COLOR); 
		}
		setVisible(false);
		setScreenPosition();
	}
	
	
	private void setScreenPosition(){
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,25,20);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,75,80);
		setBounds(topLeftP1.getX(), topLeftP1.getY(), 450, 220);
		
		
	}

}
