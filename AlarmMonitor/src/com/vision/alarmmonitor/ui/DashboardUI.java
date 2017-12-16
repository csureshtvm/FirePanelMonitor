package com.vision.alarmmonitor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.bar.BarChart01;
import org.knowm.xchart.demo.charts.bar.BarChart02;
import org.knowm.xchart.demo.charts.bar.BarChart03;
import org.knowm.xchart.demo.charts.bar.BarChart04;
import org.knowm.xchart.demo.charts.bar.BarChart05;
import org.knowm.xchart.demo.charts.bar.BarChart06;
import org.knowm.xchart.demo.charts.bar.BarChart07;
import org.knowm.xchart.demo.charts.bar.BarChart08;
import org.knowm.xchart.demo.charts.bar.BarChart09;
import org.knowm.xchart.style.Styler.ChartTheme;

import com.vision.alarmmonitor.dto.AlarmEventStatisticsDTO;
import com.vision.alarmmonitor.dto.AlarmPoint;
import com.vision.alarmmonitor.dto.CustomerStatisticsDTO;
import com.vision.alarmmonitor.util.AlarmUtil;
import com.vision.alarmmonitor.util.AppConstants;
import com.vision.alarmmonitor.util.WSCommUtil;

public class DashboardUI extends JPanel{
	
	
	
