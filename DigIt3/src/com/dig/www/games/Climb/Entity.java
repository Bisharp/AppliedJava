package com.dig.www.games.Climb;

public abstract class Entity extends Object {

	public static final int SPEED = 5;
	public static final int FALL_SPEED = 10;
	public static final int BUMP_MAX = 10;
	
	protected boolean bumped = false;
	protected int bumpTimer = 0;
	protected int bumpMove;
	
	protected boolean facingRight = true;
	
	public Entity(int x, int y, String loc, Climb owner) {
		super(x, y, loc, owner);
	}

	public Entity(int x, int y, int width, int height, Climb owner) {
		super(x, y, width, height, owner);
	}

	abstract void bump(int bX);
	
	protected int getMidX() {
		return x + width / 2;
	}
}
