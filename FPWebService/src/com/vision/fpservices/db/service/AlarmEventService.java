package com.vision.fpservices.db.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.dto.AlarmEventDTO;
import com.vision.fpservices.dto.AlarmEventRequestDTO;
import com.vision.fpservices.dto.AlarmEventStatisticsDTO;
import com.vision.fpservices.dto.AlarmEventWithContactsDTO;
import com.vision.fpservices.dto.AlarmMessagesDTO;
import com.vision.fpservices.dto.AlarmNotfnEventDetailsDTO;
import com.vision.fpservices.dto.AlarmStatisticsDTO;
import com.vision.fpservices.dto.SoftwareMessageDTO;
import com.vision.fpservices.dto.SoftwareMessagePendingDTO;

public interface AlarmEventService {
	
	public List<AlarmEventDTO> getAllAlarmEvents(String customerId,String userId);
	public boolean createNewAlarm(AlarmEventRequestDTO reqDto);
	
	public List<AlarmEventDTO> getAllAlarmEvents();
	public List<AlarmEventWithContactsDTO> getAllAlarmEventsWithContactDetails(String customerId,String userId);
	public boolean resetAlarm(Integer alarmEventId,String resetComments,String requestedBy);
	public boolean updateNotfnStatus(Integer alarmEventId,String notfnType,String notfnStatus);
	public List<AlarmNotfnEventDetailsDTO> getNotfnpendingEventList();
	public boolean saveAlarmNotfnHistory(Integer alarmEventId,Integer contactId,String notfnType);
	List<AlarmEventStatisticsDTO> getAlarmEventStatistics();
	public ArrayList<AlarmStatisticsDTO> getEventStatistics();

	public boolean saveSoftwareMessage(Integer buildingId,Integer customerId,
    		String deviceId,String isUpdate,
    	    long eventGeneratedTimeInMillis,String value, String updatedBy);

	public List<SoftwareMessageDTO> getAllSoftwareMessages(Integer customerId,Integer buildingId);

	public List<SoftwareMessagePendingDTO> getAllNotfnPendingSoftwareMessages();

	public boolean updateSwNotfnStatus(Integer alarmEventId,String notfnType,String notfnStatus);
	public boolean saveAlarmMessage(Integer buildingId,String deviceId, String messageDetails);
	public List<AlarmMessagesDTO> getAllAlarmMessages(Integer customerId,Integer buildingId);
	
}
