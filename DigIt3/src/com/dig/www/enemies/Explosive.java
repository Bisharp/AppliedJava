package com.dig.www.enemies;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Explosive extends TrackingEnemy {

	private boolean exploded = false;
	private int boomTimer = 20;

	public Explosive(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	}

	public void turnAround(int wallX, int wallY) {

		if (!exploded) {
			exploded = true;
			image = newImage("images/effects/explosion.png");
			width = image.getWidth(null);
			height = image.getHeight(null);

			Statics.playSound(owner, "enemy/boom.wav");
		}
	}

	@Override
	public void animate() {

		if (!exploded)
			super.animate();
		else {
			basicAnimate();
			boomTimer--;

			if (boomTimer <= 0)
				alive = false;
		}
	}
	
	@Override
	public void interact(Moves move, GameCharacter chr,boolean fromP) {
		
		if (!exploded)
			super.interact(move, chr,fromP);
	}
}
