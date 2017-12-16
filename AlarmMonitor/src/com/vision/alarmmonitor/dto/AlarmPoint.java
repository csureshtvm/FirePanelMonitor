package com.vision.alarmmonitor.dto;

public class AlarmPoint {
	
	private int x;
	private int y;
	
	public AlarmPoint(int x,int y){
		this.x=x;
		this.y=y;
	}
	public AlarmPoint(){}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	

}
