package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.dto.ContactDetailsDTO;
import com.vision.alarmmonitor.dto.CustomerDTO;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class CustomerContactUI extends JPanel implements ActionListener{

	ImageIcon icon;
	JLabel contactameLbl,contactTypeLbl,primaryPhLbl,secondaryPhLbl,faxLbl,emailLbl;
	JButton edit;

	JTextField contactNameTxt,contactTypeTxt,primaryPhTxt,secondaryPhTxt,faxTxt,emailTxt;
	
	Logger log = Logger.getLogger("CustomerContactUI");
	String contactType=null;
	Integer contactId=null;
	String id=null;
	JPanel jp=null;
	
	public CustomerContactUI(String contactType){
		this.contactType=contactType;
		contactameLbl=new JLabel("Contact Name");
		contactTypeLbl=new JLabel("Contact Type");
		primaryPhLbl=new JLabel("Primary Phone");
		secondaryPhLbl=new JLabel("Secondary Phone");
		faxLbl=new JLabel("Fax");
		emailLbl=new JLabel("Email");
		edit=new JButton("Edit");
		contactNameTxt=new JTextField();
		contactTypeTxt=new JTextField();
		primaryPhTxt=new JTextField();
		secondaryPhTxt=new JTextField();
		faxTxt=new JTextField();
		emailTxt=new JTextField();
		
		jp=new JPanel();
		
		jp.add(contactameLbl);
		jp.add(contactTypeLbl);
		jp.add(primaryPhLbl);
		jp.add(secondaryPhLbl);
		jp.add(faxLbl);
		jp.add(emailLbl);
		jp.add(edit);
		
		jp.add(contactNameTxt);
		jp.add(contactTypeTxt);
		jp.add(primaryPhTxt);
		jp.add(secondaryPhTxt);
		jp.add(faxTxt);
		jp.add(emailTxt);
		contactNameTxt.setEditable(false);
		contactTypeTxt.setEditable(false);
		primaryPhTxt.setEditable(false);
		secondaryPhTxt.setEditable(false);
		faxTxt.setEditable(false);
		emailTxt.setEditable(false);
		setLayout(null);
		add(jp);
		jp.setBounds(100,100,400,300);
		jp.setLayout(null);
		
		
		contactameLbl.setBounds(10, 20, 120, 20);
		contactTypeLbl.setBounds(10, 50, 120, 20);
		primaryPhLbl.setBounds(10, 80, 120, 20);
		secondaryPhLbl.setBounds(10, 110, 120, 20);
		faxLbl.setBounds(10, 140, 120, 20);
		emailLbl.setBounds(10, 170, 120, 20);
		
		contactNameTxt.setBounds(140, 20, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		contactTypeTxt.setBounds(140, 50, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		primaryPhTxt.setBounds(140, 80, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		secondaryPhTxt.setBounds(140, 110, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		faxTxt.setBounds(140, 140, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		emailTxt.setBounds(140, 170, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
			jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		}
		edit.setBounds(120, 235,120, 20);
		edit.addActionListener(this);

	}
	
	public void setScreenPosition(int width, int height){
		
		int comptTotalWidth=310;
		int comptTotalHeight=285;
		int topX=(width-comptTotalWidth)/2;
		int topY=(height-comptTotalHeight)/2;
		
		jp.setBounds(topX, topY, (width-topX), (height-topY));
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==edit){
			if(edit.getLabel().equalsIgnoreCase("Edit")){
			contactNameTxt.setEditable(true);
			contactTypeTxt.setEditable(true);
			primaryPhTxt.setEditable(true);
			secondaryPhTxt.setEditable(true);
			faxTxt.setEditable(true);
			emailTxt.setEditable(true);
			
			edit.setLabel("Save");
			}else{

				
				ContactDetailsDTO contactDTO=new ContactDetailsDTO(contactId==null?0:contactId, contactNameTxt.getText(), contactTypeTxt.getText(), primaryPhTxt.getText(), secondaryPhTxt.getText(), faxTxt.getText(), emailTxt.getText());
				
				Integer buildingId="building".equalsIgnoreCase(contactType)?(id!=null?Integer.valueOf(id):0):0;
				String customerId="customer".equalsIgnoreCase(contactType)?id:"";
				WSCommUtil.saveContactDetails(contactDTO, contactType, buildingId, customerId);
				
				
				contactNameTxt.setEditable(false);
				contactTypeTxt.setEditable(false);
				primaryPhTxt.setEditable(false);
				secondaryPhTxt.setEditable(false);
				faxTxt.setEditable(false);
				emailTxt.setEditable(false);
				
				
				
				
				edit.setLabel("Edit");
				
				JOptionPane.showMessageDialog(this, "Contact Details Saved Successfully", "Status", JOptionPane.INFORMATION_MESSAGE);
				
			}
		}
	}
	
	public void populateData(Object obj){
		if(obj instanceof BuildingDTO){
			BuildingDTO bldgDTO=(BuildingDTO)obj;
			
			contactNameTxt.setText(bldgDTO.getContactName()!=null?bldgDTO.getContactName():"");
			contactTypeTxt.setText(bldgDTO.getContactType()!=null?bldgDTO.getContactType():"");
			primaryPhTxt.setText(bldgDTO.getPhonePrimary()!=null?bldgDTO.getPhonePrimary():"");
			secondaryPhTxt.setText(bldgDTO.getPhoneSecondary()!=null?bldgDTO.getPhoneSecondary():"");
			faxTxt.setText(bldgDTO.getFax()!=null?bldgDTO.getFax():"");
			emailTxt.setText(bldgDTO.getEmail()!=null?bldgDTO.getEmail():"");
			contactId=bldgDTO.getContactId();
			id=bldgDTO.getBuildingId();
		}else if(obj instanceof CustomerDTO){
			CustomerDTO custDTO=(CustomerDTO)obj;
			contactNameTxt.setText(custDTO.getContactName()!=null?custDTO.getContactName():"");
			contactTypeTxt.setText(custDTO.getContactType()!=null?custDTO.getContactType():"");
			primaryPhTxt.setText(custDTO.getPhonePrimary()!=null?custDTO.getPhonePrimary():"");
			secondaryPhTxt.setText(custDTO.getPhoneSecondary()!=null?custDTO.getPhoneSecondary():"");
			faxTxt.setText(custDTO.getFax()!=null?custDTO.getFax():"");
			emailTxt.setText(custDTO.getEmail()!=null?custDTO.getEmail():"");
			contactId=custDTO.getContactId();
			id=custDTO.getCustomerId();
		}
	}

}
