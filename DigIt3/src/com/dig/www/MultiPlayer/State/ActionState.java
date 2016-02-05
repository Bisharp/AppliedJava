package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;

public abstract class ActionState implements Serializable{
	protected ActionType actionType;
	protected Moves move;
	public enum ActionType{
		SWITCH, MONEY,PICKUP,MOVE,BREAK,DIG,ATTACK,ADDEN
	}
public ActionState(ActionType actionType){
	this.actionType=actionType;
}
public ActionState(Moves move){
	actionType=ActionType.ATTACK;
	this.move=move;
}
public ActionType getActionType(){
	return actionType;
}
public Moves getMove(){
	return move;
	
}
}
