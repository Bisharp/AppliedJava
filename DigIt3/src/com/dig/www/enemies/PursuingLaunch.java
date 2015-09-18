package com.dig.www.enemies;

import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class PursuingLaunch extends Launch {

	private double d = 0.0;
	private static final int speed = 10;

	public PursuingLaunch(int width, int height, String loc, Board owner, int delay, boolean flying,int health) {
		super(width, height, loc, owner, delay, flying,health);
		// TODO Auto-generated constructor stub
	}

	public PursuingLaunch(int width, int height, String loc, int delay, boolean flying) {
		super(width, height, loc, delay, flying);
		// TODO Auto-generated constructor stub
	}

	public void turnAround() {
		d += 180;

		if (d > 360)
			d -= 360;

		x += Math.cos((double) Math.toRadians((double) d)) * speed * 2;
		y += Math.sin((double) Math.toRadians((double) d)) * speed * 2;
	}

	public void animate() {

		super.animate();

		if (!owner.getCharacter().getBounds().intersects(getFireBounds())) {

			if (stunTimer <= 0 && onScreen) {

				d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
				x += Math.cos((double) Math.toRadians((double) d)) * speed;
				y += Math.sin((double) Math.toRadians((double) d)) * speed;
			}
		}
	}

	private int range = 200;

	private Rectangle getFireBounds() {
		return new Rectangle(x - range, y - range, width + range * 2, height + range * 2);
	}
}
