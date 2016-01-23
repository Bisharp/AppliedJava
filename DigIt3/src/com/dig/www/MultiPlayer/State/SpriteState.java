package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

public class SpriteState implements Serializable{
	protected int x;
	protected int y;
public SpriteState(int x,int y){
	this.x=x;
	this.y=y;
}
public int getX(){
	return x;
}
public int getY(){
	return y;
}
}
