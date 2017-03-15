package com.paygo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paygo.dao.EventDao;
import com.paygo.entity.Event;
import com.paygo.service.EventService;


@Service("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	EventDao eventDao;
	
	public void addEvent(Event event) {
		eventDao.addEvent(event);
	}

	public List<Event> getEvents() {
		return eventDao.getEvents();
	}

}
