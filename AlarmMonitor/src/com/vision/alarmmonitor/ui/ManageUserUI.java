package com.vision.alarmmonitor.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.UserDTO;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class ManageUserUI extends JPanel implements ActionListener,ItemListener{
	
	JLabel userNameLbl,firstNameLbl,lastNameLbl,userEmailLbl,customerAccessLbl, roleLbl;
	JTextField userNameTxt,firstNameTxt,lastNameTxt,userEmailTxt;
	JComboBox customerCombo, roleCombo;
	JButton saveBtn,cancelbtn;
	JPanel jp;
	public ManageUserUI(){
		jp=new JPanel();
		userNameLbl=new JLabel("User Name");
		firstNameLbl=new JLabel("First Name");
		lastNameLbl=new JLabel("Last Name");
		userEmailLbl=new JLabel("Email");
		customerAccessLbl=new JLabel("Choose Customer");
		roleLbl=new JLabel("Choose Role");
		
		userNameTxt=new JTextField();
		firstNameTxt=new JTextField();
		lastNameTxt=new JTextField();
		userEmailTxt=new JTextField();
		customerCombo=new JComboBox();
		roleCombo=new JComboBox(new String[]{"ADMIN","NORMAL USER","SUPPORT USER","MAINTENANCE USER"});
		
		saveBtn=new JButton("Save");
		cancelbtn=new JButton("Cancel");
		jp.add(userNameLbl);
		jp.add(firstNameLbl);
		jp.add(lastNameLbl);
		jp.add(userEmailLbl);
		jp.add(customerAccessLbl);
		jp.add(roleLbl);
		jp.add(userNameTxt);
		jp.add(firstNameTxt);
		jp.add(lastNameTxt);
		jp.add(userEmailTxt);
		jp.add(customerCombo);
		jp.add(roleCombo);
		jp.add(saveBtn);
		jp.add(cancelbtn);
		add(jp);
		setLayout(null);
		jp.setLayout(null);
		userNameLbl.setBounds(40, 10, 120, 20);
		firstNameLbl.setBounds(40, 50, 120, 20);
		lastNameLbl.setBounds(40, 90, 120, 20);
		userEmailLbl.setBounds(40, 130, 120, 20);
		roleLbl.setBounds(40, 170, 120, 20);
		customerAccessLbl.setBounds(40, 210, 120, 20);
		
		userNameTxt.setBounds(180, 10, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		firstNameTxt.setBounds(180, 50, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		lastNameTxt.setBounds(180, 90, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		userEmailTxt.setBounds(180, 130, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		roleCombo.setBounds(180, 170, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		customerCombo.setBounds(180, 210, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		
		customerAccessLbl.setVisible(false);
		customerCombo.setVisible(false);
		saveBtn.setBounds(80, 260, 80, 25);
		cancelbtn.setBounds(180, 260, 80, 25);
		
		saveBtn.addActionListener(this);
		cancelbtn.addActionListener(this);
		
		roleCombo.addItemListener(this);
		setScreenPosition();
		Border border=BorderFactory.createLineBorder(Color.GRAY);
		jp.setBorder(border);
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		}
		initializeData();
		
	}
	private void setScreenPosition(){
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,25,20);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,75,80);
		jp.setBounds(topLeftP1.getX(), topLeftP1.getY(), 450, 330);//(bottomRightP1.getY()-topLeftP1.getY())
		
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource()==saveBtn){
			Integer selectedCustomer=null;
			if("NORMAL USER".equalsIgnoreCase(roleCombo.getSelectedItem().toString())){
				selectedCustomer=WSCommUtil.customerMap.get(customerCombo.getSelectedItem().toString());
			}
			
			UserDTO userDto=new UserDTO(userNameTxt.getText(),firstNameTxt.getText(),lastNameTxt.getText(),userEmailTxt.getText(),roleCombo.getSelectedItem().toString(),selectedCustomer);
		if(WSCommUtil.createNewUser(userDto)){
			JOptionPane.showMessageDialog(null, "User Created Successfully","User creation Status",JOptionPane.INFORMATION_MESSAGE);
			
		}else{
			JOptionPane.showMessageDialog(null, "User Creation Failed","User creation Status",JOptionPane.ERROR_MESSAGE);
		}
		clear();
		
		}else if(ae.getSource()==cancelbtn){
			clear();
		}
		
	}

	private void clear(){
		userNameTxt.setText("");
		firstNameTxt.setText("");
		lastNameTxt.setText("");
		userEmailTxt.setText("");
		
	}
	private void initializeData(){
		customerCombo.removeAllItems();
		//chooseCustomerCombo.addItem("Select Customer");
		customerCombo.addItem("Select Customer");
		
		if(WSCommUtil.customerMap!=null && WSCommUtil.customerMap.size()>0){
			
			Iterator<Map.Entry<String,Integer>> iter=WSCommUtil.customerMap.entrySet().iterator();
			
			while(iter.hasNext()){
				Map.Entry<String,Integer> entry=iter.next();
				customerCombo.addItem(entry.getKey());
			}
			
		}
		
	}
	@Override
	public void itemStateChanged(ItemEvent ae) {
		if(ae.getSource() == roleCombo){
			if(!"ADMIN".equalsIgnoreCase(roleCombo.getSelectedItem().toString())){
				customerAccessLbl.setVisible(true);
				customerCombo.setVisible(true);
			}else{
				customerAccessLbl.setVisible(false);
				customerCombo.setVisible(false);
			}
		}
		
	}
	
}
