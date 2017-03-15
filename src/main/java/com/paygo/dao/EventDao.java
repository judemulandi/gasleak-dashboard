package com.paygo.dao;

import java.util.List;

import com.paygo.entity.Event;

public interface EventDao {
	public void addEvent(Event event);
	public List<Event> getEvents();
}
