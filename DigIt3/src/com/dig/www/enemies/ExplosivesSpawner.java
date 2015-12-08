package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;

public class ExplosivesSpawner extends Launch {

	public ExplosivesSpawner(int width, int height, String loc, Board owner, int delay, boolean flying, int health) {
		super(width, height, loc, owner, delay, flying, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addBall() {
		owner.addEnemy(new Explosive(x, y, "images/enemies/unique/dynamite.png", owner, true));
	}
}
