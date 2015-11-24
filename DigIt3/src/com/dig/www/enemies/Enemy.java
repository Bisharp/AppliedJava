package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dig.www.character.GameCharacter;
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
	protected transient int health;
	protected int maxHealth;
	// protected transient boolean stunned = false;
	protected transient int stunTimer = 0;
	protected int harmTimer = 0;
	public static final int STUN_MAX = 100;
	public final boolean flying;
	public static final Font enFont = new Font("Calibri", Font.BOLD, 20);
	protected int damage = 10;
	
	protected int slowTimer = 0;
	protected int SLOW_MAX = 10;

	protected boolean invincible = false;

	public Enemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner);
		this.maxHealth = health;
		this.health = health;

		if (health < 0)
			invincible = true;

		this.flying = flying;
		alive = true;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	protected static final int BLOCK = 10;

	public void turnAround(int wallX, int wallY) {

		int myX = round(x, 2);
		int myY = round(y, 2);
		wallX = round(wallX, 2);
		wallY = round(wallY, 2);

		if (wallX > myX)
			x -= BLOCK;
		else if (wallX < myX)
			x += BLOCK;

		if (wallY > myY)
			y -= BLOCK;
		else if (wallY < myY)
			y += BLOCK;
	}

	public int round(int round, int val) {

		int div = (int) Math.pow(1, val);
		double d = round / div;
		d = Math.round(d);

		return (int) (d * div);
	}

	@Override
	public void draw(Graphics2D g2d) {

		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			g2d.drawImage(image, x, y, owner);
			if (owner.darkenWorld())
				g2d.drawImage(shadow, x, y, owner);
		} else {
			g2d.drawImage(image, x, y, owner);
			if (owner.darkenWorld())
				g2d.drawImage(shadow, x, y, owner);
		}

		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y, owner);
		else if (slowTimer > 0)
			g2d.drawImage(newImage("images/effects/ice.png"), x, y, owner);

		if (!(this instanceof Projectile) && !invincible) {
			// g2d.setFont(enFont);
			// g2d.setColor(Color.BLACK);
			// g2d.drawString("" + health, x, y - 10);
			drawBar((double) health / (double) maxHealth, g2d);
		}
	}

	public void interact(Moves move, GameCharacter character,boolean fromP) {


		switch (move) {

		// Clark
		case SPADE:
			if(!character.hasMeleed()){
			takeDamage(character.getMeleeDamage());
			character.endAction();}
			break;
		case ARROW:
			if(fromP)
			takeDamage(character.getRangedDamage());
			break;
		case PIT:

			GameCharacter.plusXP(owner.getCharacter().getSpecialDamage() / 2);
			break;

		// Carl
		case CLUB:
			if(!character.hasMeleed()){
			stunTimer = STUN_MAX / 10;
			takeDamage(character.getMeleeDamage());
			character.endAction();
			Statics.playSound(owner, "weapons/whop.wav");
			}
			break;
		case MPITCH:
			if (fromP) {
				stunTimer = (int) ((double) STUN_MAX / (double) 1.5);
				// owner.getCharacter().endAction();
				Statics.playSound(owner, "weapons/whop.wav");
				takeDamage(character.getSpecialDamage());
			}
			break;
		case PITCH:
			if (fromP) {

				takeDamage(character.getRangedDamage());
			}
			break;

		// Destiny
		case AURA:
			harmTimer = STUN_MAX / 2// *
									// (owner.getCharacter().getMeleeDamage()/4)
			;

			break;
		case HAZE:
			if (fromP) {
				takeDamage(character.getRangedDamage());
			}
			break;
		case DISPENSER:
			// TODO implement slow code
			slowTimer = SLOW_MAX;
			break;
		// Cain

		case SHIELD:

			if (this instanceof Projectile)
				alive = false;
			break;
		case CHAIN:
			takeDamage(character.getRangedDamage());
			break;
		case BASH:
			if(!character.hasSpecialed()){
			takeDamage(character.getSpecialDamage());
			stunTimer = STUN_MAX;
			if(!invincible&&! (this instanceof PathEnemy)){
			int d = (int) pointTowards(new Point(character.getX(), character.getY()));
			d += 180;
			x += Math.cos((double) Math.toRadians((double) d)) * 100;
			y += Math.sin((double) Math.toRadians((double) d)) * 100;}}
			// TODO implement launch
			break;
		default:
			break;
		case ITEM:
			//I need some of the changes pushed, then this will be better
			takeDamage(100);
			break;
		case STAB:
			if(!character.hasMeleed()){
			takeDamage(character.getMeleeDamage());
			character.endAction();}
			break;
		case DIMENSION:
			if (fromP)
			takeDamage(character.getRangedDamage());
			break;
		case WARP:
			if(!character.hasSpecialed()){
			if(this instanceof Boss)
			takeDamage(character.getSpecialDamage());
			
			else if(!invincible){
				owner.getEnemies().add(new CycleExplosion(x, y, "images/portals/normal", owner, 0,4,100));
			alive=false;
			}
			
			character.endAction();	}
			break;
		}
	}

	protected boolean takeDamage(int i) {
if (!invincible){
	int a=health;
	if(i<a)
		a=i;
			GameCharacter.plusXP(a/10);}
		health -= i;
		// owner.getCharacter().endAction();
		if (health <= 0 && !invincible) {
			alive = false;
			GameCharacter.plusXP(getKillXP());
		}
		
		return alive;
	}

	public boolean willHarm() {
		return harmTimer <= 0 && alive == true;
	}

	public void basicAnimate() {
		super.basicAnimate();
		
		if (slowTimer > 0)
			slowTimer--;

		if (stunTimer > 0)
			stunTimer--;

		if (harmTimer > 0)
			harmTimer--;
	}

	@Override
	public void resetImage(Board b) {
		super.resetImage(b);
		health = maxHealth;
	}

	public int getDamage() {
		return damage;
	}

	protected double pointTowards(Point a) {
		double d;
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		Point b = new Point(x, y);
		d = (double) (Math.toDegrees(Math.atan2(b.getY() + -a.getY(), b.getX() + -a.getX())) + 180);
		return d;
	}

	public int getKillXP() {
		return 5;
	}
	
	public int getSpeed() {
		return slowTimer <= 0? 5 : 2;
	}
	public boolean isInvincible(){
		return invincible;
	}
	public boolean poisons(){
		//for now, only Projectiles poison, full thing to be implemented later
		return false;
	}
}
