package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

public abstract class ActionState implements Serializable{
	protected ActionType actionType;
	public enum ActionType{
		SWITCH
	}
public ActionState(ActionType actionType){
	this.actionType=actionType;
}
public ActionType getActionType(){
	return actionType;
}
}
