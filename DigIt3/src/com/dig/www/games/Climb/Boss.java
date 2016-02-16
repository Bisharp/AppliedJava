package com.dig.www.games.Climb;

import java.awt.Image;

import com.dig.www.util.Statics;

public class Boss extends Enemy {
	
	
	protected String name;
	protected Image shoot;

	public Boss(String nm, Climb owner, boolean facingRight) {
		super(0, Climb.GH / 2, "images/climb/evil/" + nm + "N.gif", owner, null);
		mySpeed = Statics.RAND.nextBoolean()? SPEED / 2 : -SPEED / 2;
		name = nm;
		this.facingRight = facingRight;
		
		shoot = Statics.newImage("images/climb/evil/" + nm + "A.png");
	}

	protected int prevDir;
	@Override
	public void animate() {
		
		prevDir = mySpeed;
		y += mySpeed;
		checkOnScreen();
		if (mySpeed == prevDir && (Math.abs(y - owner.getPlayer().getY()) > SPEED / 2)) {
			if (y < owner.getPlayer().getY())
				mySpeed = SPEED / 2;
			else if (y > owner.getPlayer().getY())
				mySpeed = -SPEED / 2;
		}
		
		shootTimer--;
		if (shootTimer == 0)
			owner.addObj(new Bullet(x + width, y + height / 3, "images/climb/evil/fire.png", owner, facingRight));
		else if (shootTimer <= -5)
			shootTimer = Statics.RAND.nextInt(SHOOT_MAX) + 5;
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
