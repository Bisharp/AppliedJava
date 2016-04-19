package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

import com.dig.www.character.GameCharacter.Direction;

public class PlayerState extends SpriteState implements Serializable{
protected Direction dir;
protected String s;
protected boolean isPlayer;
protected String typeToString;
protected String mpName;
protected float health;
protected float energy;
protected int dire;
protected boolean isDead;
private int meleeT;
private int rangedT;
private int specialT;
	public PlayerState(int x, int y,int[] timers,Direction dir,String s,boolean isPlayer,String typeToString,String mpName,float health,float energy,int dire,boolean isDead) {
		super(x, y);
		this.dire=dire;
		this.health=health;
		this.energy=energy;
		this.s=s;
		this.dir=dir;
		this.isPlayer=isPlayer;
		this.typeToString=typeToString;
		this.mpName=mpName;
		this.isDead=isDead;
		this.meleeT=timers[0];
		this.specialT=timers[1];
		this.specialT=timers[2];
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

public int getDire(){
	return dire;
}
public boolean isDead(){
	return isDead;
}
public int getMeleeT(){
	return meleeT;
}
public int getRangedT(){
	return rangedT;
}
public int getSpecialT(){
	return specialT;
}
}
