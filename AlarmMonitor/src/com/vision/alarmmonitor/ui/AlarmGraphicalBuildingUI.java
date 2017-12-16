package com.vision.alarmmonitor.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.test.BuildingDemo;

public class AlarmGraphicalBuildingUI extends JPanel {
	private int width=200,height=400;
	private int shiftX=50,shiftY=50;
	AlarmPoint[] polyPts1=null;
	AlarmPoint[] polyPts2=null;
	AlarmPoint[] polyPts3=null;
	AlarmPoint[] polyPts4=null;
	
	JButton jb=new JButton("Repaint");
	

	private int buildingBlockStartXLeft=20,buildingBlockStartYLeft=20;
	private int totalFloors=15,totalColumns=5;
	private List<Integer> fireAlarmFloorsList=new ArrayList<Integer>();
	int buildingBlockStartX=0,buildingBlockStartY=0;
	
	private String buildingName,buildingAddress;
	int startX1=100,startY1=50;	
	
	public AlarmGraphicalBuildingUI(){
		
	}
	//JLabel jl1,jl2;
	private void initializePoints(){
		
		
		//jl1=new JLabel("");
		//jl2=new JLabel("");
		
		int endX1=startX1+width,endY1=startY1+shiftY;
		
		AlarmPoint poly1Pt1=new AlarmPoint(startX1, startY1);
		AlarmPoint poly1Pt2=new AlarmPoint(startX1+shiftX, endY1);
		AlarmPoint poly1Pt3=new AlarmPoint(endX1+shiftX, endY1);
		AlarmPoint poly1Pt4=new AlarmPoint(endX1, startY1);
		
		polyPts1=new AlarmPoint[]{poly1Pt1,poly1Pt2,poly1Pt3,poly1Pt4};
		
		
		AlarmPoint poly2Pt1=new AlarmPoint(startX1, startY1);
		AlarmPoint poly2Pt2=new AlarmPoint(startX1, endY1+height);
		AlarmPoint poly2Pt3=new AlarmPoint(endX1, endY1+height);
		AlarmPoint poly2Pt4=new AlarmPoint(endX1, startY1);
		polyPts2=new AlarmPoint[]{poly2Pt1,poly2Pt2,poly2Pt3,poly2Pt4};
		
		AlarmPoint poly3Pt1=new AlarmPoint(startX1, endY1+height);
		AlarmPoint poly3Pt2=new AlarmPoint(startX1+shiftX, endY1+height+shiftY);
		AlarmPoint poly3Pt3=new AlarmPoint(endX1+shiftX, endY1+height+shiftY);
		AlarmPoint poly3Pt4=new AlarmPoint(endX1, endY1+height);
		polyPts3=new AlarmPoint[]{poly3Pt1,poly3Pt2,poly3Pt3,poly3Pt4};
		
		AlarmPoint poly4Pt1=new AlarmPoint(startX1+shiftX, endY1);
		AlarmPoint poly4Pt2=new AlarmPoint(startX1+shiftX, endY1+height+shiftY);
		AlarmPoint poly4Pt3=new AlarmPoint(endX1+shiftX, endY1+height+shiftY);
		AlarmPoint poly4Pt4=new AlarmPoint(endX1+shiftX, endY1);
		
		buildingBlockStartX=startX1+shiftX;
		buildingBlockStartY=endY1;
		
		polyPts4=new AlarmPoint[]{poly4Pt1,poly4Pt2,poly4Pt3,poly4Pt4};
		
		
		//add(jb);
		//add(jl1);
		//add(jl2);
		setLayout(null);
		jb.setBounds(350, 150, 100, 20);
		//jl1.setBounds(100, 10, 200, 20);
		//jl2.setBounds(100,30, 200, 20);
		//jb.addActionListener(this);
		
		
	}
	
