package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

import com.dig.www.character.GameCharacter.Direction;

public class PlayerState extends SpriteState implements Serializable{
protected int attackNum;
protected Direction dir;
protected String s;
protected boolean isPlayer;
protected String typeToString;
protected String mpName;
protected float health;
protected float energy;
protected int attackTimer;
protected int dire;
	public PlayerState(int x, int y,int attackNum,int attackTimer,Direction dir,String s,boolean isPlayer,String typeToString,String mpName,float health,float energy,int dire) {
		super(x, y);
		this.dire=dire;
		this.attackTimer=attackTimer;
		this.health=health;
		this.energy=energy;
		this.s=s;
		this.dir=dir;
		this.isPlayer=isPlayer;
		this.attackNum=attackNum;
		this.typeToString=typeToString;
		this.mpName=mpName;
	}
public int getAttackNum(){
	return attackNum;
}
public boolean isPlayer(){
	return isPlayer;
}
public String getTypeToString(){
	return typeToString;
	
}
public Direction getDir() {
	return dir;
}
public String getS() {
	return s;
}
public String getMpName() {
	return mpName;
}
public void left(){
	isPlayer=(false);
	mpName=("I love cake");
}
public float getHealth(){
	return health;
}
public float getEnergy(){
	return energy;
}
public int getAttackTimer(){
	return attackTimer;
}
public int getDire(){
	return dire;
}
}
