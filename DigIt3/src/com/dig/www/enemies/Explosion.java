package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;

public class Explosion extends Enemy {
	
	protected int boomTimer = -1;
	protected static final int BOOM_MAX = 10;

	public Explosion(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, true, -10);
	}
	public Explosion(int x, int y, Board owner) {
		super(x, y, "images/effects/explosion/", owner, true, -10);
	}
	public Explosion(int x, int y, String loc, Board owner,int damage) {
		super(x, y, loc, owner, true, -10);
		this.damage=damage;
	}
	@Override
	public void animate() {
		basicAnimate();
		
		boomTimer++;
		image = newImage(loc);

		if (boomTimer >= BOOM_MAX)
			alive = false;
	}

	@Override
	public void interact(Moves move, GameCharacter chr, boolean fromP) {

	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
	}
	
	public Image newImage(String loc) {
		return super.newImage(loc + boomTimer + ".png");
	}
	
	public Image newShadow (String loc) {
		return newImage(loc);
	}
}
