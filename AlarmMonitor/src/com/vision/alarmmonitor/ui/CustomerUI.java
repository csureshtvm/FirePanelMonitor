package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.CustomerDTO;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class CustomerUI extends JPanel implements ActionListener,ItemListener{

	JLabel jl;
	ImageIcon icon;
	Logger log = Logger.getLogger("CustomerUI");
	
	JLabel custNameLbl,noofBuildingLbl,customerTypeLbl;
	JTabbedPane jtab;
	CustomerAddressUI customerAddressUI=null;
	CustomerContactUI customerContactUI=null;

	JButton edit;
	JPanel jp1,jp2;
	
	JTextField custNameTxt,noofBuildingTxt,customerTypeTxt;
	boolean addUser=false;
	
	//Customer Selection for Admin user
	JLabel chooseCustomerLbl ;
	JComboBox chooseCustomerCombo;
	JTextField customerNameText;
	JPanel jp;
	JButton customerAddBtn,cancelBtn;
	public CustomerUI(){
		icon=new ImageIcon("resources/facility.png");
		jl=new JLabel(icon);
		
		add(jl,BorderLayout.CENTER);
		
		
		custNameLbl=new JLabel("Customer Name");

		customerTypeLbl=new JLabel("Customer Type");

		noofBuildingLbl=new JLabel("No of Buildings");
		custNameTxt=new JTextField();
		noofBuildingTxt=new JTextField();
		customerTypeTxt=new JTextField();
		
		custNameTxt.setEditable(false);
		noofBuildingTxt.setEditable(false);
		customerTypeTxt.setEditable(false);
		edit=new JButton("Edit");
		 cancelBtn=new JButton("Cancel");
		customerAddressUI=new CustomerAddressUI("customer");
		customerContactUI=new CustomerContactUI("customer");
		
		jtab = new JTabbedPane();
		jtab.addTab("Address Details", null, customerAddressUI, "Address Details");
		jtab.addTab("Contact Details", null, customerContactUI, "Contact Details");
		
		jp1=new JPanel();
		jp2=new JPanel();
		
		
		setLayout(null);
		//jp1.setBounds(50,100,348,300);
		//jp2.setBounds(400,100,350,300);
		
		
		
		if(AlarmMonitor.isAdminUser){
			chooseCustomerLbl=new JLabel("Choose Customer ");
			chooseCustomerCombo=new JComboBox();
			customerNameText=new JTextField();
			jp=new JPanel();
			//Border border=BorderFactory.createTitledBorder("Settings");
			//jp.setBorder(border);
			
			jp.setLayout(null);
			add(jp);
			//jp.setLayout(null);
			jp.add(chooseCustomerLbl);
			jp.add(chooseCustomerCombo);
			jp.add(customerNameText);
			customerNameText.setVisible(false);
			
			try {
				Image img =ImageIO.read(getClass().getResource("/resources/add_ico.png")).getScaledInstance(25, 25,Image.SCALE_DEFAULT);
			
			//ImageIcon addIco = new ImageIcon("resources/add_icon.jpg");
			//Image scaleImage = addIco.getImage().getScaledInstance(50, 25,Image.SCALE_DEFAULT);
		    customerAddBtn = new JButton(new ImageIcon(img));
		   
		    customerAddBtn.setToolTipText("Add New");
		    customerAddBtn.setOpaque(false);
		    customerAddBtn.setContentAreaFilled(false);
		    customerAddBtn.setBorderPainted(false);
		   
		    jp.add(customerAddBtn);
		    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			chooseCustomerLbl.setBounds(0, 0, 130, 25);
			chooseCustomerCombo.setBounds(140, 0, 250, 25);
			customerNameText.setBounds(140, 0, 250, 25);
			customerAddBtn.setBounds(410, 0, 25, 25);
			cancelBtn.setBounds(410, 0, 25, 25);
			jp.setBackground(AppConstants.SCREEN_BG_COLOR);
			/*if(AppConstants.BG_COLOR_SHOW){
				setBackground(AppConstants.SCREEN_BG_COLOR); 
				jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
			}*/
			customerAddBtn.addActionListener(this);
			chooseCustomerCombo.addItemListener(this);
			//cancelBtn.setVisible(false);
			initializeData();
		}
		
		
		add(jp1);
		add(jp2);
		jp1.setLayout(null);
		jp1.add(custNameLbl);
		//jp1.add(customerTypeLbl);
		jp1.add(noofBuildingLbl);
		jp1.add(custNameTxt);
		//jp1.add(customerTypeTxt);
		jp1.add(noofBuildingTxt);
		jp1.add(cancelBtn);
	    cancelBtn.addActionListener(this);
	    cancelBtn.setVisible(false);
		jp1.add(edit);
		custNameLbl.setBounds(50,50,120,20);
		customerTypeLbl.setBounds(50,90,120,20);
		//noofBuildingLbl.setBounds(50,130,120,20);
		noofBuildingLbl.setBounds(50,90,120,20);
		custNameTxt.setBounds(180,50,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		customerTypeTxt.setBounds(180,90,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		//noofBuildingTxt.setBounds(180,130,AppConstants.TET_FIELD_WIDTH,AppConstants.TET_FIELD_HEIGHT);
		noofBuildingTxt.setBounds(180,90,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		edit.setBounds(100,180, 100,20);
		cancelBtn.setBounds(220,180, 100,20);
		
		jp2.setLayout(new BorderLayout());
		jp2.add(jtab,BorderLayout.CENTER);
		Border border1=BorderFactory.createLineBorder(Color.GRAY);
		jtab.setBorder(border1);
		edit.addActionListener(this);
		setBounds(100, 100, 800, 600);
		
		jp1.setBorder(border1);
		setScreenPosition();
		if(AppConstants.BG_COLOR_SHOW){
		setBackground(AppConstants.SCREEN_BG_COLOR); 
		jp1.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
		jp2.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		}
		setVisible(true);

		
		
		
	}
	
	private void setScreenPosition(){
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,10,20);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,45,80);
		jp1.setBounds(topLeftP1.getX(), topLeftP1.getY(), (bottomRightP1.getX()-topLeftP1.getX()), 230);//(bottomRightP1.getY()-topLeftP1.getY())
		
		AlarmPoint topLeftP2=AlarmUtil.getWidowScreenPoint(width,height,50,10);
		AlarmPoint bottomRightP2=AlarmUtil.getWidowScreenPoint(width,height,85,60);
		jp2.setBounds(topLeftP2.getX(), topLeftP2.getY(), (bottomRightP2.getX()-topLeftP2.getX()), (bottomRightP2.getY()-topLeftP2.getY()));
		customerContactUI.setScreenPosition((bottomRightP2.getX()-topLeftP2.getX()), (bottomRightP2.getY()-topLeftP2.getY()));
		customerAddressUI.setScreenPosition((bottomRightP2.getX()-topLeftP2.getX()), (bottomRightP2.getY()-topLeftP2.getY()));
		if(AlarmMonitor.isAdminUser){
			AlarmPoint topLeftP=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 25, 3);
			AlarmPoint bottomRightP=AlarmUtil.getWidowScreenPoint(AlarmMonitorMainUI.screenWidth, AlarmMonitorMainUI.screenHeight, 75, 15);
			
			jp.setBounds(topLeftP.getX(), topLeftP.getY(),480,30 );//(bottomRightP.getX()-topLeftP.getX()) , (bottomRightP.getY()-topLeftP.getY()));
			
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==edit){
			if(edit.getLabel().equalsIgnoreCase("Edit")){
				custNameTxt.setEditable(true);
				//noofBuildingTxt.setEditable(true);
				customerTypeTxt.setEditable(true);
				cancelBtn.setVisible(true);
				
				
			edit.setLabel("Save");
			
			addUser=false;
			}else{
				custNameTxt.setEditable(false);
				noofBuildingTxt.setEditable(false);
				customerTypeTxt.setEditable(false);
				
				boolean showDialog=false;
				String userMessage=null;
				String messageTitle=null;
				int dialogType=JOptionPane.INFORMATION_MESSAGE;
				AlarmMonitorMainUI.showBlockUI();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(addUser){
					if(WSCommUtil.createCustomer(custNameTxt.getText().trim())){
						//JOptionPane.showMessageDialog(null, "Customer Created Successfully","Customer creation Status",JOptionPane.INFORMATION_MESSAGE);
						userMessage="Customer Created Successfully";
						messageTitle="Customer creation Status";
						dialogType=JOptionPane.INFORMATION_MESSAGE;
						showDialog=true;
						WSCommUtil.populateCustomerAnalytics();
						initializeData();
						
						
					}else{
						//JOptionPane.showMessageDialog(null, "Customer Creation Failed","Customer creation Status",JOptionPane.ERROR_MESSAGE);
						showDialog=true;
						userMessage="Customer Creation Failed";
						messageTitle="Customer creation Status";
						
						dialogType=JOptionPane.ERROR_MESSAGE;
						
					}
						
					clear();	
					}else{
						if(WSCommUtil.updateCustomer(custNameTxt.getText().trim())){
							//JOptionPane.showMessageDialog(null, "Customer updated Successfully","Customer updation Status",JOptionPane.INFORMATION_MESSAGE);
							showDialog=true;
							userMessage="Customer updated Successfully";
							messageTitle="Customer updation Status";
							
							dialogType=JOptionPane.INFORMATION_MESSAGE;
							WSCommUtil.populateCustomerAnalytics();
							initializeData();
						}
						else{
							showDialog=true;	
							dialogType=JOptionPane.ERROR_MESSAGE;
							userMessage="Customer updation Failed";
							messageTitle="Customer updation Status";
							
							}
					}
				customerNameText.setVisible(false);
				chooseCustomerCombo.setVisible(true);
				cancelBtn.setVisible(false);
				chooseCustomerCombo.setEnabled(true);
				edit.setLabel("Edit");
				//AlarmMonitorMainUI.unblockUI();
				
				if(showDialog){
					JOptionPane.showMessageDialog(null, userMessage,messageTitle,dialogType);					
				}
				
			}
		}else if(ae.getSource()==customerAddBtn){
			JOptionPane.showMessageDialog(null, "Going to create new Customer");
			clear();
			edit.setLabel("Save");
			custNameTxt.setEditable(true);
			//noofBuildingTxt.setEditable(true);
			noofBuildingTxt.setEditable(false);
			customerTypeTxt.setEditable(true);
			//chooseCustomerCombo.setVisible(false);
			chooseCustomerCombo.setEnabled(false);
			//customerNameText.setVisible(true);
			cancelBtn.setVisible(true);
			addUser=true;
		}else if (ae.getSource()==cancelBtn){
			custNameTxt.setEditable(false);
			noofBuildingTxt.setEditable(false);
			customerTypeTxt.setEditable(false);
			cancelBtn.setVisible(false);
			edit.setLabel("Edit");
			//customerNameText.setVisible(false);
			chooseCustomerCombo.setEnabled(true);
			chooseCustomerCombo.setVisible(true);
			addUser=false;
		}
		
	}
	private void clear(){
	custNameTxt.setText("");
	noofBuildingTxt.setText("");
	customerTypeTxt.setText("");
	
	}
	public void populateData(){
		if(WSCommUtil.customerDetails!=null){
			CustomerDTO custDTO=WSCommUtil.customerDetails;
			custNameTxt.setText(custDTO.getCustomerName()!=null?custDTO.getCustomerName():"");
			noofBuildingTxt.setText("");
			customerTypeTxt.setText("");
			
			customerAddressUI.populateData(custDTO);
			customerContactUI.populateData(custDTO);
		}
	}
	
	
	private void initializeData(){
		chooseCustomerCombo.removeAllItems();
		//chooseCustomerCombo.addItem("Select Customer");
		chooseCustomerCombo.addItem("  ");
		
		if(WSCommUtil.customerMap!=null && WSCommUtil.customerMap.size()>0){
			
			Iterator<Map.Entry<String,Integer>> iter=WSCommUtil.customerMap.entrySet().iterator();
			
			while(iter.hasNext()){
				Map.Entry<String,Integer> entry=iter.next();
				chooseCustomerCombo.addItem(entry.getKey());
			}
			
		}
		
	}
	
	
	@Override
	public void itemStateChanged(ItemEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == chooseCustomerCombo){
			
			//AlarmMonitorMainUI.showBlockUI();
			String itemName=(String)chooseCustomerCombo.getSelectedItem();
			if(itemName!=null && !itemName.trim().isEmpty()){
			WSCommUtil.customerId=WSCommUtil.customerMap.get(itemName)+"";
			AlarmMonitor.dataUpdated=true;
			}
			//AlarmMonitorMainUI.unblockUI();
			
			//JOptionPane.showMessageDialog(null, "Selected Customer - "+itemName +"- "+WSCommUtil.customerMap.get(itemName));

		}
	}
	
	
}
