package com.cens.backend.censbackend.utils;

import java.io.Serializable;

public class Notification implements Serializable {

	private static final long serialVersionUID = -1922468601173366821L;
	String title;
	String body;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
