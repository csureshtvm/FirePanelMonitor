package com.vision.alarmmonitor.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class AlarmEventResetUI extends JFrame implements ActionListener{

	
	JLabel jl=new JLabel();
	JTextArea commentsTxt;
	public static Integer alarmEventId=null;
	
	JButton submitBtn,cancelbtn;
	JPanel jp;
	public AlarmEventResetUI(){
		jp=new JPanel();
		submitBtn=new JButton("Submit");
		cancelbtn=new JButton("Cancel");
		
		jl=new JLabel("Reset Comments");
		commentsTxt=new JTextArea();
		
		JScrollPane jsp=new JScrollPane(commentsTxt);
		
		Container c=getContentPane();
		c.add(jp);
		
		c.setLayout(null);
		jp.setLayout(null);
		jp.add(jl);
		jp.add(jsp);
		jp.add(submitBtn);
		jp.add(cancelbtn);
		
		jl.setBounds(50,30,300,20);
		jsp.setBounds(50,60,300,150);
		
		submitBtn.setBounds(100,250,100,20);
		cancelbtn.setBounds(220,250,100,20);
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		}
		
		submitBtn.addActionListener(this);
		cancelbtn.addActionListener(this);
		setVisible(false);
		
		setScreenPosition();
		
	}
	private void setScreenPosition(){
		
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,25,20);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,75,80);
		jp.setBounds(0, 0, 450, 300);//(bottomRightP1.getY()-topLeftP1.getY())
		setBounds(topLeftP1.getX(), topLeftP1.getY(), 450, 320);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==submitBtn){
			boolean resetStatus=WSCommUtil.resetAlarm(alarmEventId,commentsTxt.getText());
    		AlarmMonitor.getInstance().lastAlarmEventRefreshTime=0;
    		
    		if(resetStatus){
    		JOptionPane.showMessageDialog(this, "Alarm Resetted Successfully");
    		}else{
    			JOptionPane.showMessageDialog(this, "Alarm Reset Failed","Reset Status",JOptionPane.ERROR);
    		}
    		setVisible(false);
    		
		}else{
			setVisible(false);
		}
		
	}

}
