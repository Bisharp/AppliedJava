package com.dig.www.enemies;

import com.dig.www.start.Board;

public class PoisonOrFlame extends StandEnemy{
private boolean isPoison;
	public PoisonOrFlame(int x, int y,boolean isPoison, Board owner) {
		super(x, y, "images/effects/"+(isPoison?"poison":"fire")+".gif", owner, false, -10);
		// TODO Auto-generated constructor stub
		this.isPoison=isPoison;
		damage=3;
	}
public boolean isPoison(){
	return isPoison;
}
	public boolean poisons() {
		// TODO Auto-generated method stub
		return true;
	}
}
