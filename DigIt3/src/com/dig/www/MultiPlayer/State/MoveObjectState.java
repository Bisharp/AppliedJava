package com.dig.www.MultiPlayer.State;

public class MoveObjectState extends ActionState{
protected int x;
protected int y;
protected int i;
	public MoveObjectState(int x,int y,int i) {
		super(ActionType.MOVE);
		this.x=x;
		this.y=y;
		this.i=i;
		// TODO Auto-generated constructor stub
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getI() {
		return i;
	}

}
