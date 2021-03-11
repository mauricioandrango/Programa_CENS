package com.cens.backend.censbackend.utils;

import java.io.Serializable;

public class NotificationRequest implements Serializable{

	private static final long serialVersionUID = -3404639686647732006L;
	String to;
	Notification notification;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
}
