package com.vision.amclient.conn;
/*******************************************************************************
 * Copyright (c) 2014 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/

import java.util.Date;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.vision.amclient.monitor.AlarmMonitor;
import com.vision.amclient.util.StaticMessages;


public class GPIOCommunicator {

  private static final String LIGHTS_ON_MSG = "The lights are off";
  private static final String LIGHTS_OFF_MSG = "The lights are on";
  private static final  Pin[] PIN_IDS={RaspiPin.GPIO_01,RaspiPin.GPIO_02,RaspiPin.GPIO_03,RaspiPin.GPIO_04,RaspiPin.GPIO_05,RaspiPin.GPIO_06,RaspiPin.GPIO_07,RaspiPin.GPIO_08,RaspiPin.GPIO_09,RaspiPin.GPIO_10};
 
  static LightStatus currentStatus;
  GpioPinDigitalOutput output;
  GpioPinDigitalInput input;
  //GpioPinDigitalOutput socketIndicatorOutput;
  boolean deviceResetToHigh=true;
  long outputUpdatedToHighTime=0;
  
  
  enum LightStatus {
    ON(LIGHTS_OFF_MSG), OFF(LIGHTS_ON_MSG);
    String status;

    private LightStatus(String status) {
      this.status = status;
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println("Starting the photosensor demo...");
  
    
  }
  public GPIOCommunicator(){
	 
  }
  public void initialize() throws InterruptedException{
	  
	  System.out.println("Going to initialize GPIO");
	  final GpioController gpio = GpioFactory.getInstance();
	  int inputPIN= Integer.parseInt(com.vision.amclient.util.PropertyUtil.getInstance().getConfigProperty("fpclient.inputpin_no"));
	  int outputPIN= Integer.parseInt(com.vision.amclient.util.PropertyUtil.getInstance().getConfigProperty("fpclient.outputpin_no"));
	 // int socketIndicatorOutputPIN= Integer.parseInt(com.vision.amclient.util.PropertyUtil.getInstance().getConfigProperty("socket_indicator_output_pin"));
		 
	  output = gpio.provisionDigitalOutputPin(getRaspiPin(outputPIN));
	    //socketIndicatorOutput= gpio.provisionDigitalOutputPin(getRaspiPin(socketIndicatorOutputPIN));
	    input = gpio.provisionDigitalInputPin(getRaspiPin(inputPIN), PinPullResistance.PULL_DOWN);
	    
	    configureSenssor(input);
	    System.out.println("GPIO Input PIN Configured at PIN "+inputPIN);
	   // sleep();
  }
  
  private Pin  getRaspiPin(int pinNo){	  
	  return PIN_IDS[pinNo-1];	
  }
  

  private static void configureSenssor(final GpioPinDigitalInput sensor) {
	  
    sensor.addListener(new GpioPinListenerDigital() {
      //@Override
      public void handleGpioPinDigitalStateChangeEvent(final GpioPinDigitalStateChangeEvent event) {
        handleSensorInput(event);
      }

      private void handleSensorInput(final GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isLow()) {
          notifyLowInput();
        } else {
          notifyHighInput();
        }
      }

      private void notifyHighInput() {
        //led.low();
       // updateStatus(LightStatus.ON);
    	  System.out.println("High Input Recieved");
    	  //AlarmMonitor.getInstance().addLocalFPMessage("Low\n");
    	  String message="FIRE ALARM";
				AlarmMonitor
						.getInstance()
						.addAlarmMessage(
								message,
								message.toLowerCase().contains("fire") ? StaticMessages.ALARM_TYPE_FIRE
										: StaticMessages.ALARM_TYPE_NORMAL);
   }

      private void notifyLowInput() {
        //led.high();
        //updateStatus(LightStatus.OFF);
    	  System.out.println("Low Input Recieved");
    	  //AlarmMonitor.getInstance().addLocalFPMessage("High\n");
    	  String message="NORMAL ALARM";
				AlarmMonitor
						.getInstance()
						.addAlarmMessage(
								message,
								message.toLowerCase().contains("fire") ? StaticMessages.ALARM_TYPE_NORMAL
										: StaticMessages.ALARM_TYPE_NORMAL);

      }
    });
  }

  private static void sleep() throws InterruptedException {
    for (;;) {
      Thread.sleep(500);
    }
  }



  private static void updateStatus(LightStatus newStatus) {
    if (currentStatus != newStatus) {
      try {
        currentStatus = newStatus;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static LightStatus getCurrentStatus() {
   
    String text ="";//homeTimeline.get(0).getText();
    if (text.contains("on")) {
      return LightStatus.ON;
    }
    return LightStatus.OFF;
  }

 
  public void updateLowInput() {
	  AlarmMonitor.getInstance().addAlarmMessage("Normal Alarm",StaticMessages.ALARM_TYPE_NORMAL);
		
     // output.low();
     // updateStatus(LightStatus.ON);
  	 // SocketMonitor.getInstance().addLocalFPMessage("Low\n");
    }

    public void updateHighInput() {
    	AlarmMonitor.getInstance().addAlarmMessage("Fire Alarm",StaticMessages.ALARM_TYPE_FIRE);
     // output.high();
    // updateStatus(LightStatus.OFF);
  	 // SocketMonitor.getInstance().addLocalFPMessage("High\n");
    }
    
    public void notifySocketConnectionChanged(boolean connectionStatus) {
        /*if(connectionStatus){
        	socketIndicatorOutput.high();
        }else{
        	socketIndicatorOutput.low();
            
        }*/
      }
    
    
    public void updateDeviceResetRequest(){
    	System.out.println("Inside updateDeviceResetRequest()");
    	deviceResetToHigh=true;
    	outputUpdatedToHighTime=System.currentTimeMillis();
    	System.out.println("Going to change the Output SIgnal to high ");
    	output.high();
    }
    
    
    public void checkAndUpdateOutput(){
    	
    	if((System.currentTimeMillis()-outputUpdatedToHighTime)>15000 && deviceResetToHigh){
    		System.out.println("Going to change the Output SIgnal to low ");
    		deviceResetToHigh=false;
    		output.low();
    		
    	}
    }
    

}

