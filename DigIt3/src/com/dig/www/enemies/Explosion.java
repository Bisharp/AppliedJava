package com.dig.www.enemies;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;

public class Explosion extends Enemy {
	
	private int boomTimer = 25;

	public Explosion(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, true, -10);
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
}
