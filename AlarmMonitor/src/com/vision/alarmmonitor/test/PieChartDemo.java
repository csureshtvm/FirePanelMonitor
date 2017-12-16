package com.vision.alarmmonitor.test;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.pie.PieChart02;
public class PieChartDemo extends JComponent {
	

	
	   Slice[] slices = { new Slice(5, Color.black), 
	   new Slice(33, Color.green),
	   new Slice(20, Color.yellow), new Slice(15, Color.red) };
	   PieChartDemo() {}
	   public void paint(Graphics g) {
	      drawPie((Graphics2D) g, getBounds(), slices);
	   }
	   void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
	      double total = 0.0D;
	      for (int i = 0; i < slices.length; i++) {
	         total += slices[i].value;
	      }
	      double curValue = 0.0D;
	      int startAngle = 0;
	      for (int i = 0; i < slices.length; i++) {
	         startAngle = (int) (curValue * 360 / total);
	         int arcAngle = (int) (slices[i].value * 360 / total);
	         g.setColor(slices[i].color);
	         g.fillArc(area.x, area.y, area.width, area.height, 
	         startAngle, arcAngle);
	         curValue += slices[i].value;
	      }
	   }
	   public static void main(String[] args)  {

		   // Create Chart
		    PieChart chart = new PieChartBuilder().width(800).height(600).title("Test").build();

		    
		    
		    // Customize Chart
		    Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
		    chart.getStyler().setSeriesColors(sliceColors);

		    // Series
		    chart.addSeries("Gold", 24);
		    chart.addSeries("Silver", 21);
		    chart.addSeries("Platinum", 39);
		    chart.addSeries("Copper", 17);
		    chart.addSeries("Zinc", 40);
		    JPanel jf1 = new XChartPanel(chart);
		   // JPanel jf1=new SwingWrapper<PieChart>(chart).getXChartPanel();
		    
		    JFrame jf=new JFrame();
		   //jf.setContentPane(jf1.getContentPane());
		    jf.add(jf1);
			jf.getContentPane().setLayout(null);
			jf.setBounds(0, 0, 600, 400);
			jf.setVisible(true);
			jf1.setBounds(100, 100, 800, 600);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   }  
}

	class Slice {
	   double value;
	   Color color;
	   public Slice(double value, Color color) {  
	      this.value = value;
	      this.color = color;
	   }
	}

