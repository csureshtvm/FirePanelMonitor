package com.vision.notificationservices.tester;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.vision.notificationservices.communicator.EmailCommunicator;
import com.vision.notificationservices.dto.AlarmNotfnEventDetailsDTO;
import com.vision.notificationservices.util.NetUtil;
import com.vision.notificationservices.util.WSCommUtil;

public class EmailTesterUI extends JFrame{
	
	
	JTextArea jt;
	static int width=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static int height=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public EmailTesterUI(){
		setLayout(new BorderLayout());
		
		jt=new JTextArea();
		JScrollPane jsp=new JScrollPane(jt);
		jt.setWrapStyleWord(true);
		add(jsp,BorderLayout.CENTER);
		
		int x=(int)(width*0.1);
		int y=(int)(height*0.1);
		int w=(int)(width*0.8);
		int h=(int)(height*0.8);
		System.out.println(x+":"+y+":"+w+":"+h);
		setBounds(x,y,w,h);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		EmailTesterUI emailTest=new EmailTesterUI();
		String urlStr="http://144.217.91.32:8080/FPWebService/fpservices/AlarmEvents.json/notfnPendingEventlist";
		//String data=NetUtil.getResponseFromService(urlStr);
		List<AlarmNotfnEventDetailsDTO> events=WSCommUtil.getNotificationUnsentEventList();
		String data=(events!=null && events.size()>0)?events.get(0).getBuildingName():"";
		//data=new String(data.getBytes(),"UTF-8");
		
		emailTest.jt.setText(data);
		EmailCommunicator.sendEmail(data, "Email Sending Test New", "csuresh.tvm@gmail.com");
		
		EmailCommunicator.sendEmail(data,"Email Check","csuresh.tvm@gmail.com");
		
		
	}
}
