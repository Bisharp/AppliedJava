package com.dig.www.enemies;

import com.dig.www.start.Board;

public class StandEnemy extends Enemy {

	public StandEnemy(int x, int y, String loc, Board owner, boolean flying) {
		super(x, y, loc, owner, flying);
		// TODO Auto-generated constructor stub
	}

	public StandEnemy(int x, int y, String loc, boolean b) {
		// TODO Auto-generated constructor stub
		super(x, y, loc, b);
	}

	@Override
	public void turnAround() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
	}
}
