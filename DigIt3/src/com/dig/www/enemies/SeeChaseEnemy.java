package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class SeeChaseEnemy extends SeeEnemy {

	private double d;

	public SeeChaseEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act() {

		d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
		x += Math.cos((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();
		y += Math.sin((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();
	}
	
	@Override
	public void turnAround(int wallX, int wallY) {

		int myX = round(x, 2);
		int myY = round(y, 2);
		wallX = round(wallX, 2);
		wallY = round(wallY, 2);

		if (wallX > myX)
			x -= BLOCK * 1.5;
		else if (wallX < myX)
			x += BLOCK * 1.5;

		if (wallY > myY)
			y -= BLOCK * 1.5;
		else if (wallY < myY)
			y += BLOCK * 1.5;
	}
}