	 @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        System.out.println("buildingName==============>"+buildingName);
	        if(buildingName==null){
	        	return;
	        }
	      /* if(jl1!=null && jl2!=null){
	        jl1.setText(buildingName);
	        jl2.setText(buildingAddress);
	        }*/
	        initializePoints();
	        g.setColor(Color.blue);
	        Font f=new Font("MS Gothic", Font.BOLD, 12);
	        g.setFont(f);
	        
	       
	        try {
				g.drawString(buildingName+","+buildingAddress, 100, 20);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //g.drawString(, 100, 40);
	        
	        Polygon poly2=new Polygon();
	        for(AlarmPoint pt: polyPts2){
	        	poly2.addPoint(pt.getX(), pt.getY());
	        }
	        g.setColor(new Color(168,113,57));
	        g.fillPolygon(poly2);
	        
	        
	        Polygon poly3=new Polygon();
	        for(AlarmPoint pt: polyPts3){
	        	poly3.addPoint(pt.getX(), pt.getY());
	        }
	        g.setColor(new Color(168,113,57));
	        g.fillPolygon(poly3);
	        
	        Polygon poly1=new Polygon();
	        for(AlarmPoint pt: polyPts1){
	        	poly1.addPoint(pt.getX(), pt.getY());
	        }
	        g.setColor(new Color(250,183,86));
	        g.fillPolygon(poly1);
	        
	        Polygon poly4=new Polygon();
	        for(AlarmPoint pt: polyPts4){
	        	poly4.addPoint(pt.getX(), pt.getY());
	        }
	        g.setColor(new Color(224,169,80));
	        g.fillPolygon(poly4);
	      
	        
	        //g.setColor(Color.cyan);
	        
	        int startFloorX=buildingBlockStartX+buildingBlockStartXLeft;
	        int startFloorY=buildingBlockStartY+buildingBlockStartYLeft;
	        int totalFloorWidth=width-(2*buildingBlockStartXLeft);
	        int totalFloorHeight=height+shiftY-(2*buildingBlockStartYLeft);
	        g.fillRect(buildingBlockStartX+buildingBlockStartXLeft, buildingBlockStartY+buildingBlockStartYLeft, width-(2*buildingBlockStartXLeft), height+shiftY-(2*buildingBlockStartYLeft));
	        
	        
	        
	        int floorWidth=totalFloorWidth/totalColumns;
	        int floorHeight=totalFloors;
	        if(totalFloors>0){
	        floorHeight=totalFloorHeight/totalFloors;
	        }
	        g.setColor(new Color(189,229,241));
	        int originalFloorX=startFloorX;
	        int originalFloorY=startFloorY;
	        
	        int distance=2;
	        for(int i=0;i<totalFloors;i++){
	        	for(int j=0;j<totalColumns;j++){
	        		
	        		g.fillRect(startFloorX+distance,startFloorY+distance,floorWidth-(distance*2),floorHeight-(distance*2));
	        		//g.drawLine();
	        		startFloorX+=(floorWidth);
	        	}
	        	startFloorX=originalFloorX;
	        	startFloorY+=(floorHeight);
	        }
	        startFloorY=originalFloorY;
	       
	        for(Integer floorNo : fireAlarmFloorsList){
	        	
	        	int startAlarmX=startFloorX+((floorWidth*totalColumns)/2)-10;
	        	int startAlarmY=startFloorY+((totalFloors-floorNo)*floorHeight);
	        	g.setColor(Color.red);
	        	g.fillOval(startAlarmX, startAlarmY, 20, 20);
	        	
	        }
	        
	        
	        
	    }

	
	  public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	
	            	
	                JFrame frame = new JFrame();
	                AlarmGraphicalBuildingUI bd=new AlarmGraphicalBuildingUI();
	                
	               Container c= frame.getContentPane();
	                c.setLayout(null);
	                c.add(bd);
	                bd.setBounds(0,0,400,600);
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.pack();
	                frame.setLocationRelativeTo(null);
	                frame.setBounds(100,50,400,600);
	               frame.setVisible(true);
	               try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            
				//bd.setBuildingParams(200, 400, 20, 20, 20, 20, 15, 5);
				//bd.setFireFloors(Arrays.asList(new Integer[]{3}));
				//bd.setBuildingDetals("building Name1", "Building Address1");
	            }
	        });
	    }

	
	public void setBuildingParams(int width,int height,int shiftX,int shiftY,int buildingBlockStartXLeft,int buildingBlockStartYLeft,int totalFloors,int totalColumns){
		this.width=width;
		this.height=height;
		this.shiftX=shiftX;
		this.shiftY=shiftY;
		this.buildingBlockStartXLeft=buildingBlockStartXLeft;
		this.buildingBlockStartYLeft=buildingBlockStartYLeft;
		this.totalFloors=totalFloors;
		this.totalColumns=totalColumns;
	}
	public void setFireFloors(List<Integer> fireAlarmFloors){
		fireAlarmFloorsList.clear();
		fireAlarmFloorsList.addAll(fireAlarmFloors);
	}
	public void setBuildingDetals(String buildingName, String buildingAddress){
		this.buildingName=buildingName;
		this.buildingAddress=buildingAddress;
	}
	
	
}
