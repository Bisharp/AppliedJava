package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

public class ObjectState extends SpriteState implements Serializable{
	private ObjectsTypes type;
	private String loc;
	private boolean wall;
	private String ident;
	private int i;
public enum ObjectsTypes{
	NORMAL,MONEY,CUBE
}
	public ObjectState(int x, int y,ObjectsTypes type,String loc,boolean wall,String ident,int i) {
		super(x, y);
	this.type=type;
	this.loc=loc;
	this.wall=wall;
	this.ident=ident;
	this.i=i;
	}
	public ObjectsTypes getType() {
		return type;
	}
	public String getLoc() {
		return loc;
	}
	public boolean isWall() {
		return wall;
	}
	public String getIdent() {
		return ident;
	}
public int getI(){
	return i;
}
}
