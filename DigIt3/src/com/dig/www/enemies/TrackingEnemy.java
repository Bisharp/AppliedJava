package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;

public class TrackingEnemy extends Enemy {

	private double d = 0;
	private int speed = 5;

	public TrackingEnemy(int x, int y, String loc, Board owner, boolean flying) {
		super(x, y, loc, owner, flying);
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

			d = pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
			x += Math.cos((double) Math.toRadians((double) d)) * speed;
			y += Math.sin((double) Math.toRadians((double) d)) * speed;
		}

	}

	public static double pointTowards(Point b, Point a) {
		double d;
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		d = (double) (Math.toDegrees(Math.atan2(b.getY() + -a.getY(), b.getX()
				+ -a.getX())) + 180);
		// System.out.println(d);

		return d;
	}
}
