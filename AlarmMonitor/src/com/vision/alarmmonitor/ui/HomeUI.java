package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeUI extends JPanel{

	JLabel jl;
	ImageIcon icon;
	Logger log = Logger.getLogger("HomeUI");
	public HomeUI(){
		icon=new ImageIcon("resources/facility.png");
		jl=new JLabel(icon);
		
		add(jl,BorderLayout.CENTER);
		
	}
}
