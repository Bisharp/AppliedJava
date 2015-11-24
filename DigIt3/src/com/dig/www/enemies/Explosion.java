package com.dig.www.enemies;

import java.awt.Graphics2D;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;

public class Explosion extends Enemy {
	
	protected int boomTimer = 25;

	public Explosion(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, true, -10);
	}
	public Explosion(int x, int y, String loc, Board owner,int damage) {
		super(x, y, loc, owner, true, -10);
		this.damage=damage;
	}
	@Override
	public void animate() {
		basicAnimate();
		boomTimer--;

		if (boomTimer <= 0)
			alive = false;
	}

	@Override
	public void interact(Moves move, GameCharacter chr, boolean fromP) {

	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
	}
}
