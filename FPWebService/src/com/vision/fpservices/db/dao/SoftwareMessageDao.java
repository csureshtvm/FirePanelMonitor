package com.vision.fpservices.db.dao;

import java.util.List;

import com.vision.fpservices.db.model.AlarmEvents;
import com.vision.fpservices.db.model.SoftwareMessage;
import com.vision.fpservices.dto.OtherContactsDTO;
import com.vision.fpservices.dto.SoftwareMessageDTO;
import com.vision.fpservices.dto.SoftwareMessagePendingDTO;

public interface SoftwareMessageDao extends GenericDao<SoftwareMessage, Integer>{

	public List<SoftwareMessage> getAllSoftwareMessagesFromBuilding(Integer buildingId);
	public List<SoftwareMessageDTO> getAllSoftwareMessages(Integer customerId,Integer buildingId);

	public List<SoftwareMessagePendingDTO> getAllNotfnPendingSoftwareMessages();
	public SoftwareMessage loadAlarmDevice(Integer softwareMessageId);
	public List<OtherContactsDTO> getAllAlarmOtherEventsContacts();
}
