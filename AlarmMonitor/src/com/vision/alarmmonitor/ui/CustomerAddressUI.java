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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.vision.alarmmonitor.dto.AddressDetailsDTO;
import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.dto.ContactDetailsDTO;
import com.vision.alarmmonitor.dto.CustomerDTO;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class CustomerAddressUI extends JPanel implements ActionListener{

	JLabel addFirstLineLbl,addSecondLineLbl,townLbl,cityLbl,countryLbl,countyLbl,postalCodeLbl;
	ImageIcon icon;
	Logger log = Logger.getLogger("CustomerAddressUI");
	JTextArea addFirstLineTxt,addSecondLineTxt;
	JTextField townTxt,cityTxt,countryTxt,countyTxt,postalCodeTxt;
	
	JScrollPane jsp1,jsp2;
	JPanel jp=null;

	JButton edit;
	String addressType=null;
	String id=null;
	Integer addressId=0;
	public CustomerAddressUI(String addressType){
		this.addressType=addressType;
		//icon=new ImageIcon("resources/facility.png");
		addFirstLineLbl=new JLabel("Address First Line");
		addSecondLineLbl=new JLabel("Address Second Line");
		townLbl=new JLabel("Town");
		cityLbl=new JLabel("City");
		countryLbl=new JLabel("Country");
		countyLbl=new JLabel("County");
		postalCodeLbl=new JLabel("Postal Code");
		edit=new JButton("Edit");
		
		addFirstLineTxt=new JTextArea();
		addSecondLineTxt=new JTextArea();
		townTxt=new JTextField();
		cityTxt=new JTextField();
		countryTxt=new JTextField();
		countyTxt=new JTextField();
		postalCodeTxt=new JTextField();
		jsp1=new JScrollPane(addFirstLineTxt);
		jsp2=new JScrollPane(addSecondLineTxt);
		
		
		jp=new JPanel();
		jp.add(addFirstLineLbl);
		jp.add(addSecondLineLbl);
		jp.add(townLbl);
		jp.add(cityLbl);
		jp.add(countryLbl);
		jp.add(countyLbl);
		jp.add(postalCodeLbl);
		jp.add(edit);
		
		jp.add(jsp1);
		jp.add(jsp2);
		jp.add(townTxt);
		jp.add(cityTxt);
		jp.add(countryTxt);
		jp.add(countyTxt);
		jp.add(postalCodeTxt);
		
		setLayout(null);
		add(jp);
		jp.setBounds(100,100,400,300);
		jp.setLayout(null);
		
		addFirstLineLbl.setBounds(10, 5, 120, 20);
		addSecondLineLbl.setBounds(10, 70, 140, 20);
		townLbl.setBounds(10, 135, 120, 20);
		cityLbl.setBounds(10, 160, 120, 20);
		countryLbl.setBounds(10, 185, 120, 20);
		countyLbl.setBounds(10, 210, 120, 20);
		postalCodeLbl.setBounds(10, 235, 120, 20);
		
		jsp1.setBounds(140, 5, AppConstants.TEXT_FIELD_WIDTH, 60);
		jsp2.setBounds(140, 70, AppConstants.TEXT_FIELD_WIDTH, 60);
		townTxt.setBounds(140, 135, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		cityTxt.setBounds(140, 160, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		countryTxt.setBounds(140, 185, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		countyTxt.setBounds(140, 210, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		postalCodeTxt.setBounds(140, 235, AppConstants.TEXT_FIELD_WIDTH, AppConstants.TEXT_FIELD_HEIGHT);
		
		addFirstLineTxt.setEditable(false);
		addSecondLineTxt.setEditable(false);
		townTxt.setEditable(false);
		cityTxt.setEditable(false);
		countryTxt.setEditable(false);
		countyTxt.setEditable(false);
		postalCodeTxt.setEditable(false);



		edit.setBounds(120, 270,120, 20);
		edit.addActionListener(this);
		setLayout(null);
		if(AppConstants.BG_COLOR_SHOW){
		setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		}
	}
	
	
	public void setScreenPosition(int width, int height){
		
		int comptTotalWidth=310;
		int comptTotalHeight=320;
		int topX=(width-comptTotalWidth)/2;
		int topY=(height-comptTotalHeight)/2;
		
		jp.setBounds(topX, topY, (width-topX), (height-topY));
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==edit){
			addFirstLineTxt.setEditable(true);
			addSecondLineTxt.setEditable(true);
			townTxt.setEditable(true);
			cityTxt.setEditable(true);
			countryTxt.setEditable(true);
			countyTxt.setEditable(true);
			postalCodeTxt.setEditable(true);
			if(edit.getLabel().equalsIgnoreCase("Edit")){
			edit.setLabel("Save");
			}else{
				
				
				AddressDetailsDTO addressDTO=new AddressDetailsDTO();
				
				//addressDTO.setAddressType(address.getAddressType());
				addressDTO.setAddressFirstLine(addFirstLineTxt.getText());
				addressDTO.setAddressSecondLine(addSecondLineTxt.getText());
				addressDTO.setTown(townTxt.getText());
				addressDTO.setCountry(countryTxt.getText());
				addressDTO.setCounty(countyTxt.getText());
				addressDTO.setPostalCode(postalCodeTxt.getText());
				
				
				Integer buildingId="building".equalsIgnoreCase(addressType)?(id!=null?Integer.valueOf(id):0):0;
				String customerId="customer".equalsIgnoreCase(addressType)?id:"";
				if(WSCommUtil.saveAddressDetails(addressDTO, addressType, buildingId, customerId)){
					JOptionPane.showMessageDialog(this, "Address Details Saved Successfully", "Status", JOptionPane.INFORMATION_MESSAGE);
					
				}else{
					JOptionPane.showMessageDialog(this, "Address Details Save Failed", "Status", JOptionPane.ERROR_MESSAGE);
					
				}
				
				
				addFirstLineTxt.setEditable(false);
				addSecondLineTxt.setEditable(false);
				townTxt.setEditable(false);
				cityTxt.setEditable(false);
				countryTxt.setEditable(false);
				countyTxt.setEditable(false);
				postalCodeTxt.setEditable(false);
				edit.setLabel("Edit");
				
				
			}
		}
	}
	
	public void populateData(Object obj){
		if(obj instanceof BuildingDTO){
			BuildingDTO bldgDTO=(BuildingDTO)obj;
		addFirstLineTxt.setText(bldgDTO.getAddressFirstLine()!=null?bldgDTO.getAddressFirstLine():"");
		addSecondLineTxt.setText(bldgDTO.getAddressSecondLine()!=null?bldgDTO.getAddressSecondLine():"");
		townTxt.setText(bldgDTO.getTown()!=null?bldgDTO.getTown():"");
		//cityTxt.setText(bldgDTO.getC()!=null?bldgDTO.getAddressSecondLine():"");
		countryTxt.setText(bldgDTO.getCounty()!=null?bldgDTO.getCounty():"");
		countyTxt.setText(bldgDTO.getCounty()!=null?bldgDTO.getCounty():"");
		postalCodeTxt.setText(bldgDTO.getPostalCode()!=null?bldgDTO.getPostalCode():"");
		id=bldgDTO.getBuildingId();
		}else if(obj instanceof CustomerDTO){
			CustomerDTO custDTO=(CustomerDTO)obj;
			
			addFirstLineTxt.setText(custDTO.getAddressFirstLine()!=null?custDTO.getAddressFirstLine():"");
			addSecondLineTxt.setText(custDTO.getAddressSecondLine()!=null?custDTO.getAddressSecondLine():"");
			townTxt.setText(custDTO.getTown()!=null?custDTO.getTown():"");
			//cityTxt.setText(bldgDTO.getC()!=null?bldgDTO.getAddressSecondLine():"");
			countryTxt.setText(custDTO.getCounty()!=null?custDTO.getCounty():"");
			countyTxt.setText(custDTO.getCounty()!=null?custDTO.getCounty():"");
			postalCodeTxt.setText(custDTO.getPostalCode()!=null?custDTO.getPostalCode():"");
			id=WSCommUtil.customerId;
			
			
		}
		
	}
	
}
