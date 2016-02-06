package com.dig.www.games.Climb;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import com.dig.www.games.Climb.Character.States;
import com.dig.www.util.Preferences;
import com.dig.www.util.Statics;

public class Character extends Entity {

	public enum States {
		GROUND, JUMP, FALL, CLIMB
	}

	protected static final int FALL_MAX = 50;

	protected States state = States.FALL;
	protected int jumpTimer = 0;
	protected int dX = 0, dY = 0, timer = 0;
	private int fallTimer = FALL_MAX;

	protected static final int TIMER = 10;
	protected boolean frame0 = true;

	public void setDX(int set) {
		dX = set;
	}

	protected boolean walking = false;
	protected boolean jumped = false, up = false, wants2Climb = false;
	protected boolean hasCat = false;

	public Character(int x, int y, Climb owner) {
		super(x, y, loc + "n.png", owner);
	}

	@Override
	public void animate() {

		switch (state) {
		case JUMP:
			dY--;
			if (dY < -FALL_SPEED)
				dY = FALL_SPEED;
			break;

		case FALL:
			dY++;
			if (dY > FALL_SPEED)
				dY = FALL_SPEED;
			fallTimer--;
			break;
		default:
			if (fallTimer < 0) {
				if (hasCat) {
					fallTimer = FALL_MAX;
					hasCat = false;
				} else
					owner.die();
			} else
				fallTimer = FALL_MAX;
			break;
		}

		if (bumped)
			bumpAnimate();
		else if (state != States.CLIMB)
			normalAnimate();
		else
			climbAnimate();

		if (x + width > Climb.GW)
			x = Climb.GW - width;
		else if (x < 0)
			x = 0;

		if (y > Climb.GH || y < 0) {
			owner.centerScreen();
		}
	}

	protected void normalAnimate() {
		if (walking)
			if (facingRight) {
				dX++;
				if (dX > SPEED)
					dX = SPEED;
			} else {
				dX--;
				if (dX < -SPEED)
					dX = -SPEED;
			}
		else if (dX > 0)
			dX--;
		else if (dX < 0)
			dX++;

		if (dY != 0) {
			newImage("j");
		} else if (dX != 0) {
			imageAnimate('r');
		} else
			newImage("n");

		if (jumpTimer > 0) {
			jumpTimer--;
			if (jumpTimer <= 0)
				state = States.FALL;
		}

		x += dX;
		y += dY;
	}

	protected void climbAnimate() {
		if (walking)
			if (!up) {
				dY++;
				if (dY > SPEED)
					dY = SPEED;
			} else {
				dY--;
				if (dY < -SPEED)
					dY = -SPEED;
			}
		else if (dY > 0)
			dY--;
		else if (dY < 0)
			dY++;

		if (dY != 0)
			imageAnimate('c');
		else
			newImage("c");

		y += dY;
	}

	protected void bumpAnimate() {
		bumpTimer--;
		x += bumpMove;

		if (bumpTimer == 0)
			bumped = false;

		if (x > Climb.GW || x + width < 0)
			owner.die();
	}

	protected void imageAnimate(char im) {
		timer--;
		if (timer <= 0) {
			newImage(im + "_" + (frame0 = !frame0));
			timer = TIMER;
		}
	}

	protected void press(int keyCode) {

		if (state != States.CLIMB) {
			if (keyCode == KeyEvent.VK_LEFT) {
				facingRight = false;
				walking = true;
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				facingRight = true;
				walking = true;
			} else if (keyCode == KeyEvent.VK_SPACE && !jumped) {
				state = States.JUMP;
				jumped = true;
				jumpTimer = TIMER;
			} else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
				wants2Climb = true;
				up = keyCode == KeyEvent.VK_UP;
			}
		} else {
			if (keyCode == KeyEvent.VK_UP) {
				walking = true;
				up = true;
			} else if (keyCode == KeyEvent.VK_DOWN) {
				walking = true;
				up = false;
			} else if (keyCode == KeyEvent.VK_SPACE) {
				jump();
			}
		}
	}

	protected void jump() {
		state = States.JUMP;
		jumped = true;
		jumpTimer = TIMER;
		wants2Climb = false;
		walking = false;
	}

	protected void release(int keyCode) {

		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
			if (state != States.CLIMB)
				walking = false;
		} else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN)
			if (state == States.CLIMB)
				walking = false;
			else
				wants2Climb = false;
	}

	protected void breakFall(int landedY) {
		y = landedY - height;

		if (state == States.GROUND)
			return;
		else if (state == States.CLIMB) {
			wants2Climb = false;
			walking = false;
		}

		dY = 0;
		jumped = false;
		state = States.GROUND;

		owner.centerScreen();
	}

	protected void catchLadder(int newX, int theY) {
		if (wants2Climb) {
			state = States.CLIMB;
			x = newX;
			fallTimer = FALL_MAX;
		}
		
		//else if (theY )
	}

	protected void startFalling() {
		if (state == States.CLIMB)
			walking = false;
		if (state != States.JUMP)
			state = States.FALL;
	}

	protected Rectangle getBounds() {
		switch (state) {
		case JUMP:
		case FALL:
		case CLIMB:
			if (facingRight)
				return new Rectangle(x + 12, getDY() + 7, 26, 31);
			else
				return new Rectangle(x + 11, getDY() + 7, 26, 31);
		default:
			if (facingRight)
				return new Rectangle(x + 12, getDY() + 7, 26, 45);
			else
				return new Rectangle(x + 11, getDY() + 7, 26, 45);
		}
	}

	protected void setCat(boolean hasCat) {
		this.hasCat = hasCat;
	}

	public boolean hasCat() {
		return hasCat;
	}

	protected static final String sLoc = "images/climb/super/";
	protected static final String loc = "images/climb/character/";
	protected static final String end = ".png";

	protected void newImage(String name) {
		image = Statics.newImage((hasCat ? sLoc : loc) + name + end);
	}

	protected void bump(int pX) {

		if (pX - 20 > getMidX())
			bumpMove = (int) (-SPEED * 1.5);
		else if (pX + 20 < getMidX())
			bumpMove = (int) (SPEED * 1.5);
		else {
			bumpMove = 0;
			return;
		}

		bumped = true;
		bumpTimer = BUMP_MAX;
	}

	protected boolean fallenTooFar() {
		return fallTimer < 0;
	}

	public States getState() {
		return state;
	}

	public boolean willStandOnLadder(int y2) {
		return (y > y2 || !wants2Climb) && state != States.CLIMB;
	}
}
