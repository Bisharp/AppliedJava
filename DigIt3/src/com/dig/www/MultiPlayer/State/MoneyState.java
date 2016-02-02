package com.dig.www.MultiPlayer.State;

public class MoneyState extends ActionState{
protected int val;
protected int i;
	public MoneyState(int val,int i) {
		
		super(ActionType.MONEY);
		this.val=val;
		this.i=i;
		// TODO Auto-generated constructor stub
	}
public int getVal(){
	return val;
}
public int getI(){
	return i;
}
}
