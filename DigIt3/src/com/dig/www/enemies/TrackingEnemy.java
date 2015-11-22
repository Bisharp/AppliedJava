package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class TrackingEnemy extends Enemy {

	protected double d = 0;
	protected boolean turn = true;

	public TrackingEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void turnAround(int wallX, int wallY) {
		if (turn)
			super.turnAround(wallX, wallY);
		else
			turn = true;
	}

	@Override
	public void animate() {
		basicAnimate();

		if (stunTimer <= 0 && onScreen) {
			d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
			x += Math.cos((double) Math.toRadians((double) d)) * getSpeed();
			y += Math.sin((double) Math.toRadians((double) d)) * getSpeed();
		}
	}

	@Override
	public boolean willHarm() {
		
		turn = false;
		return super.willHarm();
	}
}
