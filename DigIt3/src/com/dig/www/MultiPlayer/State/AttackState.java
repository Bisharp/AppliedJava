package com.dig.www.MultiPlayer.State;

import com.dig.www.character.Moves;

public class AttackState extends ActionState{
	protected String charName;
	protected int attackNum;
public AttackState(int move,String charName){
	super(ActionType.ATTACK);
	this.attackNum=move;
	this.charName=charName;
}
public String getCharName() {
	return charName;
}
public int getAttackNum(){
	return attackNum;
}
}
