package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;

public class Launch extends Enemy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private char identity;

	private int timer = 0;
	private final int DELAY;

	public Launch(int width, int height, String loc, Board owner, int delay,
			boolean flying) {

		super(width, height, loc, owner, flying);

		String[] s = loc.split("/");

		identity = s[s.length - 1].charAt(0);
		System.out.println(identity);
		// identity=1;
		DELAY = delay;
	}

	public Launch(int width, int height, String loc, int delay, boolean flying) {

		super(width, height, loc, flying);

		String[] s = loc.split("/");

		identity = s[s.length - 1].charAt(0);
		DELAY = delay;
	}

	public void animate() {

		basicAnimate();

		if (stunTimer <= 0 && willHarm()) {

			if (onScreen && alive) {
				timer++;

				if (timer >= DELAY) {
					addBall();
					timer = 0;
				}
			}
		}
	}

	private static final int SPEED = 10;

	public void addBall() {
		owner.addEnemy(new Projectile(pointTowards(new Point(x, y), new Point(
				owner.getCharacterX(), owner.getCharacterY())), x, y, SPEED,
				this, "images/enemies/blasts/" + identity + ".png", owner, true));
	}

	private static double pointTowards(Point b, Point a) {
		double d;
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		d = (double) (Math.toDegrees(Math.atan2(b.getY() + -a.getY(), b.getX()
				+ -a.getX())) + 180);
		return d;
	}

	@Override
	public void turnAround() {
		// TODO Auto-generated method stub

	}
}
