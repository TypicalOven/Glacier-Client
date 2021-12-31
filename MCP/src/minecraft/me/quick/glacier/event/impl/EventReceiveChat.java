package me.quick.glacier.event.impl;

import me.quick.glacier.event.Event;

public class EventReceiveChat extends Event {
	
	public String message;
	
	public EventReceiveChat(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
