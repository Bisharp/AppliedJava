package com.dig.www.MultiPlayer.State;

import com.dig.www.character.Moves;

public class AttackState extends ActionState{
	protected String charName;
	protected int attackNum;
	protected int dir;
public AttackState(int move,String charName,int dir){
	super(ActionType.ATTACK);
	this.attackNum=move;
	this.charName=charName;
	this.dir=dir;
}
public int getDir(){
	return dir;
}
public String getCharName() {
	return charName;
}
public int getAttackNum(){
	return attackNum;
}
}
