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
	protected int[][] points;
	protected int position = -1;

	protected int startX;
	protected int startY;
protected int currentX;
protected int currentY;
	protected static final int MOVE_MAX = 50;
	protected int moveTimer = 0;

	public PatrolChaseEnemy(int x, int y, String loc, Board owner, boolean flying, int health, int[][] point) {
		super(x, y, loc, owner, flying, health);
		points = point;

		startX = x;
		startY = y;
		currentX = x;
	currentY = y;
	}

	@Override
	public void animate() {

		basicAnimate();
		checkForTarget();

		if (!hasTarget) {
			x += scrollX;
			y += scrollY;
			currentX=x;
			currentY=y;
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
		currentX += owner.getScrollX();
		currentY += owner.getScrollY();
	}
	
	@Override
	public void initialAnimate(int sX, int sY) {
		super.initialAnimate(sX, sY);
		
		startX = x;
		startY = y;

		currentX = x;
		currentY = y;
	}

	@Override
	public void act() {
		if(!isOnScreen()){
		hasTarget = false;
		x=currentX;
		y=currentY;
		return;
		}
		
//Point p=new Point((int)owner.getCharacter().getBounds().getCenterX(),(int)owner.getCharacter().getBounds().getCenterY());
double d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
x += Math.cos((double) Math.toRadians((double) d)) * getSpeed();
y += Math.sin((double) Math.toRadians((double) d)) * getSpeed();
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
			drawBar((double) health / (double) maxHealth, g2d);
			if(!hasTarget){
				g2d.setColor(Color.yellow);
			g2d.fill(getSight());}
	}
}
