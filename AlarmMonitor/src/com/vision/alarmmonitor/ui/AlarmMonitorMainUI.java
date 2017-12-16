package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;


public class AlarmMonitorMainUI extends JFrame implements WindowStateListener,ActionListener {

	
	JPanel panel1,panel2;
	public static BlockUI blockUI=null;
	JTabbedPane jtab;
	static AlarmEventsUI alarmEventUI=null;
	AlarmGraphicalUI alarmGraphicalUI=null;
	HomeUI homeUI=null;
	CustomerUI customerUI=null;
	ThirdPartiesUI thirdPartyUI=null;
	ScheduleUI scheduleUI=null;
	HelpUI helpUI=new HelpUI();
	Logger log = Logger.getLogger("MainForm");
	BuildingUI buildingUI=null;
	NotificationSettingsUI notfnSettingUI=null;
	DashboardUI dashboardUI=null;
	PreferenceUI preferenceUI=null;
	ManageUserUI manageUserUI=null;
	ChangePasswordUI changePWUI=null;
	MaintenanceScheduleUI maintenanceScheduleUI;
	static AlarmDeviceStatusUI alarmDevStatusUI=null;
	static int width=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static int height=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	static int screenWidth=0;
	static int screenHeight=0;
	static AlarmPoint topLeft=AlarmUtil.getWidowScreenPoint(width,height,0,0);
	static AlarmPoint bottomRight=AlarmUtil.getWidowScreenPoint(width,height,100,100);
	JMenuItem passwordMenu,aboutMenu,logoutMenu,exitMenu;
	JMenuBar jmb;
	LoginUI loginUI=null;
	static{
		screenWidth=(bottomRight.getX()-topLeft.getX());
		screenHeight=(bottomRight.getY()-topLeft.getY());
	}
	JPanel logoPanel;
	
	//JButton logoutBtn,chagePwBtn,aboutUsBtn,exitBtn;
	
