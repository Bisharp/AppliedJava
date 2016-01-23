package com.dig.www.games.Climb;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;

import com.dig.www.util.Preferences;
import com.dig.www.util.Statics;

public class Character extends Object {

	public enum States {
		GROUND, JUMP, FALL
	}

	protected States state = States.GROUND;
	protected int jumpTimer = 0;
	protected int dX = 0, dY = 0, timer = 0;

	protected static final int TIMER = 10;

	protected static final String loc = "images/climb/character/";
	protected boolean frame0 = true;

	public void setDX(int set) {
		dX = set;
	}

	public static final int FALL_SPEED = -10;
	public static final int SPEED = 15;
	protected boolean facingRight = true, walking = false, jumped = false;

	public Character(int x, int y, int health, Climb owner) {
		super(x, y, health, loc + "n.png", owner);
	}

	public Shape getBounds() {
		return new Rectangle(x + 40, y + 13, width - 80, height - 13);
	}

	@Override
	public void animate(int sX, int sY) {

		switch (state) {
		case JUMP:
			dY--;
			if (dY < FALL_SPEED)
				dY = FALL_SPEED;
			break;

		case FALL:
			dY++;
			if (dY > -FALL_SPEED)
				dY = -FALL_SPEED;
			break;
		default:
			break;
		}

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
		else
			if (dX > 0)
				dX--;
			else if (dX < 0)
				dX++;

		owner.setDeltas(dX, dY);

		if (dY != 0) {
			image = Statics.newImage(loc + "j.png");
		} else if (dX != 0) {
			timer--;
			if (timer <= 0) {
				image = Statics.newImage(loc + "r_" + (frame0 = !frame0) + ".png");
				timer = TIMER;
			}
		} else
			image = Statics.newImage(loc + "n.png");
		
		if (jumpTimer > 0) {
			jumpTimer--;
			if (jumpTimer <= 0)
				state = States.FALL;
		}
		
		x += dX;
		y += dY;
		
		if (x > 500)
			x = 500;
		else if (x < 0)
			x = 0;
	}

	protected void press(int keyCode) {

		if (keyCode == Preferences.LEFT()) {
			facingRight = false;
			walking = true;
		} else if (keyCode == Preferences.RIGHT()) {
			facingRight = true;
			walking = true;
		} else if (keyCode == Preferences.ATTACK() && !jumped) {
			state = States.JUMP;
			jumped = true;
			jumpTimer = TIMER;
		} else if (keyCode == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	protected void release(int keyCode) {

		if (keyCode == Preferences.LEFT() || keyCode == Preferences.RIGHT()) {
			walking = false;
		}
	}
}
