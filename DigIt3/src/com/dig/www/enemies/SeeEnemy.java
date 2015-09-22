package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public abstract class SeeEnemy extends WalkingEnemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int SIGHT_DISTANCE = 1000;
	protected boolean hasTarget = false;

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
		} else if (onScreen && stunTimer <= 0 && hasTarget) {
			act();
		}

		if (getSight().intersects(owner.getCharacter().getBounds()))
			hasTarget = true;
		else if (hasTarget && new Point(x, y).distance(owner.getCharPoint()) >= SIGHT_DISTANCE)
			hasTarget = false;
	}

	protected Rectangle getSight() {

		int x = scrollX > 0 ? this.x + width : this.x;
		int y = scrollY > 0 ? this.y + height : this.y;
		int width = scrollX == 0 ? this.width : SIGHT_DISTANCE * scrollX;
		int height = scrollY == 0 ? this.height : SIGHT_DISTANCE * scrollY;

		if (width < 0)
			return new Rectangle(x + width, y, -width, height);
		else if (height < 0)
			return new Rectangle(x, y + height, width, -height);
		else
			return new Rectangle(x, y, width, height);
	}

	public abstract void act();

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}
}