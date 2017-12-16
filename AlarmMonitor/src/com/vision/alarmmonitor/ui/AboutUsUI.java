package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.vision.alarmmonitor.util.AppConstants;

public class AboutUsUI extends JFrame{

	JLabel jl;
	ImageIcon icon;
	Logger log = Logger.getLogger("AboutUsUI");
	public AboutUsUI(){
		icon=new ImageIcon("resources/facility.png");
		jl=new JLabel("Alarm Monitor v 1.0");
		Container c=getContentPane();
		c.setLayout(null);
		c.add(jl);//,BorderLayout.CENTER
		if(AppConstants.BG_COLOR_SHOW){
		c.setBackground(AppConstants.SCREEN_BG_COLOR); 
		jl.setBackground(AppConstants.SCREEN_BG_COLOR); 
		}
		
		setVisible(true);
		setBounds(100, 100, 600, 400);
	}

}
