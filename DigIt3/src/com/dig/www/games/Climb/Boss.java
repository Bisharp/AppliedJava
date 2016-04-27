package com.dig.www.games.Climb;

import java.awt.Image;

import com.dig.www.util.Statics;

public class Boss extends Enemy {

	protected String name;
	protected Image shoot;
	protected boolean random = false;
	protected int fire;

	public Boss(String nm, Climb owner, boolean facingRight, int level) {
		super(0, Climb.GH / 2, "images/climb/evil/" + nm + "N.gif", owner, null);
		mySpeed = Statics.RAND.nextBoolean() ? getSpeed() : -getSpeed();
		name = nm;
		this.facingRight = facingRight;

		shoot = Statics.newImage("images/climb/evil/" + nm + "A.png");

		if ((int) (level / 4) <= 4)
			fire = 0;
		else if ((int) (level / 4) <= 8)
			fire = 1;
		else
			fire = 2;

		if ((int) (level / 4) >= 12)
			random = true;
	}

	protected int prevDir;

	@Override
	public void animate() {

		prevDir = mySpeed;
		y += mySpeed;
		checkOnScreen();
		if (mySpeed == prevDir && (Math.abs(y - owner.getPlayer().getY()) > SPEED / 2)) {
			if (y < owner.getPlayer().getY())
				mySpeed = getSpeed();
			else if (y > owner.getPlayer().getY())
				mySpeed = -getSpeed();
		}

		shootTimer--;
		if (shootTimer == 0 || shootTimer == -10 || shootTimer == -20)
			owner.addObj(new Bullet(x + width, y, "images/climb/evil/fire.png", owner, facingRight));
		else if (shootTimer <= -10 * fire)
			shootTimer = random ? Statics.RAND.nextInt(getShootMax()) + 10 : getShootMax();
	}
	
	protected int getShootMax() {
		return (int) (SHOOT_MAX * 1.5) - (fire * 10 + (random? 5 : 0));
	}

	protected int getSpeed() {
		return random ? SPEED * 2 / 3 : SPEED / 2;
	}

	@Override
	public void scrollY(int amount) {
		y += amount;
		checkOnScreen();
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public Image getImage() {
		if (shootTimer >= 0)
			return image;
		else
			return shoot;
	}

	protected void checkOnScreen() {
		if (y + height < 0) {
			y = 0;
			mySpeed = SPEED / 2;
		} else if (y > Climb.GH) {
			y = Climb.GH - height;
			mySpeed = -SPEED / 2;
		}
	}

	protected void setOnGround(boolean oG, int gY) {

	}

	protected void switchDir() {

	}
}