	JLabel logoutBtnLbl,closeBtnLbl,welcomeLabel,changePwLbl;
	
	
	static AlarmEventResetUI alarmResetUI=new AlarmEventResetUI();
	public AlarmMonitorMainUI(LoginUI loginUI) {

		super("Alarm Monitor");
		this.loginUI=loginUI;
		blockUI=new BlockUI(this);
		blockUI.showBlockUI=false;
		LookAndFeelInfo[] lookandfeeArr = javax.swing.UIManager
				.getInstalledLookAndFeels();
		LookAndFeelInfo looknfeelSelected=null;
		for (LookAndFeelInfo inf : lookandfeeArr) {
			System.out.println("LookAndFeel ----->" + inf.getName());
			System.out.println(inf);
			if(inf.getName().toLowerCase().contains("nimbus")){
				looknfeelSelected=inf;
				break;
			}
		}
		
		
		jmb=new JMenuBar();
		panel1=new JPanel();
		panel2=new JPanel();
		JMenu optionMenu = new JMenu("Options");
		//passwordMenu.setMnemonic(KeyEvent.VK_C);
		jmb.add(optionMenu);
		
		passwordMenu = new JMenuItem("Change Password");
		passwordMenu.setMnemonic(KeyEvent.VK_C);
		optionMenu.add(passwordMenu);
		
		 aboutMenu = new JMenuItem("About Us");
		 aboutMenu.setMnemonic(KeyEvent.VK_A);
		 optionMenu.add(aboutMenu);

		 logoutMenu = new JMenuItem("Logout");
		 logoutMenu.setMnemonic(KeyEvent.VK_L);
		 optionMenu.add(logoutMenu);
		 
		 exitMenu = new JMenuItem("Exit");
		 exitMenu.setMnemonic(KeyEvent.VK_E);
		 optionMenu.add(exitMenu);
		
		
		/*try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}*/
		
		try {
			if (looknfeelSelected !=null)
				javax.swing.UIManager.setLookAndFeel(looknfeelSelected.getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		jtab = new JTabbedPane();
		alarmEventUI=new AlarmEventsUI();
		alarmGraphicalUI=new AlarmGraphicalUI();
		homeUI=new HomeUI();
		
		thirdPartyUI=new ThirdPartiesUI();
		scheduleUI=new ScheduleUI();
		helpUI=new HelpUI();
		buildingUI=new BuildingUI();
		notfnSettingUI=new NotificationSettingsUI();
		changePWUI=new ChangePasswordUI();
		alarmDevStatusUI=new AlarmDeviceStatusUI();
		if(AlarmMonitor.isAdminUser){
			dashboardUI=new DashboardUI();
			jtab.addTab("Dashboard", null, dashboardUI, "Dashboard");
			
			manageUserUI=new ManageUserUI();
			jtab.addTab("Add User Account", null, manageUserUI, "Manage User");
			//preferenceUI=new PreferenceUI();
			//jtab.addTab("Preference", null, preferenceUI, "Preference");
		}
		customerUI=new CustomerUI();
		if(AlarmMonitor.isAdminUser){
		jtab.addTab("Customer", null, customerUI, "Customer Details");
		}
		
		jtab.addTab("Alarms", null, alarmEventUI, "Alarm Events");
		if(!AlarmMonitor.isSupportUser){
		//jtab.addTab("Alarms(Graphical)", null, alarmGraphicalUI, "Alarm Events(Graphical)");		
		jtab.addTab("Buildings", null, buildingUI, "Building Details");
		jtab.addTab("Third Parties", null, thirdPartyUI, "Third Parties");
		jtab.addTab("Notification Settings", null, notfnSettingUI, "Notification Settings");
		
		maintenanceScheduleUI=new MaintenanceScheduleUI();
		jtab.addTab("Maintenance Schedule", null, maintenanceScheduleUI, "Schedule");
		}
		jtab.addTab("Alarm Devices", null, alarmDevStatusUI, "Alarm Devices");
		
		//jtab.addTab("Change Password", null, changePWUI, "Change Password");
		//jtab.addTab("About", null, helpUI, "About");
		
		//jtab.addTab("Configure Controller", null, configPanel, "Tab2");
		//jtab.addTab("Device Status", null, devUI, "Tab1");
		//jtab.addTab("Communication Logs", null, logPanel, "Tab3");
		//jtab.addTab("Serial Settings", null, settingsUI, "Tab3");
		
		
		Container c=getContentPane();
		c.setLayout(null);
		c.add(panel1);
		c.add(panel2);
		
		panel2.setLayout(new BorderLayout());
		panel2.add(jtab, BorderLayout.CENTER);
		///jtab.setBounds(0,0,700,600);
		
		
		
		
		
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
			panel1.setBackground(AppConstants.SCREEN_BG_COLOR); 
			panel2.setBackground(AppConstants.SCREEN_BG_COLOR); 
			jtab.setBackground(AppConstants.SCREEN_BG_COLOR); 
		}
		addWindowStateListener(this);
		 ChangeListener changeListener = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				// TODO Auto-generated method stub
				 JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			        int index = sourceTabbedPane.getSelectedIndex();
			       String selectedTabName= sourceTabbedPane.getTitleAt(index);
			       //"Customer"
					//.equalsIgnoreCase(selectedTabName) || "Customer"
					//.equalsIgnoreCase(selectedTabName) || 
				if (AlarmMonitor.isAdminUser && WSCommUtil.customerId == null
						&& ("Alarms".equalsIgnoreCase(selectedTabName) || "Buildings"
								.equalsIgnoreCase(selectedTabName) || "Third Parties"
								.equalsIgnoreCase(selectedTabName) || "Notification Settings"
								.equalsIgnoreCase(selectedTabName) || "Schedule"
								.equalsIgnoreCase(selectedTabName)|| "Alarm Devices"
								.equalsIgnoreCase(selectedTabName))) {
					jtab.setSelectedIndex(2);
					JOptionPane.showMessageDialog(null, "No Customer Selected. Please choose Customer from Customer screen. ", "Customer Selection failed", JOptionPane.ERROR_MESSAGE);
					

				}
			        
			       // System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
			}
		};
		
