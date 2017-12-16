package com.vision.amclient.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.vision.amclient.monitor.AlarmMonitor;
import com.vision.amclient.util.StaticMessages;

public class TestMessageInputUI extends JFrame implements ActionListener{
	JButton jb1,jb2;
	public TestMessageInputUI(){
		super("Test Messages");
		jb1=new JButton("Send Fire Message");
		jb2=new JButton("Send Normal Alarm Message");
		this.add(jb1);
		this.add(jb2);
		setLayout(null);
		jb1.setBounds(100, 70, 160, 30);
		jb2.setBounds(300, 70, 200, 30);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		int height=(int)dim.getHeight();
		int width=(int)dim.getWidth();
		setBounds((width/2)-300, (height/2)-250, 600, 200);
		if(AlarmMonitor.isInputViaTestUI){
			setVisible(true);
		}
	}

	//@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			String message="FIRE ALARM";
			AlarmMonitor.getInstance().addAlarmMessage(message,message.toLowerCase().contains("fire")?StaticMessages.ALARM_TYPE_FIRE:StaticMessages.ALARM_TYPE_NORMAL);
			
		}else if(e.getSource()==jb2){
			String message="NORMAL ALARM";
			AlarmMonitor.getInstance().addAlarmMessage(message,message.toLowerCase().contains("fire")?StaticMessages.ALARM_TYPE_FIRE:StaticMessages.ALARM_TYPE_NORMAL);
			
		}
		
	}
	
}
