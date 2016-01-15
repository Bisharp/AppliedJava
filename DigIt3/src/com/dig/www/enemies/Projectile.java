package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;

import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;

public class Projectile extends Enemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double d;
	protected int speed;
	protected boolean poisons;

	public void setTurning(boolean b) {
		isTurning = b;
	}

	// half height of image
	int hImgX = image.getWidth(null) / 2;
	int hImgY = image.getHeight(null) / 2;
	protected boolean isTurning;

	public Projectile(double dir, int x, int y, int speed, Enemy maker, String loc, Board owner, boolean flying, int damage) {
		super(x, y, loc, owner, flying, 1);

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
		this.damage = damage;
	}

	public Projectile(double dir, int x, int y, int speed, Enemy maker, String loc, Board owner, boolean flying, int damage, boolean poisons) {
		super(x, y, loc, owner, flying, 1);

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
		this.damage = damage;
		this.poisons = poisons;
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (isTurning)
			g2d.rotate(Math.toRadians(d), x + width / 2, y + height / 2);
		g2d.drawImage(image, x, y, owner);
		drawShadow(g2d);
		if (isTurning)
			g2d.rotate(-Math.toRadians(d), x + width / 2, y + height / 2);
	}

	public void animate() {

		basicAnimate();
		// Move, This is the code Micah it is also in the ImportantLook class

		x += Math.cos((double) Math.toRadians((double) d)) * speed;
		y += Math.sin((double) Math.toRadians((double) d)) * speed;

		if (!onScreen)
			alive = false;
	}

	@Override
	public void turnAround(int wallX, int wallY) {
		// TODO Auto-generated method stub
		alive = false;
	}

	@Override
	public void interact(Moves type, GameCharacter chr, boolean fromP) {

		if (type == Moves.SHIELD || type == Moves.DISPENSER)
			alive = false;

	}

	public boolean poisons() {
		return poisons;
	}
	/*
	 * TODO The below method will be deprecated upon Jonah's finishing of adding
	 * extra move code. If this is still present in the second semester, delete
	 * without mercy
	 */

	// TODO delete without mercy
	// @Override
	// public void interact(Types type) {
	//
	// // if (type != Types.CLUB)
	// // super.interact(type);
	// // else
	// // alive = false;
	// }
}