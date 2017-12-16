package com.vision.alarmmonitor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class NotificationSettingsContactUI extends JPanel implements ActionListener{

	ImageIcon icon;
	JLabel contactameLbl,phoneLbl,emailLbl;
	JButton edit;

	JLabel contactameTxt,phoneTxt,emailTxt;
	JLabel notifyViaLbl;
	 
	JCheckBox smsChk,emailChk;
	
	Logger log = Logger.getLogger("CustomerContactUI");
	private String type=null;
	private String buildingId=null;
	JPanel jp;
	public NotificationSettingsContactUI(String type){
		this.type=type;
		contactameLbl=new JLabel("Contact Name");
		phoneLbl=new JLabel("Phone");
		emailLbl=new JLabel("Email");
		edit=new JButton("Edit");
		contactameTxt=new JLabel("");
		phoneTxt=new JLabel("");
		emailTxt=new JLabel("");
		notifyViaLbl=new JLabel("Notify Via");
		smsChk=new JCheckBox("SMS");
		emailChk=new JCheckBox("Email");
		
		jp=new JPanel();
		
		jp.add(contactameLbl);
		jp.add(phoneLbl);
		jp.add(emailLbl);
		jp.add(edit);
		
		jp.add(contactameTxt);
		jp.add(phoneTxt);
		jp.add(emailTxt);
		jp.add(notifyViaLbl);
		
		jp.add(smsChk);
		jp.add(emailChk);
		jp.setLayout(null);
		add(jp);
		setLayout(null);
		
		contactameLbl.setBounds(10, 20, 120, 20);
		phoneLbl.setBounds(10, 60, 120, 20);
		emailLbl.setBounds(10, 100, 120, 20);
		notifyViaLbl.setBounds(10, 150, 120, 20);
		
		contactameTxt.setBounds(140, 20,120, 20);
		phoneTxt.setBounds(140, 60,180, 20);
		emailTxt.setBounds(140, 100, 180, 20);
		
		smsChk.setBounds(100, 150, 80, 20);
		emailChk.setBounds(180, 150, 80, 20);
		
		smsChk.setEnabled(false);
		emailChk.setEnabled(false);
		
		edit.setBounds(50, 200,120, 20);
		edit.addActionListener(this);
		
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
			jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
		}

	}
	
	public void setScreenPosition(int width,int height){
		int comptTotalWidth=310;
		int comptTotalHeight=220;
		int topX=(width-comptTotalWidth)/2;
		int topY=(height-comptTotalHeight)/2;
		
		jp.setBounds(topX, topY, (width-topX)-10, (height-topY)-10);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==edit){
			if(edit.getLabel().equalsIgnoreCase("Edit")){
			
			smsChk.setEnabled(true);
			emailChk.setEnabled(true);
			edit.setLabel("Save");
			}else if(edit.getLabel().equalsIgnoreCase("Save")){
				
				boolean smsNotify=smsChk.isSelected();
				boolean emailNotify=emailChk.isSelected();
				
				if(WSCommUtil.saveNotfnSettings(buildingId,type,(smsNotify?"Y":"N"),(emailNotify?"Y":"N"))){
					JOptionPane.showMessageDialog(this, "Settings Updated Successfully", "Status", JOptionPane.INFORMATION_MESSAGE);
					
				}else{
					JOptionPane.showMessageDialog(this, "Settings Updation Failed", "Status", JOptionPane.ERROR_MESSAGE);
					
				}
				
				smsChk.setEnabled(false);
				emailChk.setEnabled(false);
				edit.setLabel("Edit");
				
			}
		}
	}

	public void populateData(String contactName, String phone, String email, String notifyEmail, String notifySMS,String buildingId){
		
		contactameTxt.setText(contactName!=null?contactName:"");
		phoneTxt.setText(phone!=null?phone:"");
		emailTxt.setText(email!=null?email:"");
		this.buildingId=buildingId;
		smsChk.setSelected((notifySMS!=null && "Y".equalsIgnoreCase(notifySMS))?true:false);
		emailChk.setSelected((notifyEmail!=null && "Y".equalsIgnoreCase(notifyEmail))?true:false);
	}

}
