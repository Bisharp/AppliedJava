package com.dig.www.enemies;

import com.dig.www.start.Board;

public class Explosion extends Enemy{
	private int boomTimer = 25;
	public Explosion(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, true, -10);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
		boomTimer--;

		if (boomTimer <= 0)
			alive = false;
	}
}
