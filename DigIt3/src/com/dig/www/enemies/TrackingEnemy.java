package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class TrackingEnemy extends Enemy {

	protected double d = 0;
	protected int speed = 5;

	public TrackingEnemy(int x, int y, String loc, Board owner, boolean flying,int health) {
		super(x, y, loc, owner, flying,health);
		// TODO Auto-generated constructor stub
	}

	public TrackingEnemy(int x, int y, String loc, boolean b) {
		// TODO Auto-generated constructor stub
		super(x, y, loc, b);
	}

	public void turnAround() {
		d += 180;

		if (d > 360)
			d -= 360;

		x += Math.cos((double) Math.toRadians((double) d)) * speed * 2;
		y += Math.sin((double) Math.toRadians((double) d)) * speed * 2;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();

		if (stunTimer <= 0 && onScreen) {

			d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
			x += Math.cos((double) Math.toRadians((double) d)) * speed;
			y += Math.sin((double) Math.toRadians((double) d)) * speed;
		}

	}
}
