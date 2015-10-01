package com.dig.www.enemies;

import java.awt.Point;

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
			boolean flying,int health) {

		super(width, height, loc, owner, flying,health);

		String[] s = loc.split("/");

		identity = s[s.length - 1].charAt(0);
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

	static final int SPEED = 20;

	public void addBall() {
		owner.addEnemy(new Projectile(pointTowards( new Point(
				owner.getCharacterX()+(owner.getCharacter().getWidth()/2), owner.getCharacterY()+(owner.getCharacter().getHeight()/2))), x, y, SPEED,
				this, "images/enemies/blasts/" + identity + ".png", owner, true));
	}

	

	@Override
	public void turnAround(int wallX, int wallY) {
		// TODO Auto-generated method stub

	}
}
