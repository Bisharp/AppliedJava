package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

public class SwitchState extends ActionState implements Serializable{
protected String from;
protected String to;
	public SwitchState(String from,String to) {
		super(ActionType.SWITCH);
		// TODO Auto-generated constructor stub
		this.from=from;
		this.to=to;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}

}
