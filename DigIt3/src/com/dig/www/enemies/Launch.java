package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.character.GameCharacter;
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
		if(identity=='2')
			identity='0';
		// identity=1;
		DELAY = delay;
	}

	public void animate() {

		basicAnimate();

		if (stunTimer <= 0 && willHarm()) {

			if (onScreen && alive) {
				timer+=owner.mult();

				if (timer >= DELAY) {
					addBall();
					timer = 0;
				}
			}
		}
	}

	static final int SPEED = 20;

	public void addBall() {
		GameCharacter chara=getClosest();
		owner.addEnemy(new Projectile(pointTowards( new Point(
				chara.getX()+(chara.getWidth()/2), chara.getY()+(chara.getHeight()/2))), x, y, SPEED,
				this, "images/enemies/blasts/" + identity + ".png", owner, true,10));
	}

	

	@Override
	public void turnAround(int wallX, int wallY) {
		// TODO Auto-generated method stub

	}
}
