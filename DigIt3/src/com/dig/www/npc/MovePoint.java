package com.dig.www.npc;

public class MovePoint {
	protected int x;
	protected int y;
	protected boolean waitFirst;
public MovePoint(int x,int y, boolean waitFirst){
	this.x=x;
	this.y=y;
	this.waitFirst=waitFirst;
}
protected int getX(){
	return x;
}
protected int getY(){
	return y;
}
protected boolean getWaitFirst(){
	return waitFirst;
}
}
