package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public abstract class SeeEnemy extends WalkingEnemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int SIGHT_DISTANCE = 1000;
	private boolean hasTarget = false;
	private Polygon lineOfSight;

	public SeeEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate() {

		basicAnimate();

		if (onScreen && !hasTarget && stunTimer <= 0) {
			if (animateTimer > 0) {
				x += 5 * scrollX;
				y += 5 * scrollY;

				animateTimer--;
			} else {
				switch (Statics.RAND.nextInt(4)) {

				case 0:
					scrollX = 1;
					scrollY = 0;
					break;
				case 1:
					scrollX = 0;
					scrollY = 1;
					break;
				case 2:
					scrollX = -1;
					scrollY = 0;
					break;
				case 3:
					scrollX = 0;
					scrollY = -1;
					break;
				}
				animateTimer = Statics.RAND.nextInt(ANIMAX) + 50;
			}
		} else if (onScreen && stunTimer <= 0) {
			
		}

		int[] xs = { x, x + width, x + scrollX * SIGHT_DISTANCE + width, x + scrollX * SIGHT_DISTANCE };
		int[] ys = { y, y + height, y + scrollY * SIGHT_DISTANCE + height, y + scrollY * SIGHT_DISTANCE };
		lineOfSight = new Polygon(xs, ys, xs.length);

		if (lineOfSight.intersects(owner.getCharacter().getBounds()))
			hasTarget = true;
		else if (hasTarget && new Point(x, y).distance(owner.getCharPoint()) >= SIGHT_DISTANCE)
			hasTarget = false;
	}
	
	public abstract void act() ;

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);

		if (lineOfSight != null)
			g2d.draw(lineOfSight);
	}
}