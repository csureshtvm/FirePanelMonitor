package com.vision.fpservices.db.dao;

import java.io.Serializable;
import java.util.List;

import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.AlarmNotfnHistory;
import com.vision.fpservices.dto.AlarmEventDTO;
import com.vision.fpservices.dto.AlarmEventStatisticsDTO;
import com.vision.fpservices.dto.AlarmEventWithContactsDTO;
import com.vision.fpservices.dto.AlarmNotfnEventDetailsDTO;

public interface AlarmEventDao extends GenericDao<AlarmEvents, Integer>{
	
	public List<AlarmEventDTO> getAllalarmEvents(String customerId);
	
	public AlarmEvents loadAlarmEvent(Integer alarmEventId);
	public int saveOrUpdate(AlarmEvents alarmEvent);
	public List<AlarmNotfnEventDetailsDTO> getNotfnpendingEventList();
	public int saveOrUpdate(AlarmNotfnHistory alarmotfnHistory);
	public List<AlarmEventStatisticsDTO> getAlarmEventStatistics();
	public List<AlarmEventStatisticsDTO> getAlarmEventStatistics1();
	public List<AlarmEventDTO> getAllalarmEvents();
	public List<AlarmEventWithContactsDTO> getAllalarmEventsWithContactDetails(String customerId);
}
