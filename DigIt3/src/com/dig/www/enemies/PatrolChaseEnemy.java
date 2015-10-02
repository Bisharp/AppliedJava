package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class PatrolChaseEnemy extends SeeEnemy {

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

	public PatrolChaseEnemy(int x, int y, String loc, Board owner, boolean flying, int health, String summoned, int[][] point) {
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
			x += scrollX;
			y += scrollY;
			moveTimer--;
			if (moveTimer <= 0)
				changePos();
		} else
			act();

		
	}

	protected void changePos() {

		position++;
		if (position >= points.length) {
			position = 0;

			x = startX;
			y = startY;
		}
		
		scrollX = points[position][0] * 2;
		scrollY = points[position][1] * 2;
		moveTimer = MOVE_MAX;
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
		if(!isOnScreen()){
		hasTarget = false;
		x=startX;
		y=startY;
		for(int c=0;c<position;c++){
			x+=points[c][0]*100;
			y+=points[c][1]*100;
		}
		return;
		}
		
//Point p=new Point((int)owner.getCharacter().getBounds().getCenterX(),(int)owner.getCharacter().getBounds().getCenterY());
double d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
x += Math.cos((double) Math.toRadians((double) d)) * speed;
y += Math.sin((double) Math.toRadians((double) d)) * speed;
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