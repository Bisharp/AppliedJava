package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import com.dig.www.MultiPlayer.State.AttackState;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Moves;
import com.dig.www.character.Wizard;
import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public abstract class Enemy extends Sprite implements Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
protected int flameHurt;
protected boolean once;
	protected transient boolean alive = true;
	protected transient int health;
	protected int maxHealth;
	protected int hitstunTimer = 0;
	protected static final int HITSTUN_MAX = 15;
	protected transient int stunTimer = 0;
	protected int harmTimer = 0;
	public static final int STUN_MAX = 100;
	public final boolean flying;
	public static final Font enFont = new Font("Calibri", Font.BOLD, 20);
	protected int damage = 10;

	protected int slowTimer = 0;
	protected int SLOW_MAX = 10;

	protected boolean slipped = false;
	protected static final int SLIP_MAX = 75;
protected int burnTimer;
protected int burnHurtTimer;
	protected boolean invincible = false;
	protected boolean collisionFlagged = false;
	protected boolean turn = true;
public void burn(int amount){
	burnTimer=amount;
	burnHurtTimer=4;
}
public void burnMore(int by){
	burnTimer+=by;
}
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

		if (turn) {
			double tempD = Statics.pointTowards(new Point(x, y), new Point(wallX, wallY)) + 180;
			if (tempD > 360)
				tempD -= 360;
			x += (int) (Math.cos(Math.toRadians((double) tempD)) * BLOCK);
			y += (int) (Math.sin(Math.toRadians((double) tempD)) * BLOCK);

			collisionFlagged = true;
		} else
			turn = true;
	}

	public int round(int round, int val) {

		int div = (int) Math.pow(1, val);
		double d = round / div;
		d = Math.round(d);

		return (int) (d * div);
	}

	@Override
	public void draw(Graphics2D g2d) {
		if(burnTimer>0)
g2d.drawString(""+burnTimer,10,400);
		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			g2d.drawImage(hitstunRenders() ? image : null, x, y, owner);
			drawShadow(g2d, x, y);
		} else {
			if(hitstunRenders()){
			g2d.drawImage(image, x, y, owner);
			drawShadow(g2d);}
		}
		drawStatus(g2d);

		if (!(this instanceof Projectile) && !invincible) {
			// g2d.setFont(enFont);
			// g2d.setColor(Color.BLACK);
			// g2d.drawString("" + health, x, y - 10);
			drawBar((double) health / (double) maxHealth, g2d);
		}
	}
	
	protected void drawShadow(Graphics2D g2d, int x, int y) {
		if (owner.darkenWorld() && !illuminated)
			g2d.drawImage(shadow, x, y, owner);
	}

	protected void drawStatus(Graphics2D g2d) {

		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y,width,height, owner);
		if (slowTimer > 0)
			g2d.drawImage(newImage("images/effects/ice.png"), x, y,width,height, owner);
		if(burnTimer>0)
			g2d.drawImage(newImage("images/effects/fire.gif"), x, y,width,height, owner);
		if (slipped)
			g2d.drawImage(newImage("images/effects/slip.png"), x, y - 59,width,height, owner);

	}

	public void interact(Moves move, GameCharacter character, boolean fromP) {
		switch (move) {

		// Clark
		case SPADE:
			if (!character.hasMeleed()) {
				takeDamage(character.getMeleeDamage(),character);
				character.endAction();
			}
			break;
		case ARROW:
			if (fromP)
				takeDamage(character.getRangedDamage(),character);
			break;
		case PIT:

			GameCharacter.plusXP(owner.getCharacter().getSpecialDamage() / 2,character);
			break;

		// Carl
		case CLUB:
			if (!character.hasMeleed()) {
				stunTimer = STUN_MAX / 10;
				takeDamage(character.getMeleeDamage(),character);
				character.endAction();
				Statics.playSound(owner, "weapons/whop.wav");
			}
			break;
		case MPITCH:
			if (fromP) {
				stunTimer = (int) ((double) STUN_MAX / (double) 1.5);
				// owner.getCharacter().endAction();
				Statics.playSound(owner, "weapons/whop.wav");
				takeDamage(character.getSpecialDamage(),character);
			}
			break;
		case PITCH:
			if (fromP) {

				takeDamage(character.getRangedDamage(),character);
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
				takeDamage(character.getRangedDamage(),character);
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
			if(fromP){
			takeDamage(character.getRangedDamage(),character);
			}
			break;
		case BASH:
			if (!character.hasSpecialed()) {
				takeDamage(character.getSpecialDamage(),character);
				stunTimer = STUN_MAX;
				if (!invincible && !(this instanceof PathEnemy)&&!(this instanceof PatrolChaseEnemy)) {
					int d = (int) pointTowards(new Point(character.getX(), character.getY()));
					d += 180;
					x += Math.cos((double) Math.toRadians((double) d)) * 100*owner.mult();
					y += Math.sin((double) Math.toRadians((double) d)) * 100*owner.mult();
				}
			}
			// TODO implement launch
			break;
		default:
			break;
		case ITEM:
			// I need some of the changes pushed, then this will be better
			takeDamage(100,character);
			break;
		case STAB:
			if (!character.hasMeleed()) {
				takeDamage(character.getMeleeDamage(),character);
				character.endAction();
			}
			break;
		case DIMENSION:
			if (fromP)
				takeDamage(character.getRangedDamage(),character);
			break;
		case WARP:
			if (!character.hasSpecialed()) {
				if (this instanceof Boss)
					takeDamage(character.getSpecialDamage(),character);

				else if (!invincible) {
					owner.getEnemies().add(new CycleExplosion(x, y, "images/portals/normal", owner, 0, 4, 100, null));
					alive = false;
				}

				character.endAction();
			}
			break;

		case WIZ_M:
			if (!character.hasMeleed()) {
				takeDamage(character.getMeleeDamage(),character);
				character.endAction();
			}
			break;
		case WIZ_R:
			if (fromP)
				takeDamage(character.getRangedDamage(),character);
			break;
		case WIZ_S:
			if (!character.hasSpecialed()) {
				takeDamage(character.getSpecialDamage()*((Wizard)character).getDamageMult(),character);
				//character.endAction();
			}
			break;
			
		case MAC_M:
			if (!character.hasMeleed()) {
				takeDamage(character.getMeleeDamage(),character);
				character.endAction();
			}
			break;
			
		case MAC_R:
			if (fromP)
				takeDamage(character.getRangedDamage(),character);
			break;

		case MAC_S:
			if (fromP) {
				//takeDamage(character.getSpecialDamage());
				//character.endAction();
				owner.puddleTimers();
				stunTimer = SLIP_MAX;
				slipped = true;
			}
			break;
		case KYSERYX:
			if (!character.hasMeleed()) {
				takeDamage(character.getMeleeDamage(),character);
				character.endAction();
			}
			break;
		case FIREBALL:
			if (fromP) {
				burnMore(character.getRangedDamage()+5);
				takeDamage(5,character);
			}
			break;
		case FLAME:
			if(!once){
				once=true;
			if(flameHurt<=0){
				flameHurt=8;
				takeDamage(12,character);
				burnMore(character.getSpecialDamage()/2);}
			else
				flameHurt-=owner.mult();
			}
			//burnMore(100);
			break;
		}
	}

	protected boolean takeDamage(int i,GameCharacter chara) {
		if (!invincible) {
			int a = health;
			if (i < a)
				a = i;
			GameCharacter.plusXP(a / 10,chara);
		}

		health -= i;
		hitstunTimer = HITSTUN_MAX;
		// owner.getCharacter().endAction();
		if (health <= 0 && !invincible) {
			alive = false;
			GameCharacter.plusXP(getKillXP(),chara);
		}

		return alive;
	}
public void animate(){
	
}
	public boolean willHarm() {
		turn = false;
		return harmTimer <= 0 && alive == true;
	}

	public void basicAnimate() {
		super.basicAnimate();
once=false;
		if (slowTimer > 0)
			slowTimer-=owner.mult();

		if (stunTimer > 0) {
			stunTimer-=owner.mult();
			if (stunTimer <= 0 && slipped)
				slipped = false;
		}
if(burnTimer>0){
	burnHurtTimer-=owner.mult();
	if(burnHurtTimer<=0){
		burnHurtTimer=4;
	takeDamage(1, null);
	burnTimer--;}
}
		if (harmTimer > 0)
			harmTimer-=owner.mult();

		if (hitstunTimer > 0)
			hitstunTimer-=owner.mult();

		collisionFlagged = false;
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
		return slowTimer <= 0 ? 5 : 2;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public boolean poisons() {
		// for now, only Projectiles poison, full thing to be implemented later
		return false;
	}
public boolean isPoison(){
	return true;
	//is poison, otherwise fire
}
	protected boolean hitstunRenders() {
		return hitstunTimer % 3 == 0 || hitstunTimer <= 0;
	}
	public int getHealth(){
		return health;
	}
	public String getLoc(){
		return loc;
	}
	public GameCharacter getClosest(){
		GameCharacter chara=owner.getCharacter();
		int dist=(int) Statics.dist(x, y,owner.getCharacterX(), owner.getCharacterY());
		for(int c=0;c<owner.getFriends().size();c++){
			if(owner.getFriends().get(c).isDead())
				continue;
			int i=(int)Statics.dist(x, y,owner.getFriends().get(c).getX(), owner.getFriends().get(c).getY());
			if(i<dist){
				chara=owner.getFriends().get(c);
				dist=i;}
		}
		return chara;
	}
	public void setHealth(int setter){
		health=setter;
	}
	public Enemy getClone(){
		try {
			return (Enemy)this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.err.println("Could not clone");
			return new StandEnemy(0, 0, loc, null, flying, health);
		}
	}
	public Image newShadow() {
		// TODO Auto-generated method stub
		return newShadow(loc);
	}
}
