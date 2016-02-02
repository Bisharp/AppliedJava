package com.dig.www.MultiPlayer.State;

public class ObjectPickUpState extends ActionState{
protected int i;
	public ObjectPickUpState(int i) {
		super(ActionType.PICKUP);
		this.i=i;
		// TODO Auto-generated constructor stub
	}
public int getI(){
	return i;
}
}
