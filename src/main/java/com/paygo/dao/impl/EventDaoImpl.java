package com.paygo.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paygo.dao.EventDao;
import com.paygo.entity.Event;

@Repository("eventDao")
@Transactional
public class EventDaoImpl implements EventDao {

	private static Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;		
	
	
	public void addEvent(Event event) {
		logger.trace("Adding event...");
		sessionFactory.getCurrentSession().saveOrUpdate(event);

	}

	@SuppressWarnings("unchecked")
	public List<Event> getEvents() {
        logger.trace("Fetching events...");
		return sessionFactory.getCurrentSession().createCriteria(Event.class).list();
	}

}
