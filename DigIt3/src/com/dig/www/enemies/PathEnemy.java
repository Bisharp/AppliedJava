package com.dig.www.enemies;

import com.dig.www.start.Board;

public class PathEnemy extends Enemy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int[][] points;
	protected int position = -1;
	protected int scrollX = 0;
	protected int scrollY = 0;

	protected int startX;
	protected int startY;

	protected static final int MOVE_MAX = 50;
	protected int moveTimer = 0;

	protected Enemy e;

	public PathEnemy(int x, int y, String loc, Board owner, boolean flying, int health, int[][] point) {
		super(x, y, loc, owner, flying, health);

		points = point;

		startX = x;
		startY = y;
	}

	@Override
	public void animate() {

		basicAnimate();

		x += scrollX;
		y += scrollY;
		moveTimer--;
		if (moveTimer <= 0)
			changePos();
	}

	protected void changePos() {

		position++;
		if (position >= points.length) {
			position = 0;

			x = startX;
			y = startY;
		}

		scrollX = points[position][0] * (slowTimer <= 0? 2 : 1);
		scrollY = points[position][1] * (slowTimer <= 0? 2 : 1);
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
}
