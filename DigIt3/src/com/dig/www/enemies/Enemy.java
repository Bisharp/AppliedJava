package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Moves;
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
	protected transient int health;
	protected int maxHealth;
	// protected transient boolean stunned = false;
	protected transient int stunTimer = 0;
	protected int harmTimer = 0;
	public static final int STUN_MAX = 100;
	public final boolean flying;
	public static final Font enFont = new Font("Calibri", Font.BOLD, 20);

	public Enemy(int x, int y, String loc, Board owner, boolean flying,int health) {
		super(x, y, loc, owner);
this.maxHealth=health;
		this.health=health;
		this.flying = flying;
		alive = true;
	}

	public Enemy(int x, int y, String loc, boolean flying) {
		super(x, y, loc);
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

		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			g2d.drawImage(image, x, y, owner);
		} else
			g2d.drawImage(image, x, y, owner);

		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y, owner);

		if (!(this instanceof Projectile)) {
//			g2d.setFont(enFont);
//			g2d.setColor(Color.BLACK);
//			g2d.drawString("" + health, x, y - 10);
			drawBar((double)health/(double)maxHealth, g2d);
		}
	}

	public void interact(Moves move) {

		switch (move) {

		// Clark
		case SPADE:
			takeDamage(1);
			break;
		case ARROW:
			takeDamage(2);
			break;
		case PIT:
			break;

		// Carl
		case CLUB:
			stunTimer = STUN_MAX;
			owner.getCharacter().endAction();
			Statics.playSound(owner, "weapons/whop.wav");
			break;
		case MPITCH:
			stunTimer = STUN_MAX;
			owner.getCharacter().endAction();
			Statics.playSound(owner, "weapons/whop.wav");
		case PITCH:
			takeDamage(3);
			break;

		// Destiny
		case AURA:
			harmTimer = STUN_MAX / 2;
			break;
		case HAZE:
			takeDamage(1);
			break;
		case DISPENSER:
			// TODO implement slow code

			// Cain
			
		case SHIELD:
			
			if (this instanceof Projectile)
				alive = false;
			break;
		case CHAIN:
			takeDamage(2);
			break;
		case BASH:
			takeDamage(5);
			stunTimer = STUN_MAX;
			//TODO implement launch
			break;
			default:
				break;
		}
	}

	// TODO temporary method
//	public void interact(Types type) {
//
//		switch (type) {
//
//		case SPADE:
//			break;
//
//		case CLUB:
//			stunTimer = STUN_MAX;
//			takeDamage(1);
//			owner.getCharacter().endAction();
//			Statics.playSound(owner, "weapons/whop.wav");
//			break;
//
//		case DIAMOND:
//
//			if (this instanceof Projectile)
//				alive = false;
//			break;
//
//		case HEART:
//			harmTimer = STUN_MAX / 2;
//			break;
//		}
//	}

	private boolean takeDamage(int i) {

		health--;
owner.getCharacter().endAction();
		if (health <= 0)
			alive = false;

		return health > 0;
	}

	public boolean willHarm() {
		return harmTimer <= 0&&alive==true;
	}

	public void basicAnimate() {
		super.basicAnimate();

		if (stunTimer > 0)
			stunTimer--;

		if (harmTimer > 0)
			harmTimer--;
	}
	@Override
	public void resetImage(Board b) {
	super.resetImage(b);
		health=maxHealth;
	}
}
