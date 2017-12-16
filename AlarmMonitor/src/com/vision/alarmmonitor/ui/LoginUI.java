package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

public class LoginUI extends JFrame implements ActionListener{
	
	JPanel jp;
	
	JLabel userNamelbl,passwdlbl;
	JTextField userNameTxt,passwdTxt;
	
	JButton btnLogin,btnCancel;
	JButton logoBtn = null;

	static int width=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static int height=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public LoginUI(){
		super("Login");
		userNamelbl=new JLabel("User Name");
		passwdlbl=new JLabel("Password");
		
		//userNameTxt=new JTextField("visionuser");
		//passwdTxt=new JPasswordField("visionfocus");
		
		userNameTxt=new JTextField("visionuser");
		passwdTxt=new JPasswordField("visionfocus");
		
		btnLogin=new JButton("Login");
		btnCancel=new JButton("Cancel");
		
		jp=new JPanel();
		jp.add(userNamelbl);
		jp.add(passwdlbl);
		jp.add(userNameTxt);
		jp.add(passwdTxt);
		jp.add(btnLogin);
		jp.add(btnCancel);
		jp.setLayout(null);
		
		setLayout(null);
		add(jp);
		Border border=BorderFactory.createLineBorder(Color.GRAY);
		jp.setBorder(border);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		
		if(AppConstants.BG_COLOR_SHOW){
			getContentPane().setBackground(AppConstants.SCREEN_BG_COLOR); 
			jp.setBackground(AppConstants.INNER_SCREEN_COMPT_BG_COLOR); 
			
		}
		/*try {
			Image img =ImageIO.read(getClass().getResource("/resources/add_icon.jpg")).getScaledInstance(50, 25,Image.SCALE_DEFAULT);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		Image img=null;
		try {
		System.out.println("Gong to load Image");
		img = ImageIO.read(getClass().getResource("/resources/vision365_1.png"));//
		System.out.println("Image created...Going to scale image");
		img =img.getScaledInstance(156, 34,Image.SCALE_DEFAULT);
		System.out.println("Image scaled");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Option 1 error");
			//e.printStackTrace();
		}
		
		
		logoBtn = new JButton(new ImageIcon(img));
		logoBtn.setToolTipText("Vision 365");
		logoBtn.setOpaque(false);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setBorderPainted(false);
		
		add(logoBtn);
		setScreenPosition();
		setVisible(true);
		
	}
	
	private void setScreenPosition(){
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,30,30);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,70,70);
		int frameWidth=(bottomRightP1.getX()-topLeftP1.getX());
		int frameHeight=(bottomRightP1.getY()-topLeftP1.getY());
		setBounds(topLeftP1.getX(), topLeftP1.getY(),frameWidth , frameHeight);//(bottomRightP1.getY()-topLeftP1.getY())
		
		int comptTotalWidth=350;
		int comptTotalHeight=180;
		
		
		int topX=(frameWidth-comptTotalWidth)/2;
		int topY=(frameHeight-comptTotalHeight)/2;
		
		jp.setBounds(topX, topY-20,comptTotalWidth,comptTotalHeight );//(frameWidth-topX), (frameHeight-topY));
		
		logoBtn.setBounds((frameWidth/2)-78,5,156,34);
		
		
		userNamelbl.setBounds(30, 30, 100, 20);
		passwdlbl.setBounds(30, 70, 100, 20);
		userNameTxt.setBounds(140, 30, 180, 20);
		passwdTxt.setBounds(140, 70, 180, 20);
		btnLogin.setBounds(60, 120, 80, 20);
		btnCancel.setBounds(180, 120, 80, 20);
		
		//AlarmPoint topLeftP2=AlarmUtil.getWidowScreenPoint(width,height,50,20);
		//AlarmPoint bottomRightP2=AlarmUtil.getWidowScreenPoint(width,height,85,70);
		//jp.setBounds(topLeftP2.getX(), topLeftP2.getY(), (bottomRightP2.getX()-topLeftP2.getX()), (bottomRightP2.getY()-topLeftP2.getY()));
		
		
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource()==btnLogin){
			String s = "%3A%22%3F%3F%3F%3F%3F%3F%3F%3F%3F+%3F%3F%3F%3F%3F%3F%3F%3F%3F%3F+-+%3F%3F%3F%3F%3F%3F%3F%3F%3F+%22%2C%22";//"\u6253\u5370\u8FC7\u671F\u8BC1\u4E66\u8BB0\u5F55"; 
			//JOptionPane.showMessageDialog(null, s);
			
			if(userNameTxt.getText().isEmpty() || passwdTxt.getText().isEmpty() ){
				JOptionPane.showMessageDialog(null, "User Name and Password are mandatory.", "Validation Failed", JOptionPane.ERROR_MESSAGE);
			}else{
			
			Map<String,Object> responseMap=WSCommUtil.loginUser(userNameTxt.getText().trim(), passwdTxt.getText().trim());
			if(responseMap!=null && String.valueOf(responseMap.get("status")).equals(AppConstants.STATUS_FAILED)){
				JOptionPane.showMessageDialog(null, String.valueOf(responseMap.get("message")), "Login Error", JOptionPane.ERROR_MESSAGE);
			}else if(responseMap!=null && String.valueOf(responseMap.get("status")).equals(AppConstants.STATUS_SUCCESS)){
				UserDTO userDTO=(UserDTO)responseMap.get("user");
				if(AppConstants.ROLE_ADMIN.equalsIgnoreCase(userDTO.getUserRole())){
					setVisible(false);
					AlarmMonitor.isAdminUser=true;
					AlarmMonitor.loginUI=this;
					AlarmMonitor.getInstance().startMonitor();
					WSCommUtil.customerId=null;
				}else if (AppConstants.ROLE_SUPPORT_USER.equalsIgnoreCase(userDTO.getUserRole())){
					setVisible(false);
					AlarmMonitor.isSupportUser=true;
					AlarmMonitor.getInstance().startMonitor();
					WSCommUtil.customerId=null;
					
				}else{
					if(userDTO.getCustomerId()==null || userDTO.getCustomerId()==0){
						JOptionPane.showMessageDialog(null, "You do not have permission to access the system. Please Contact Administrator",  "Login Error", JOptionPane.ERROR_MESSAGE);
					}else{
						WSCommUtil.customerId=String.valueOf(userDTO.getCustomerId());
						setVisible(false);
						AlarmMonitor.getInstance().startMonitor();
					}
				}
				
			}else{
				JOptionPane.showMessageDialog(null, AppConstants.LOGIN_fAILURE_ERR_MSG,  "Login Error", JOptionPane.ERROR_MESSAGE);
			}
			
			}
			
		}else if(ae.getSource()==btnCancel){
			setVisible(false);
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args) {
		new LoginUI();
	}
}
