package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;

import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;

public class Projectile extends Enemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double d;
	int speed;

	// half height of image
	int hImgX = image.getWidth(null) / 2;
	int hImgY = image.getHeight(null) / 2;

	public Projectile(double dir, int x, int y, int speed, Launch maker, String loc, Board owner, boolean flying) {
		super(x, y, loc, owner, flying,1);
		
		
		d = dir;
		this.speed = speed;

		Image img = maker.getImage();
		int aSpeed;
		// Moves the ball away from center of launcher's image
		if (img.getWidth(null) >= img.getHeight(null)) {
			aSpeed = (int) (img.getWidth(null) / 2);
		} else {
			aSpeed = (int) (img.getHeight(null) / 2);
		}
		// This is the move
		this.x += Math.cos((double) Math.toRadians((double) dir)) * aSpeed;
		this.y += Math.sin((double) Math.toRadians((double) dir)) * aSpeed;
	}

	public void animate() {

		basicAnimate();
		// Move, This is the code Micah it is also in the ImportantLook class

		if (stunTimer <= 0) {
			x += Math.cos((double) Math.toRadians((double) d)) * speed;
			y += Math.sin((double) Math.toRadians((double) d)) * speed;
		} else
			stunTimer--;

		if (!onScreen)
			alive = false;
	}

	@Override
	public void turnAround() {
		// TODO Auto-generated method stub
		alive = false;
	}

	@Override
	public void interact(Moves type) {

		if (type != Moves.CLUB)
			super.interact(type);
		else
			alive = false;
	}

	/* 
	 * TODO The below method will be deprecated upon Jonah's finishing of adding
	 * extra move code. If this is still present in the second semester, delete
	 * without mercy
	 */
	
	@Override
	public void interact(Types type) {

//		if (type != Types.CLUB)
//			super.interact(type);
//		else
//			alive = false;
	}
}