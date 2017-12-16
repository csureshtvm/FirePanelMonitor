package com.vision.alarmmonitor.util;

import com.vision.alarmmonitor.dto.AlarmPoint;

public class AlarmUtil {
	
	
	public static AlarmPoint getWidowScreenPoint(int width, int height,int xMargin,int yMargin){
		AlarmPoint point=new AlarmPoint();
		
		point.setX((int)((double)xMargin*(double)width/(double)100));
		point.setY((int)((double)yMargin*(double)height/(double)100));
		return point;
	}

}
