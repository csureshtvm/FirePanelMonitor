package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.BuildingDTO;
import com.vision.alarmmonitor.dto.BuildingsummaryDTO;
import com.vision.alarmmonitor.dto.ContactDetailsDTO;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.ImageDownloader;
import com.vision.alarmmonitor.util.WSCommUtil;

public class BuildingUI extends JPanel  implements ActionListener,ItemListener{

	JLabel jl;
	ImageIcon icon;
	Logger log = Logger.getLogger("BuildingUI");
	JLabel bldgNameLbl,bldgLocLbl,bldgLandMarkLbl,bldgMarakaniNoLbl,bldhnearPolLbl,bldgNearCivilDefLbl,bldgnearHospLbl;
	JTabbedPane jtab;
	
	JLabel chooseBuildingLbl=null;
	JComboBox chooseBuildingCombo=null;
	CustomerAddressUI customerAddressUI=null;
	CustomerContactUI customerContactUI=null;
	

	JButton edit;
	JPanel jp1,jp2;
	
	JPanel jp3;
	
	JTextField bldgNameTxt,bldgLocTxt,bldgLandMarkTxt,bldgMarakaniNoTxt,bldhnearPolTxt,bldgNearCivilDefTxt,bldgnearHospTxt;
	Vector<BuildingDTO> buildingList=new Vector<BuildingDTO>();
	
	JLabel bldgImageLbl;
	BuildingImageUI buildingImageUI=new BuildingImageUI();
	Integer selectedBldgContactId=0;
	
	JButton bldgAddBtn,cancelBtn;
	boolean addNewBuilding=false;
	
	

