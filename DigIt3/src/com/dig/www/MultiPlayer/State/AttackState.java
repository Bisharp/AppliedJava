package com.dig.www.MultiPlayer.State;

import com.dig.www.character.Moves;

public class AttackState extends ActionState{
	protected int i;
	protected boolean fromP;
	protected String charName;
public AttackState(int i, Moves move,boolean fromP,String charName){
	super(ActionType.ATTACK);
	this.i=i;
	this.move=move;
	this.fromP=fromP;
	this.charName=charName;
}
public int getI() {
	return i;
}
public boolean isFromP() {
	return fromP;
}
public String getCharName() {
	return charName;
}
}