		jtab.addChangeListener(changeListener);
		passwordMenu.addActionListener(this);
		aboutMenu.addActionListener(this);
		logoutMenu.addActionListener(this);
		exitMenu.addActionListener(this);
		setResizable(false);
		//setBackground(StaticUtils.BACKGROUND_COLOR);
		setVisible(true);
		try {
			Font f=new Font("Verdana", Font.BOLD | Font.ITALIC, 12);
			panel1.setLayout(null);
			logoutBtnLbl=new JLabel();
			logoutBtnLbl.setText("<HTML><U>Sign Out</U></HTML>");
			logoutBtnLbl.setFont(f);
			welcomeLabel=new JLabel("Welcome "+AlarmMonitor.loginUserFullName);
			
			closeBtnLbl=new JLabel();
			closeBtnLbl.setText("<HTML><U>Exit</U></HTML>");
			closeBtnLbl.setFont(f);
			
			
			changePwLbl=new JLabel();
			changePwLbl.setText("<HTML><U>Change Password</U></HTML>");
			changePwLbl.setFont(f);
			
			Image img =ImageIO.read(getClass().getResource("/resources/vision365_1.png")).getScaledInstance(156, 34,Image.SCALE_DEFAULT);
		
		
			JButton logoBtn = new JButton(new ImageIcon(img));
	   
			logoBtn.setToolTipText("Vision 365");
			logoBtn.setOpaque(false);
			logoBtn.setContentAreaFilled(false);
			logoBtn.setBorderPainted(false);
			
			//logoPanel=new JPanel();
			Font f1=new Font("Verdana", Font.BOLD | Font.ITALIC, 14);
			welcomeLabel.setFont(f1);
			
			panel1.add(logoBtn);
			panel1.add(logoutBtnLbl);
			panel1.add(closeBtnLbl);
			panel1.add(welcomeLabel);
			panel1.add(changePwLbl);
			
			
			
			logoBtn.setBounds((width/2)-40,5,156,34);
			changePwLbl.setBounds(width-335,2,140,20);
			logoutBtnLbl.setBounds(width-200,2,70,20);
			closeBtnLbl.setBounds(width-130,2,100,20);
			
			
			
			welcomeLabel.setBounds(100,10,400,20);
			welcomeLabel.setForeground(Color.BLUE);
			
			logoutBtnLbl.setHorizontalTextPosition(JLabel.CENTER);
			logoutBtnLbl.setVerticalTextPosition(JLabel.BOTTOM);
			logoutBtnLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			logoutBtnLbl.setForeground(Color.BLUE);
			
			closeBtnLbl.setHorizontalTextPosition(JLabel.CENTER);
			closeBtnLbl.setVerticalTextPosition(JLabel.BOTTOM);
			closeBtnLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			closeBtnLbl.setForeground(Color.BLUE);
			
			
			changePwLbl.setHorizontalTextPosition(JLabel.CENTER);
			changePwLbl.setVerticalTextPosition(JLabel.BOTTOM);
			changePwLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			changePwLbl.setForeground(Color.BLUE);
			
			final LoginUI loginUIforPopup=loginUI;
			logoutBtnLbl.addMouseListener(new MouseAdapter() {		
			   public void mouseClicked(MouseEvent e) {
				   
				   //System.out.println("Mouse click event triggeredddddddddd:"+System.currentTimeMillis());
			      if (e.getClickCount() > 0) {
			          
			    	  setVisible(false);
			    	  loginUIforPopup.setVisible(true);
			      }
			   }
			});
			
			//final ChangePasswordUI changePwUIPopup=changePWUI;
			changePwLbl.addMouseListener(new MouseAdapter() {		
			   public void mouseClicked(MouseEvent e) {
				   
				   //System.out.println("Mouse click event triggeredddddddddd:"+System.currentTimeMillis());
			      if (e.getClickCount() > 0) {
			          
			    	  changePWUI.setVisible(true);
			      }
			   }
			});
			
			
			closeBtnLbl.addMouseListener(new MouseAdapter() {		
				   public void mouseClicked(MouseEvent e) {
					   
					   //System.out.println("Mouse click event triggeredddddddddd:"+System.currentTimeMillis());
				      if (e.getClickCount() > 0) {
				          
				    	  System.exit(0);
				      }
				   }
				});
			
			//add(logoPanel,BorderLayout.PAGE_START);
		//	logoPanel.setBounds(800, 0, 100, 100);
			//logoPanel.setBounds(600,10,100,100);
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setScreenPosition();
		//this.setJMenuBar(jmb);
		c.setBounds(topLeft.getX(), topLeft.getY(), (bottomRight.getX()-topLeft.getX()), (bottomRight.getY()-topLeft.getY()));
		this.setBounds(topLeft.getX(), topLeft.getY(), (bottomRight.getX()-topLeft.getX()), (bottomRight.getY()-topLeft.getY()));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void setScreenPosition(){
		
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,0,0);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,100,5);
		