	public BuildingUI(){
		bldgNameLbl=new JLabel("Building Name");
		chooseBuildingLbl=new JLabel("Choose building");
		chooseBuildingCombo=new JComboBox();
		bldgLocLbl=new JLabel("Location");
		bldgImageLbl=new JLabel();
		bldgLandMarkLbl=new JLabel("Landmark");
		bldgMarakaniNoLbl=new JLabel("Marakani No");
		bldhnearPolLbl=new JLabel("Near Polic Station");
		bldgNearCivilDefLbl=new JLabel("Near Civil Defence");
		bldgnearHospLbl=new JLabel("Near Hospital");
		cancelBtn=new JButton("Cancel");
		
		bldgNameTxt=new JTextField();
		bldgLocTxt=new JTextField();
		bldgLandMarkTxt=new JTextField();
		bldgMarakaniNoTxt=new JTextField();
		bldhnearPolTxt=new JTextField();
		bldgNearCivilDefTxt=new JTextField();
		bldgnearHospTxt=new JTextField();
		
		bldgNameTxt.setEditable(false);
		bldgLocTxt.setEditable(false);
		bldgLandMarkTxt.setEditable(false);
		bldgMarakaniNoTxt.setEditable(false);
		bldhnearPolTxt.setEditable(false);
		bldgNearCivilDefTxt.setEditable(false);
		bldgnearHospTxt.setEditable(false);
		edit=new JButton("Edit");
		
		customerAddressUI=new CustomerAddressUI("building");
		customerContactUI=new CustomerContactUI("building");
		Border border1=BorderFactory.createLineBorder(Color.GRAY);
		//Border border2=BorderFactory.createTitledBorder("Contact Details");
		
		//customerAddressUI.setBorder(border1);
		//customerContactUI.setBorder(border2);
		
		jtab = new JTabbedPane();
		jtab.addTab("Address Details", null, customerAddressUI, "Tab1");
		jtab.addTab("Contact Details", null, customerContactUI, "Tab3");
		
		jp1=new JPanel();
		jp2=new JPanel();
		jtab.setBorder(border1);
		jp1.setBorder(border1);
		setLayout(null);
		//jp1.setBounds(50,150,348,400);
		//jp2.setBounds(400,150,350,300);
		
		
		
		jp3=new JPanel();
		
		
		add(jp1);
		add(jp2);
		add(jp3);
		jp3.setLayout(null);
		jp3.add(chooseBuildingLbl);
		jp3.add(chooseBuildingCombo);
		jp3.add(bldgImageLbl);
		
		try {
			Image img =ImageIO.read(getClass().getResource("/resources/add_ico.png")).getScaledInstance(25, 25,Image.SCALE_DEFAULT);
		
		//ImageIcon addIco = new ImageIcon("resources/add_icon.jpg");
		//Image scaleImage = addIco.getImage().getScaledInstance(50, 25,Image.SCALE_DEFAULT);
			bldgAddBtn = new JButton(new ImageIcon(img));
	   
			bldgAddBtn.setToolTipText("Add New");
			bldgAddBtn.setOpaque(false);
			bldgAddBtn.setContentAreaFilled(false);
			bldgAddBtn.setBorderPainted(false);
	   
	    jp3.add(bldgAddBtn);
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		chooseBuildingLbl.setBounds(0,40,120,20);
		chooseBuildingCombo.setBounds(130,40,250,20);
		bldgAddBtn.setBounds(390, 40, 25, 25);
		
		bldgImageLbl.setBounds(420,0,200,100);
		bldgImageLbl.setForeground(Color.BLUE);
		Font f=new Font("Verdana", Font.ITALIC, 12);
		bldgImageLbl.setFont(f);
		
		jp1.setLayout(null);
		jp1.add(bldgNameLbl);
		jp1.add(bldgLocLbl);
		jp1.add(bldgLandMarkLbl);
		jp1.add(bldgNameTxt);
		jp1.add(bldgLocTxt);
		jp1.add(bldgLandMarkTxt);
		jp1.add(bldgMarakaniNoLbl);
		jp1.add(bldhnearPolLbl);
		jp1.add(bldgNearCivilDefLbl);
		jp1.add(bldgnearHospLbl);
		
		jp1.add(bldgMarakaniNoTxt);
		jp1.add(bldhnearPolTxt);
		jp1.add(bldgNearCivilDefTxt);
		jp1.add(bldgnearHospTxt);
		jp1.add(cancelBtn);
		
		
		jp1.add(edit);
		bldgNameLbl.setBounds(50,20,120,20);
		bldgLocLbl.setBounds(50,50,120,20);
		bldgLandMarkLbl.setBounds(50,80,120,20);
		bldgMarakaniNoLbl.setBounds(50,110,120,20);
		bldhnearPolLbl.setBounds(50,140,140,20);
		bldgNearCivilDefLbl.setBounds(50,170,120,20);
		bldgnearHospLbl.setBounds(50,200,120,20);
		
		
		bldgNameTxt.setBounds(180,20,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		bldgLocTxt.setBounds(180,50,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		bldgLandMarkTxt.setBounds(180,80,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		bldgMarakaniNoTxt.setBounds(180,110,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		bldhnearPolTxt.setBounds(180,140,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		bldgNearCivilDefTxt.setBounds(180,170,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		bldgnearHospTxt.setBounds(180,200,AppConstants.TEXT_FIELD_WIDTH,AppConstants.TEXT_FIELD_HEIGHT);
		edit.setBounds(100,250, 100,20);
		cancelBtn.setBounds(210,250, 100,20);
		cancelBtn.setVisible(false);
		jp2.setLayout(new BorderLayout());
		jp2.add(jtab,BorderLayout.CENTER);
		
			
		setScreenPosition();
		
		edit.addActionListener(this);
		bldgAddBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		chooseBuildingCombo.addItemListener(this);
		setBounds(100, 100, 800, 600);
		if(AppConstants.BG_COLOR_SHOW){
		setBackground(AppConstants.SCREEN_BG_COLOR); 
		jp2.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
		jp1.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR);
		jp3.setBackground(AppConstants.SCREEN_BG_COLOR);
		}
		setVisible(true);

		
	}
	
	
	private void setScreenPosition(){
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,10,25);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,45,80);
		jp1.setBounds(topLeftP1.getX(), topLeftP1.getY(), (bottomRightP1.getX()-topLeftP1.getX()), 300);//(bottomRightP1.getY()-topLeftP1.getY())
		
		AlarmPoint topLeftP2=AlarmUtil.getWidowScreenPoint(width,height,50,20);
		AlarmPoint bottomRightP2=AlarmUtil.getWidowScreenPoint(width,height,85,70);
		jp2.setBounds(topLeftP2.getX(), topLeftP2.getY(), (bottomRightP2.getX()-topLeftP2.getX()), (bottomRightP2.getY()-topLeftP2.getY()));
		
		
		AlarmPoint topLeftP3=AlarmUtil.getWidowScreenPoint(width,height,25,5);
		AlarmPoint bottomRightP3=AlarmUtil.getWidowScreenPoint(width,height,70,15);
		jp3.setBounds(topLeftP3.getX(), topLeftP3.getY(), (bottomRightP3.getX()-topLeftP3.getX()), (bottomRightP3.getY()-topLeftP3.getY()));
		
		customerContactUI.setScreenPosition((bottomRightP2.getX()-topLeftP2.getX()), (bottomRightP2.getY()-topLeftP2.getY()));
		customerAddressUI.setScreenPosition((bottomRightP2.getX()-topLeftP2.getX()), (bottomRightP2.getY()-topLeftP2.getY()));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==edit){
			if(edit.getLabel().equalsIgnoreCase("Edit")){
				enableBldgEdit();
				
			cancelBtn.setVisible(true);
			edit.setLabel("Save");
			
			}else{
				
				BuildingsummaryDTO bldgSummaryDTO=new BuildingsummaryDTO();
				
				if(!addNewBuilding){
					BuildingDTO selectedBldg=buildingList.get(chooseBuildingCombo.getSelectedIndex());
					bldgSummaryDTO.setBuildingId(Integer.parseInt(selectedBldg.getBuildingId()));
					
				}
				//bldgSummaryDTO.setBuildingLocation(bldgSummary.getBuildingLocation());
				try {
					bldgSummaryDTO.setBuildingName(bldgNameTxt.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bldgSummaryDTO.setBuildingLocation(bldgLocTxt.getText());
				//bldgSummaryDTO.setDescription(bldgNameTxt.getText());
				//bldgSummaryDTO.setBuildingTpe(bldgNameTxt.getText());
				bldgSummaryDTO.setMarakaniNo(bldgMarakaniNoTxt.getText());
				//bldgSummaryDTO.setNoOfFloor(bldgNameTxt.getText());
				bldgSummaryDTO.setNearPoliceStation(bldhnearPolTxt.getText());
				bldgSummaryDTO.setNearCivilDefence(bldgNearCivilDefTxt.getText());
				bldgSummaryDTO.setNearHospital(bldgnearHospTxt.getText());
				
				
				if(WSCommUtil.saveBuilding(bldgSummaryDTO)){
					JOptionPane.showMessageDialog(null, "Building Details saved Successfully","Building save Status",JOptionPane.INFORMATION_MESSAGE);
					
				}else{
					JOptionPane.showMessageDialog(null, "Building save Failed","Building save Status",JOptionPane.ERROR_MESSAGE);
					
				}
				
				disableBldgEdit();
				edit.setLabel("Edit");
				cancelBtn.setVisible(false);
				addNewBuilding=false;
				chooseBuildingCombo.setEditable(true);
				
			}
		}else if(ae.getSource()==bldgAddBtn){
			addNewBuilding=true;
			clear();
			enableBldgEdit();
			cancelBtn.setVisible(true);
			edit.setLabel("Save");
			chooseBuildingCombo.setEditable(false);
			bldgImageLbl.setVisible(false);
		}
		else if(ae.getSource()==cancelBtn){
			
			addNewBuilding=false;
			clear();
			disableBldgEdit();
			edit.setLabel("Edit");
			chooseBuildingCombo.setEditable(true);
			cancelBtn.setVisible(false);
		}
		
	}
	
	private void enableBldgEdit(){
		bldgNameTxt.setEditable(true);
		bldgLocTxt.setEditable(true);
		bldgLandMarkTxt.setEditable(true);
		bldgMarakaniNoTxt.setEditable(true);
		bldhnearPolTxt.setEditable(true);
		bldgNearCivilDefTxt.setEditable(true);
		bldgnearHospTxt.setEditable(true);
	}
	private void disableBldgEdit(){
		bldgNameTxt.setEditable(false);
		bldgLocTxt.setEditable(false);
		bldgLandMarkTxt.setEditable(false);
		bldgMarakaniNoTxt.setEditable(false);
		bldhnearPolTxt.setEditable(false);
		bldgNearCivilDefTxt.setEditable(false);
		bldgnearHospTxt.setEditable(false);
	}
	private void clear(){
		bldgNameTxt.setText("");
		bldgLocTxt.setText("");
		bldgLandMarkTxt.setText("");
		bldgMarakaniNoTxt.setText("");
		bldhnearPolTxt.setText("");
		bldgNearCivilDefTxt.setText("");
		bldgnearHospTxt.setText("");
	}
	public void populateData(){
		String selectedBuildingName=chooseBuildingCombo.getSelectedIndex()>=0?chooseBuildingCombo.getSelectedItem().toString():null;
		if(buildingList!=null && WSCommUtil.buildingList!=null){
			buildingList.clear();
		}
		if(WSCommUtil.buildingList!=null){
		buildingList.addAll(WSCommUtil.buildingList);
		chooseBuildingCombo.removeAllItems();
		
		for(BuildingDTO bldg : buildingList){
			try {
				chooseBuildingCombo.addItem(bldg.getBuildingName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(selectedBuildingName!=null && selectedBuildingName.equalsIgnoreCase(bldg.getBuildingName())){
				chooseBuildingCombo.setSelectedItem(bldg.getBuildingName());
			}
		}
		}
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == chooseBuildingCombo){
			if(chooseBuildingCombo.getSelectedIndex()>=0 && buildingList!=null && chooseBuildingCombo.getSelectedIndex()<buildingList.size()){
			BuildingDTO selectedBldg=buildingList.get(chooseBuildingCombo.getSelectedIndex());
			
			try {
				if(selectedBldg.getBuildingImageUrl()!=null && !selectedBldg.getBuildingImageUrl().trim().isEmpty()){
				showImage(selectedBldg.getBuildingImageUrl(),selectedBldg.getBuildingId());
				}else{
					bldgImageLbl.setIcon(null);
					bldgImageLbl.setText("");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bldgNameTxt.setText(selectedBldg.getBuildingName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bldgLocTxt.setText(selectedBldg.getBuildingLocation());
			
			bldgMarakaniNoTxt.setText(selectedBldg.getMarakaniNo());
			bldhnearPolTxt.setText(selectedBldg.getNearPoliceStation());
			bldgNearCivilDefTxt.setText(selectedBldg.getNearCivilDefence());
			bldgnearHospTxt.setText(selectedBldg.getNearHospital());
			
			customerAddressUI.populateData(selectedBldg);
			customerContactUI.populateData(selectedBldg);
			}
		}
		
	}
	
	
	private void showImage(String bldgImage,String buildingId) throws IOException{
		
		BufferedImage bufImage=ImageDownloader.retreiveImageFile(bldgImage,buildingId);
		
		final ImageIcon icon=new ImageIcon(bufImage);
		final int height=icon.getIconHeight();
		final int width=icon.getIconWidth();
		
		
		
		Image scaleImage = icon.getImage().getScaledInstance(120, 70,Image.SCALE_DEFAULT);
		
		//bldgImageLbl.setIcon(new ImageIcon(scaleImage));
		bldgImageLbl.setText("<HTML><U>click here to view image</U></HTML>");
		bldgImageLbl.setHorizontalTextPosition(JLabel.CENTER);
		bldgImageLbl.setVerticalTextPosition(JLabel.BOTTOM);
		bldgImageLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bldgImageLbl.addMouseListener(new MouseAdapter() {		
		   public void mouseClicked(MouseEvent e) {
			   
			   //System.out.println("Mouse click event triggeredddddddddd:"+System.currentTimeMillis());
		      if (e.getClickCount() > 0) {
		          
		    	  int screenWith=AlarmMonitorMainUI.width;
		    	  int screenHeight=AlarmMonitorMainUI.height;
		    	  int imageWidth=(((width)>(AlarmMonitorMainUI.screenWidth*0.8))?(int)(AlarmMonitorMainUI.screenWidth*0.8):width);
		    	  int imageHeight=((height)>(AlarmMonitorMainUI.screenHeight*0.8)?(int)(AlarmMonitorMainUI.screenHeight*0.8):height);
		    	  Image scaleImage = icon.getImage().getScaledInstance(imageWidth,imageHeight ,Image.SCALE_DEFAULT);
		    	  buildingImageUI.imageLable.setIcon(new ImageIcon(scaleImage));
		    	  buildingImageUI.imageLable.setBounds(0,0,imageWidth,imageHeight);
		    	  buildingImageUI.setVisible(true);
		    	  
		    	  
		    	  System.out.println(width+":"+height+":"+imageWidth+":"+imageHeight);
		    	  buildingImageUI.setBounds((screenWith-imageWidth)/2,(screenHeight-imageHeight)/2,imageWidth,imageHeight);
				    	  
		      }
		   }
		});
		
		//JLabel wIcon = new JLabel(icon);
	}

}
