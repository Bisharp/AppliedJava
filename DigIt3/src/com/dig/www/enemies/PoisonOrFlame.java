package com.dig.www.enemies;

import java.awt.Graphics2D;

import com.dig.www.start.Board;

public class PoisonOrFlame extends StandEnemy{
private boolean isPoison;
	public PoisonOrFlame(int x, int y,boolean isPoison, Board owner) {
		super(x, y, "images/effects/"+(isPoison?"poison":"fire")+".gif", owner, false, -10);
		// TODO Auto-generated constructor stub
		this.isPoison=isPoison;
		damage=3;
	}
	public PoisonOrFlame(int x, int y,boolean isPoison, Board owner,boolean flying) {
		super(x, y, "images/effects/"+(isPoison?"poison":"fire")+".gif", owner, flying, -10);
		// TODO Auto-generated constructor stub
		this.isPoison=isPoison;
		damage=3;
	}
	public PoisonOrFlame(int x,int y,String loc,Board owner,boolean flying){
		super(x,y,loc,owner,flying,-10);
		this.isPoison=loc.endsWith("poison.gif");
		damage=3;
	}
public boolean isPoison(){
	return isPoison;
}
	public boolean poisons() {
		// TODO Auto-generated method stub
		return true;
	}
	protected void drawStatus(Graphics2D g2d) {

	}
	public boolean willHarm() {
		turn = false;
		return alive == true;
	}
	public void setInvincible(boolean setter){
		invincible=setter;
	}
}
