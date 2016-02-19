package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;

public abstract class ActionState implements Serializable{
	protected ActionType actionType;
	public enum ActionType{
		SWITCH, MONEY,PICKUP,MOVE,BREAK,DIG,ATTACK,ADDEN,REMOVEENN
	}
public ActionState(ActionType actionType){
	this.actionType=actionType;
}
public ActionType getActionType(){
	return actionType;
}
}
