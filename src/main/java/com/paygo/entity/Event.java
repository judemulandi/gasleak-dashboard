package com.paygo.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")
public class Event implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "eventid", unique = true, nullable = false)
	private int eventid;

	@Column(name = "offset", nullable = false)
	private String offset;

	@Column(name = "seqno", nullable = false)
	private Long seqno;

	@Column(name = "enqueuetime", nullable = false)
	private Date enqueuetime;

	@Column(name = "deviceid", nullable = false)
	private String deviceid;

	@Column(name = "payload", nullable = false)
	private String payload;
	
	

	public int getEventid() {
		return eventid;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public Long getSeqno() {
		return seqno;
	}

	public void setSeqno(Long seqno) {
		this.seqno = seqno;
	}

	public Date getEnqueuetime() {
		return enqueuetime;
	}

	public void setEnqueuetime(Date enqueuetime) {
		this.enqueuetime = enqueuetime;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}
