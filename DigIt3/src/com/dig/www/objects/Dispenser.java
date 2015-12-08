package com.dig.www.objects;

import java.awt.Graphics2D;
import java.awt.Image;

import com.dig.www.character.Field;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.Heart;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Dispenser extends Objects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int MAX = 10;
	public static final int DECREMENT = 2;
	private int timer = MAX;

	private Field field;
	private Heart maker;

	public Dispenser(int x, int y, GameCharacter maker, String loc, Board owner, double dir) {
		super(x, y, loc, false, owner,"A dispenser placed using Destiny's wand given to her by the wizard.\n It blocks projectiles, heals friendlies, and slows enemies.");

		Image img = maker.getImage();
		int aSpeed;

		if (img.getWidth(null) >= img.getHeight(null)) {
			aSpeed = (int) (img.getWidth(null));
		} else {
			aSpeed = (int) (img.getHeight(null));
		}
		// This is the move
		this.x += Math.cos((double) Math.toRadians((double) dir)) * aSpeed;
		this.y += Math.sin((double) Math.toRadians((double) dir)) * aSpeed;

		field = new Field(0, this.x, this.y, maker, Statics.DUMMY, owner);
		owner.getfP().add(field);

		this.maker = (Heart) maker;
		shadow = null;
	}

	public void animate() {
		super.animate();
		timer--;

		if (timer <= 0) {
			maker.decrementEnergy(DECREMENT);
			timer = MAX;
		}
	}

	public void basicAnimate() {
		super.basicAnimate();
		field.dBasicAnimate();
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		field.draw(g2d);
	}
}
