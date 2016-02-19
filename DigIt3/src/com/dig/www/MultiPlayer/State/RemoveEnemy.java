package com.dig.www.MultiPlayer.State;

public class RemoveEnemy extends ActionState
{
private int i;
	public RemoveEnemy(int i) {
		super(ActionType.REMOVEENN);
		this.i=i;
	}
public int getI(){
	return i;
}
}