		panel1.setBounds(topLeftP1.getX(),topLeftP1.getY(),(bottomRightP1.getX()-topLeftP1.getX()),(bottomRightP1.getY()-topLeftP1.getY()));
		
		AlarmPoint topLeftP2=AlarmUtil.getWidowScreenPoint(width,height,0,5);
		AlarmPoint bottomRightP2=AlarmUtil.getWidowScreenPoint(width,height,100,100);
		
		
		System.out.println("Panel 2=====>"+topLeftP2.getX()+":"+topLeftP2.getY()+":"+(bottomRightP2.getX()-topLeftP2.getX())+":"+(bottomRightP2.getY()-topLeftP2.getY()));
		panel2.setBounds(topLeftP2.getX(),topLeftP2.getY(),(bottomRightP2.getX()-topLeftP2.getX()),(bottomRightP2.getY()-topLeftP2.getY()));
		
		
	
	}
	public static void main(String[] args) {
		//new AlarmMonitorMainUI();
	}

	public void close() {
		this.dispose();
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		System.out.println("State Changed:" + e.getNewState() + ":"
				+ Frame.ICONIFIED);
		if (e.getNewState() == Frame.ICONIFIED) {
			//setVisible(false);

		}

	}
	public void populateAlarmEventData(){
		alarmEventUI.populateData();
		if(!AlarmMonitor.isSupportUser){
		alarmGraphicalUI.populateData();
		}
		alarmDevStatusUI.populateData();
	}
	
	public void populateData(){
		customerUI.populateData();
		buildingUI.populateData();
		thirdPartyUI.populateData();
		notfnSettingUI.populateData();
		if(scheduleUI!=null){
		scheduleUI.populateData();
		}
		if(maintenanceScheduleUI!=null){
			maintenanceScheduleUI.populateData();
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == passwordMenu){
			changePWUI.setVisible(true);
		//	new LoginUI();
		}else if(ae.getSource() == aboutMenu){
			helpUI.setVisible(true);
			//setVisible(false);
			//	new LoginUI();
		}else if(ae.getSource() == logoutMenu){
			setVisible(false);
			loginUI.setVisible(true);
		}else if(ae.getSource() == exitMenu){
			System.exit(0);
			//	new LoginUI();
		}
		
	}
	
	public static void showBlockUI(){
		blockUI.showBlockUI=true;	
		blockUI.showBlockUI();
	}
	public static void unblockUI(){
		blockUI.showBlockUI=false;	
		blockUI.showUnblockUI();
	}
	}
