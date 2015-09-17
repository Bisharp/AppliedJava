package com.dig.www.enemies;

import java.awt.Graphics2D;

import com.dig.www.blocks.Block;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public abstract class Enemy extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient boolean alive = true;
	protected transient boolean onScreen = true;
	// protected transient boolean stunned = false;
	protected transient int stunTimer = 0;
	protected int harmTimer = 0;
	public static final int STUN_MAX = 100;
	public final boolean flying;

	public Enemy(int x, int y, String loc, Board owner, boolean flying) {
		super(x, y, loc, owner);

		this.flying = flying;
		// TODO Auto-generated constructor stub
		alive = true;
	}

	public Enemy(int x, int y, String loc, boolean flying) {
		super(x, y, loc);
		// TODO Auto-generated constructor stub
		this.flying = flying;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

	public abstract void turnAround();

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub

		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			g2d.drawImage(image, x, y, owner);
		} else
			g2d.drawImage(image, x, y, owner);

		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y, owner);
	}

	public void interact(Types type) {
		// TODO Auto-generated method stub

		switch (type) {

		case SPADE:
			break;

		case CLUB:
			stunTimer = STUN_MAX;
			owner.getCharacter().endAction();
			Statics.playSound(owner, "weapons/whop.wav");
			break;

		case DIAMOND:

			if (this instanceof Projectile)
				alive = false;
			break;

		case HEART:
			harmTimer = STUN_MAX / 2;
			break;
		}
	}

	public boolean willHarm() {
		return harmTimer <= 0;
	}

	public void basicAnimate() {
		super.basicAnimate();

		if (stunTimer > 0)
			stunTimer--;

		if (harmTimer > 0)
			harmTimer--;
	}
}
