package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

public class BlockUI extends JDialog implements Runnable{

	
	public static boolean showBlockUI=false;
	JLabel jl;
	
	public BlockUI(AlarmMonitorMainUI mainUI){
		super(mainUI);
		//Image image=GenerateImage.toImage(true);  //this generates an image file
		
		try {
		//	Image img = ImageIO.read(getClass().getResource("/resources/blockUI.gif"));
		
		URL url = BlockUI.class.getResource("/resources/blockUI.gif");

		ImageIcon icon = new ImageIcon(url); 
		jl = new JLabel();
		jl.setIcon(icon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//
		
		setLayout(new BorderLayout());
		getContentPane().add(jl,BorderLayout.CENTER);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		int screenWith=AlarmMonitorMainUI.width;
		int screenHeight=AlarmMonitorMainUI.height;
		int imageWidth=200;
  	  	int imageHeight=250;
  	  
		 setBounds((screenWith-imageWidth)/2,(screenHeight-imageHeight)/2,imageWidth,imageHeight);
		 
		
		//setBounds(200,200,200,300);
		setVisible(false);
		setResizable(false);
		new Thread(this).start();
		//set
		
	}
	
	public void run(){
		boolean previousState=false;
		
		while(true){
			//System.out.println(showBlockUI);
			if(!previousState && showBlockUI){
				showBlockUI();
				
			}else if(previousState && !showBlockUI){
				showUnblockUI();
			}
			previousState=showBlockUI;
			
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void showBlockUI(){
		
		System.out.println("Inside show BlockUI");
		
		setVisible(true);
		
	}
	
	public void showUnblockUI(){
		setVisible(false);
	}
	
	public static void main(String[] args) throws InterruptedException {
		BlockUI ui=null;//new BlockUI();
		ui.showBlockUI=true;
		Thread.sleep(10000);
		ui.showBlockUI=false;
		
	}
}
