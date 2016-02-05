package com.dig.www.MultiPlayer.State;

public class DigPit extends ActionState{
protected int i;
	public DigPit(int i) {
		
		super(ActionState.ActionType.DIG);
	this.i=i;
	}
public int getI(){
	return i;
}
}
