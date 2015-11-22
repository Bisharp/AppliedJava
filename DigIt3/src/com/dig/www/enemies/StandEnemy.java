package com.dig.www.enemies;

import com.dig.www.start.Board;

public class StandEnemy extends Enemy {

	public StandEnemy(int x, int y, String loc, Board owner, boolean flying,int health) {
		super(x, y, loc, owner, flying, health);
	}

	@Override
	public void turnAround(int wallX, int wallY) {

	}
	
	@Override
	public void animate() {
		basicAnimate();
	}
}