	JPanel pieChart;
	//AlarmStatisticsBarChartUI barChart;
	JPanel barChart;
	JPanel jp;
	JLabel pieChartTitleLbl;
	public DashboardUI(){
		if(AppConstants.BG_COLOR_SHOW){
			setBackground(AppConstants.SCREEN_BG_COLOR); 
		}
		//jp=new JPanel();
		setLayout(null);
		pieChartTitleLbl=new JLabel("Customer/Building Statstics");
		//add(pieChartTitleLbl);
		
		Font titleFont = new Font("Arial", Font.BOLD, 18);
		pieChartTitleLbl.setFont(titleFont);
		initializeCharts();
		//add(jp);
		
		
	}
	private void initializeCharts(){
		/* ArrayList<Double> values=new ArrayList<Double>();
		 values.add((double)30);
		 values.add((double)10);
		 values.add((double)60);
	     ArrayList<Color> colors=new ArrayList<Color>();
	     colors.add(Color.blue);
	     colors.add(Color.red);
	     colors.add(Color.green);
		pieChart=new CustomerStatisticsPieChartUI(values,colors);*/
		// Create Chart
		
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		AlarmPoint topLeftP1=AlarmUtil.getWidowScreenPoint(width,height,10,20);
		AlarmPoint bottomRightP1=AlarmUtil.getWidowScreenPoint(width,height,45,70);
		
	    PieChart chart = new PieChartBuilder().width((bottomRightP1.getX()-topLeftP1.getX())).height((bottomRightP1.getY()-topLeftP1.getY())).title("Customer/Building Statstics").build();
	      
	    // Customize Chart
	    Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
	    chart.getStyler().setSeriesColors(sliceColors);
	    chart.getStyler().setChartBackgroundColor(AppConstants.SCREEN_BG_COLOR);
	    // Series
	    
	   List<CustomerStatisticsDTO> custStatsList=WSCommUtil.populateCustomerAnalytics();
	   
	   if(custStatsList!=null && custStatsList.size()>0){
		   for(CustomerStatisticsDTO custStat : custStatsList){
		   chart.addSeries(custStat.getCustomerName()+" - "+custStat.getNoOfBuildings(), custStat.getNoOfBuildings());
		   }
	   }
	    
	  /*  chart.addSeries("Gold", 24);
	    chart.addSeries("Silver", 21);
	    chart.addSeries("Platinum", 39);
	    chart.addSeries("Copper", 17);
	    chart.addSeries("Zinc", 40);*/
		
	    pieChart= new XChartPanel(chart);
		
		
	    Color[] colors = new Color[] { Color.blue,Color.red,Color.green,Color.cyan,Color.gray,Color.orange,Color.yellow };
		
	   
	    add(pieChart);
		pieChart.setBounds(topLeftP1.getX(), topLeftP1.getY(),(bottomRightP1.getX()-topLeftP1.getX()) , (bottomRightP1.getY()-topLeftP1.getY()));
		pieChart.setBackground(Color.WHITE);
		pieChartTitleLbl.setBounds(topLeftP1.getX()+30, topLeftP1.getY()-70,400,40);
		addOrUpdateBarChart();
		
	}
	
	
	private void addOrUpdateBarChart(){
		
		try{
		int width=AlarmMonitorMainUI.width;
		int height=AlarmMonitorMainUI.height;
		Axis axis=new Axis(10,50,500,"No Of Alarms");
			//barChart=new AlarmStatisticsBarChartUI(barChartValues,axis);
		/*String[] seriesLabels=new String[]{"August","September","October"};
		String[] yLabels=new String[]{"bulding1","Building2","Building3","Building4"};
		Integer[][] vals=new Integer[][]{{10,20,11,22},{30,10,5,18},{7,15,22,18}};*/
		
		
		 Map<String,Object> eventStatsDTOList=WSCommUtil.populateEventAnalyticsNew();
		 String[] custNameLabels=(String[])eventStatsDTOList.get("custNames");
		 String[] monthLabels=(String[])eventStatsDTOList.get("months");
		 Integer[][] vals=(Integer[][])eventStatsDTOList.get("vals");
		 System.out.println(vals.length+":"+vals[0].length+":"+custNameLabels.length+":"+monthLabels.length);
		
		
		if(barChart!=null){
		barChart.removeAll();
		barChart.repaint();
		barChart=null;
		}
		barChart  = getBarChart(600,400,0,60,monthLabels,custNameLabels,vals);
		add(barChart);
		AlarmPoint topLeftP2=AlarmUtil.getWidowScreenPoint(width,height,55,20);
		AlarmPoint bottomRightP2=AlarmUtil.getWidowScreenPoint(width,height,90,70);
		//barChart.width=(bottomRightP2.getX()-topLeftP2.getX());
		//barChart.height=(bottomRightP2.getY()-topLeftP2.getY());
		barChart.setBounds(topLeftP2.getX(), topLeftP2.getY(),(bottomRightP2.getX()-topLeftP2.getX())+25 , (bottomRightP2.getY()-topLeftP2.getY())+25);
		barChart.setBackground(Color.WHITE);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String[] seriesLabels=new String[]{"August","September","October"};
		String[] yLabels=new String[]{"bulding1 fsaafsfsafsafsafs","Building2 dsassasaf","Building3","Building4"};
		Integer[][] vals=new Integer[][]{{10,20,11,22},{30,10,5,18},{7,15,22,18}};
		JPanel barchart  = getBarChart(600,400,0,60,seriesLabels,yLabels,vals);
			  
			  //new XChartPanel(chart);
		  
		  JFrame frame=new JFrame();
		  frame.add(barchart,BorderLayout.CENTER);
		  frame.setBounds(0, 0, 500, 500);
		  frame.setVisible(true);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private static JPanel getBarChart(int width,int height,int yAxisMin,int yAxisMax,String[] seriesLabels, String[] yLabels, Integer[][] vals){
		JPanel barchart  =null;
		try{
		 CategoryChart chart = new CategoryChartBuilder().width(width).height(height).theme(ChartTheme.Matlab).title("Customer vs. Alarms").xAxisTitle("Customer").yAxisTitle("Alarm Count").build();
		 chart.getStyler().setYAxisMin(yAxisMin);
		 chart.getStyler().setYAxisMax(50);
		 chart.getStyler().setHasAnnotations(true);
		 
		 for(int i=0;i<seriesLabels.length;i++){
			 System.out.println("Y Labels ===============>"+Arrays.asList(yLabels));
			 chart.addSeries(seriesLabels[i], Arrays.asList(yLabels), Arrays.asList(vals[i]));
		}
		 barchart  = new XChartPanel(chart);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return barchart;
	}

}
