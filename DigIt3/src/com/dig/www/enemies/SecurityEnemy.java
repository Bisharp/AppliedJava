package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;

public class SecurityEnemy extends SeeEnemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String summon;
	private Enemy e;

	public SecurityEnemy(int x, int y, String loc, Board owner, boolean flying, int health, String summoned) {
		super(x, y, loc, owner, flying, health);

		summon = summoned;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate() {
		super.animate();

		if (e != null && (!e.isAlive() || !e.isOnScreen())) {
			e.setAlive(false);
			e = null;
		}
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		hasTarget = false;

		if (e == null) {
			e = new TrackingEnemy(x, y, "images/enemies/unique/" + summon + ".png", owner, false, 5);
			owner.addEnemy(e);
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);

		g2d.setColor(Color.yellow);
		g2d.fill(getSight());
	}
}
