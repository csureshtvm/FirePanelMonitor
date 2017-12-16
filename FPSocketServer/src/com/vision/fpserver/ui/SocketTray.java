/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.vision.fpserver.ui;

/*
 * TrayIconDemo.java
 */

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.*;

import com.vision.fpserver.monitor.FirePanelMonitor;
import com.vision.fpserver.util.StaticMessages;

public class SocketTray {

	TrayIcon trayIcon = null;
	static Logger log = Logger.getLogger("AlarmMonitorTray");
	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = (MenuItem) e.getSource();
			// TrayIcon.MessageType type = null;
			System.out.println(item.getLabel());
			if ("System Messages".equals(item.getLabel())) {
				//FirePanelMonitor.getInstance().systemMessagesUI.setVisible(true);
			}else if ("Application Logs".equals(item.getLabel())) {
				//FirePanelMonitor.getInstance().logUI.setVisible(true);
			}


		}
	};

	public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
			log.info("ERROR while initializing LookAndFeel!!!!!!!!! "
					+ ex.toString());
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			log.info("ERROR while initializing LookAndFeel!!!!!!!!! "
					+ ex.toString());
		} catch (InstantiationException ex) {
			ex.printStackTrace();
			log.info("ERROR while initializing LookAndFeel!!!!!!!!! "
					+ ex.toString());
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			log.info("ERROR while initializing LookAndFeel!!!!!!!!! "
					+ ex.toString());
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		// Schedule a job for the event-dispatching thread:
		// adding TrayIcon.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// createAndShowGUI();
			}
		});
	}

	public SocketTray() {
		// Check the SystemTray support
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		trayIcon = new TrayIcon(createImage("resources/tray.gif", "tray icon"));
		final SystemTray tray = SystemTray.getSystemTray();
		
		// Create a popup menu components
		MenuItem logItem = new MenuItem("System Messages");
		MenuItem systemLogsItem = new MenuItem("Application Logs");

		MenuItem exitItem = new MenuItem("Exit");
		MenuItem aboutItem = new MenuItem("About");

		// Add components to popup menu
		popup.addSeparator();
		popup.add(systemLogsItem);
		popup.addSeparator();
		popup.add(logItem);

		popup.addSeparator();
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(exitItem);

		logItem.addActionListener(listener);
		systemLogsItem.addActionListener(listener);
		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);

		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}

		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Fire Panel Monitor");
			}
		});

		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Fire Panel Monitor V1.0");
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FirePanelMonitor.getInstance().stopMonitor();
				System.out.println("Going to close monitor");
				try {
					FirePanelMonitor.getInstance().join(10000);
					System.out.println("Monitor closed");
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tray.remove(trayIcon);
				System.exit(0);
			}
		});
	}

	// Obtain the image URL
	protected static Image createImage(String path, String description) {
		URL imageURL = ClassLoader.getSystemResource("resources/tray.gif");

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

	public void showTrayMessage(String message, int type) {
		if (type != StaticMessages.ERROR) {
			trayIcon
					.displayMessage("Staus", message, TrayIcon.MessageType.INFO);
		} else {
			trayIcon.displayMessage("Staus", message,
					TrayIcon.MessageType.ERROR);
		}
	}
}
