package com.dig.www.games.Climb;

import java.awt.Rectangle;

public class Bullet extends Enemy {
	
	public static final int BULLET_WIDTH = 10;

	public Bullet(int x, int y, Climb owner, boolean facingRight) {
		super(x, y, BULLET_WIDTH, BULLET_WIDTH, owner, Type.WALK);
		this.facingRight = facingRight;
	}
	
	public Bullet(int x, int y, String loc, Climb owner, boolean facingRight) {
		super(x, y, loc, owner, Type.WALK);
		this.facingRight = facingRight;
	}

	public void animate() {
		x += SPEED * (facingRight? 1 : -1);
		
		if (x + width < 0 || x > Climb.GH || bumped)
			owner.removeObj(this);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
