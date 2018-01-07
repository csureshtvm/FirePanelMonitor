package com.vision.fpservices.db.dao;

import java.util.List;

import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.AlarmMessages;
import com.vision.fpservices.dto.AlarmMessagesDTO;

public interface AlarmMessagesDAO extends GenericDao<AlarmMessages, Integer>{

	public AlarmMessages loadAlarmMessages(Integer alarmMessageId);
	public List<AlarmMessagesDTO> getAllSoftwareMessages(Integer customerId,Integer buildingId);
}
