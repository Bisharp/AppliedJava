package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;

public class PatrolSecurityEnemy extends SeeEnemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String summon;
	protected int[][] points;
	protected int position = -1;

	protected int startX;
	protected int startY;

	protected static final int MOVE_MAX = 50;
	protected int moveTimer = 0;

	protected Enemy e;

	public PatrolSecurityEnemy(int x, int y, String loc, Board owner, boolean flying, int health, String summoned, int[][] point) {
		super(x, y, loc, owner, flying, health);

		summon = summoned;
		points = point;

		startX = x;
		startY = y;
	}

	@Override
	public void animate() {

		basicAnimate();
		checkForTarget();

		if (!hasTarget) {
			x += scrollX*owner.mult();
			y += scrollY*owner.mult();
			moveTimer-=owner.mult();
			if (moveTimer <= 0)
				changePos();
		} else
			act();

		if (e != null && (!e.isAlive() || !e.isOnScreen())) {
			e.setAlive(false);
			e = null;
		}
	}

	protected void changePos() {

		position++;
		if (position >= points.length) {
			position = 0;

			x = startX;
			y = startY;
		}

		scrollX = points[position][0] * (slowTimer <= 0? 2 : 1)*owner.mult();
		scrollY = points[position][1] * (slowTimer <= 0? 2 : 1)*owner.mult();
		moveTimer = slowTimer <= 0? MOVE_MAX : MOVE_MAX * 2;
	}

	@Override
	public void turnAround(int wallX, int wallY) {

	}

	@Override
	public void basicAnimate() {

		super.basicAnimate();

		startX += owner.getScrollX();
		startY += owner.getScrollY();
	}

	@Override
	public void initialAnimate(int sX, int sY) {
		super.initialAnimate(sX, sY);

		startX = x;
		startY = y;
	}

	@Override
	public void act() {
		hasTarget = false;

		if (e == null) {
			e = new TrackingEnemy(x, y, summon, owner, false, 5);
			owner.addEnemy(e);
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);

		g2d.setColor(Color.yellow);
		g2d.fill(getSight());

		if (scrollY < 0)
			drawBar((double) health / (double) maxHealth, g2d);
	}
}
