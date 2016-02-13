package com.dig.www.enemies;

import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class PursuingLaunch extends Launch {

	private double d = 0.0;

	public PursuingLaunch(int width, int height, String loc, Board owner, int delay, boolean flying,int health) {
		super(width, height, loc, owner, delay, flying,health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void turnAround(int wallX, int wallY) {

		int myX = round(x, 2);
		int myY = round(y, 2);
		wallX = round(wallX, 2);
		wallY = round(wallY, 2);

		if (wallX > myX)
			x -= BLOCK * getSpeed();
		else if (wallX < myX)
			x += BLOCK * getSpeed();

		if (wallY > myY)
			y -= BLOCK * getSpeed();
		else if (wallY < myY)
			y += BLOCK * getSpeed();
	}

	public void animate() {

		super.animate();

		if (!owner.getCharacter().getBounds().intersects(getFireBounds())) {

			if (stunTimer <= 0 && onScreen) {

				d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
				x += Math.cos((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();
				y += Math.sin((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();
			}
		}
	}

	private int range = 200;

	private Rectangle getFireBounds() {
		return new Rectangle(x - range, y - range, width + range * 2, height + range * 2);
	}
}
