package com.dig.www.enemies;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Explosive extends TrackingEnemy {

	public Explosive(int x, int y, String loc, Board owner, boolean flying) {
		super(x, y, loc, owner, flying, -10);
		// TODO Auto-generated constructor stub
	}

	public void turnAround(int wallX, int wallY) {

		owner.getEnemies().remove(this);
		owner.getEnemies().add(new Explosion(x, y, owner));
	}
	
	@Override
	public void interact(Moves move, GameCharacter chr,boolean fromP) {
		
		turnAround(0, 0);
	}
}
