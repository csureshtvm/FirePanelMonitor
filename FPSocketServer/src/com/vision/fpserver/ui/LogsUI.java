package com.vision.fpserver.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import com.vision.fpserver.model.Message;
import com.vision.fpserver.monitor.FirePanelMonitor;
import com.vision.fpserver.util.StaticMessages;

public class LogsUI extends JFrame implements ActionListener,
		WindowStateListener {

	JScrollPane jsp;
	JTextArea jta;
	JButton jb;
	Logger log = Logger.getLogger("ControllerLogsUI");
	JTextPane textPane = new JTextPane();
	private static Highlighter.HighlightPainter cyanPainter = new DefaultHighlighter.DefaultHighlightPainter(
			Color.cyan);
	private static Highlighter.HighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(
			Color.red);

	public LogsUI() {
		super("Fire Panel Server - Application Logs");
		jta = new JTextArea();
		jta.setEditable(false);
		jb = new JButton("Clear");
		jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setLayout(null);
		// setBackground(StaticUtils.BACKGROUND_COLOR);
		jsp.setBounds(10, 10, 550, 400);
		jb.setBounds(250, 420, 100, 20);
		add(jsp);
		add(jb);

		jb.addActionListener(this);
		
		
		
		
		
		
		//setBounds(100, 100, 600, 500);
	}

	public LogsUI(boolean colorText) {
		jb = new JButton("Clear");
		jsp = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setLayout(null);
		// setBackground(StaticUtils.BACKGROUND_COLOR);
		jsp.setBounds(10, 10, 550, 400);
		jb.setBounds(250, 420, 100, 20);
		add(jsp);
		add(jb);
		jb.addActionListener(this);
		//setBounds(100, 100, 600, 500);
		
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		int height=(int)dim.getHeight();
		int width=(int)dim.getWidth();
		//getContentPane().setBackground(new Color(117,197,237));
		getContentPane().setBackground(new Color(1,161,145));
				
		System.out.println(width+":"+height+":"+(width/2)+":"+(height/2));
		setBounds((width/2)-300, (height/2)-250, 600, 500);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jb) {
			//textPane.removeAll();
			textPane.setText("");
			textPane.repaint();
			FirePanelMonitor.getInstance().logMessages.clear();
		}

	}

	public void refereshMessages(Vector<Message> messages) {

		/*
		 * remove(textPane); remove(jsp); textPane = new JTextPane(); jsp=new
		 * JScrollPane
		 * (textPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane
		 * .HORIZONTAL_SCROLLBAR_ALWAYS); jsp.setBounds(10,10,550,400);
		 * add(jsp);
		 */
		// System.out.println("Going to refresh message--->"+messages);
		String msg = "";
		//textPane.removeAll();
		Vector<StartEndLoc> startEndLocVect = new Vector<StartEndLoc>();
		for (int i = (messages.size() - 1); i >= 0; i--) {
			Message message = messages.get(i);
			int startLoc = msg.length();
			msg += message.getMessageDate() + "\t" + message.getMessage()
					+ "\n\n";
			// System.out.println(message.getMessageDate()+"\t"+message.getMessage());
			int endLoc = msg.length();

			if (message.getType() == StaticMessages.ERROR) {
				try {
					startEndLocVect.add(new StartEndLoc(startLoc, endLoc - 1,
							redPainter));
					// textPane.getHighlighter().addHighlight(startLoc,endLoc-1,
					// redPainter);
					// System.out.println(message.getMessage()+":"+message.getType()+":"+startLoc+":"+(endLoc-1));
				} catch (Exception ble) {
				}
			} else if (message.getType() == StaticMessages.INFO) {
				try {
					startEndLocVect.add(new StartEndLoc(startLoc, endLoc - 1,
							cyanPainter));
					// textPane.getHighlighter().addHighlight(startLoc,endLoc-1,
					// DefaultHighlighter.DefaultPainter);
					// System.out.println(message.getMessage()+":"+message.getType()+":"+startLoc+":"+(endLoc-1));
				} catch (Exception ble) {
				}
			} else {

				try {
					startEndLocVect.add(new StartEndLoc(startLoc, endLoc - 1,
							DefaultHighlighter.DefaultPainter));
					// textPane.getHighlighter().addHighlight(startLoc,endLoc-1,
					// DefaultHighlighter.DefaultPainter);
					// System.out.println(message.getMessage()+":"+message.getType()+":"+startLoc+":"+(endLoc-1));
				} catch (Exception ble) {
				}

			}
		}

		// jta.add(new JLabel(msg));
		textPane.setText(msg);

		for (int i = 0; i < startEndLocVect.size(); i++) {
			StartEndLoc loc = startEndLocVect.get(i);

			try {
				// textPane.getHighlighter().addHighlight(0, 3,
				// DefaultHighlighter.DefaultPainter);
				// textPane.getHighlighter().addHighlight(8, 14, cyanPainter);
				textPane.getHighlighter().addHighlight(loc.start, loc.end,
						loc.painter);
			} catch (BadLocationException ble) {
			}

		}

		// jta.setText(msg);
		// jta.repaint();
	}

	@Override
	public void windowStateChanged(WindowEvent e) {

		if (e.getNewState() == Frame.ICONIFIED) {
			setVisible(false);

		}

	}

	class StartEndLoc {
		int start;
		int end;
		HighlightPainter painter;

		StartEndLoc(int start, int end, HighlightPainter painter) {
			this.start = start;
			this.end = end;
			this.painter = painter;
		}
	}

}
