package com.vision.alarmmonitor.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.UserDTO;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class ChangePasswordUI extends JFrame implements ActionListener{
	
	JLabel oldPasswordLbl,newPasswordLbl,confirmNewPasswordLbl;
	JPasswordField oldPasswordTxt,newPasswordTxt,confirmNewPasswordTxt;
	JButton saveBtn,cancelbtn;
	JPanel jp;
	public ChangePasswordUI(){
		jp=new JPanel();
		oldPasswordLbl=new JLabel("Old Password");
		newPasswordLbl=new JLabel("New Password");
		confirmNewPasswordLbl=new JLabel("Confirm New Password");
		
		
		oldPasswordTxt=new JPasswordField();
		newPasswordTxt=new JPasswordField();
		confirmNewPasswordTxt=new JPasswordField();
		
		
		saveBtn=new JButton("Save");
		cancelbtn=new JButton("Cancel");
		jp.add(oldPasswordLbl);
		jp.add(newPasswordLbl);
		jp.add(confirmNewPasswordLbl);
		jp.add(oldPasswordTxt);
		jp.add(newPasswordTxt);
		jp.add(confirmNewPasswordTxt);
		
		jp.add(saveBtn);
		jp.add(cancelbtn);
		add(jp);
		setLayout(null);
		jp.setLayout(null);
		oldPasswordLbl.setBounds(40, 10, 150, 20);
		newPasswordLbl.setBounds(40, 50, 150, 20);
		confirmNewPasswordLbl.setBounds(40, 90, 150, 20);
		oldPasswordTxt.setBounds(200, 10, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		newPasswordTxt.setBounds(200, 50, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		confirmNewPasswordTxt.setBounds(200, 90, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		
		saveBtn.setBounds(100, 130, 80, 25);
		cancelbtn.setBounds(200, 130, 80, 25);
		
		saveBtn.addActionListener(this);
		cancelbtn.addActionListener(this);
		setScreenPosition();
		Border border=BorderFactory.createLineBorder(Color.GRAY);
		jp.setBorder(border);
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		}
		
		setVisible(false);
	}
	private void setScreenPosition(){
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,25,20);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,75,80);
		jp.setBounds(0, 0, 450, 180);//(bottomRightP1.getY()-topLeftP1.getY())
		setBounds(topLeftP1.getX(), topLeftP1.getY(), 450, 220);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource()==saveBtn){
			
		if(oldPasswordTxt.getText().trim().isEmpty() || newPasswordTxt.getText().trim().isEmpty() || confirmNewPasswordTxt.getText().trim().isEmpty()){
			JOptionPane.showMessageDialog(null, "All fields are mandatory","Change Password Status",JOptionPane.ERROR_MESSAGE);
		}else if(!AlarmMonitor.loggedInUserPW.equals(oldPasswordTxt.getText())){
			JOptionPane.showMessageDialog(null, "Current Password entered is wrong ","Change Password Status",JOptionPane.ERROR_MESSAGE);
		}else if(!newPasswordTxt.getText().equals(confirmNewPasswordTxt.getText())){
			JOptionPane.showMessageDialog(null, "New Password shouldmatch with Confirm Password ","Change Password Status",JOptionPane.ERROR_MESSAGE);
		}else{
			
			
		if(WSCommUtil.updatePassword(newPasswordTxt.getText())){
			JOptionPane.showMessageDialog(null, "Password Changed Successfully","Change Password Status",JOptionPane.INFORMATION_MESSAGE);
			
		}else{
			JOptionPane.showMessageDialog(null, "Password updation Failed","Change Password Status",JOptionPane.ERROR_MESSAGE);
		}
		clear();
		}
		}else if(ae.getSource()==cancelbtn){
			clear();
		}
		
	}

	private void clear(){
		oldPasswordTxt.setText("");
		newPasswordTxt.setText("");
		confirmNewPasswordTxt.setText("");
				
	}
	
	
}
