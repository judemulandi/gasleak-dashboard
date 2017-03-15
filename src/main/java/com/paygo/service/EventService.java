package com.paygo.service;

import java.util.List;

import com.paygo.entity.Event;

public interface EventService {
	public void addEvent(Event event);
	public List<Event> getEvents();
}
