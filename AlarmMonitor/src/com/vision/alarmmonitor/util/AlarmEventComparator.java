package com.vision.alarmmonitor.util;

import java.util.Comparator;
import java.util.Map;

import com.vision.alarmmonitor.model.AlarmEventGraphicalDetals;

public class AlarmEventComparator implements Comparator<String>{

	Map<String,AlarmEventGraphicalDetals> alarmMap;
	
	public AlarmEventComparator(Map<String,AlarmEventGraphicalDetals> alarmMap){
		this.alarmMap=alarmMap;
	}
	 public int compare(String a, String b) {
	        if (alarmMap.get(a).getValue() >= alarmMap.get(b).getValue()) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
}
