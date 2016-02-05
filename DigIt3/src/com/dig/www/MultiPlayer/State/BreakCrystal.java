package com.dig.www.MultiPlayer.State;

public class BreakCrystal extends ActionState{
protected int i;
	public BreakCrystal(int i) {
		
		super(ActionState.ActionType.BREAK);
	this.i=i;
	}
public int getI(){
	return i;
}
}
