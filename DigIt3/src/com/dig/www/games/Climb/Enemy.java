package com.dig.www.games.Climb;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.util.Statics;

public class Enemy extends Entity {

	public enum Type {
		STAND, WALK, CONTRARY
	}

	protected static final int SHOOT_MAX = 50;
	protected static final int CONTRARY = 30;
	
	protected Type type;
	protected int mySpeed = SPEED / 2;
	protected int myFall = 0;
	protected boolean onGround = true;
	protected boolean shoots = false;
	protected int shootTimer = SHOOT_MAX;

	public Enemy(int x, int y, String loc, Climb owner, Type type) {
		super(x, y, loc, owner);
		this.type = type;
	}

	public Enemy(int x, int y, String loc, Climb owner, Type type, boolean shoots) {
		super(x, y, loc, owner);
		this.type = type;
		this.shoots = shoots;
	}

	public Enemy(int x, int y, int width, int height, Climb owner, Type type) {
		super(x, y, width, height, owner);
		this.type = type;
	}

	protected void animate() {

		if (!bumped) {
			switch (type) {
			case STAND:
				break;
			case CONTRARY:
				if (Statics.RAND.nextInt(CONTRARY) == 0 && onGround)
					switchDir();
			case WALK:
				x += mySpeed;
				break;
			}

			if (x + width > Climb.GW || x < 0)
				if (onGround)
					switchDir();
				else
					owner.removeObj(this);
		} else {
			bumpTimer--;
			x += bumpMove;

			if (bumpTimer == 0)
				bumped = false;

			if (x + width > Climb.GW || x < 0)
				owner.removeObj(this);
		}

		if (!onGround) {
			myFall++;
			if (myFall > FALL_SPEED)
				myFall = FALL_SPEED;

			y += myFall;

			if (y > Climb.GH)
				owner.removeObj(this);
		} else if (shoots) {
			shootTimer--;

			if (shootTimer == 0) {
				owner.addObj(new Bullet(facingRight ? x + width : x - Bullet.BULLET_WIDTH, y, owner, facingRight));
				shootTimer = SHOOT_MAX;
			}
		}
	}

	protected void switchDir() {
		if (!bumped) {
			mySpeed *= -1;
			facingRight = !facingRight;
		}
	}

	protected void bump(int pX) {

		if (pX > getMidX())
			bumpMove = (int) (-SPEED * 1.5);
		else if (pX < getMidX())
			bumpMove = (int) (SPEED * 1.5);

		bumped = true;
		bumpTimer = BUMP_MAX;
	}

	protected Rectangle getGBounds() {
		return new Rectangle(facingRight ? x + width : x - 5, y + height, 5, 5);
	}

	protected Rectangle getBounds() {
		return new Rectangle(x, y, width, height + 5);
	}

	protected void draw(Graphics2D g2d) {
		if (facingRight)
			g2d.drawImage(image, x, y, owner);
		else
			g2d.drawImage(image, x + width, y, -width, height, owner);
	}

	protected void setOnGround(boolean oG, int gY) {
		if ((onGround = oG) == false)
			return;
		y = gY - height;
	}
	
	protected boolean isOnGround() {
		return onGround;
	}
}
