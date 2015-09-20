package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class SeeShootEnemy extends SeeEnemy {
	
	private int timer = 0;
	private final int DELAY;
	private char identity;

	public SeeShootEnemy(int x, int y, String loc, Board owner, int delay, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
		
		DELAY = delay;
		
		String[] s = loc.split("/");
		identity = s[s.length - 1].charAt(0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		timer++;

		if (timer >= DELAY) {
			addBall();
			timer = 0;
		}
	}

	public void addBall() {
		owner.addEnemy(new Projectile(Statics.pointTowards(new Point(x, y), new Point(
				owner.getCharacterX(), owner.getCharacterY())), x, y, Launch.SPEED,
				this, "images/enemies/blasts/" + identity + ".png", owner, true));
	}
}
