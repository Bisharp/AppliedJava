package com.dig.www.enemies;

import java.awt.Image;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class StaticExplosion extends Explosion{

	protected StaticExplosion(int x, int y, String loc, Board owner, int damage) {
		super(x, y, loc, owner, damage);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void animate() {
		basicAnimate();
		
		boomTimer+=owner.mult();
		if (boomTimer >= BOOM_MAX)
			alive = false;
	}
	public Image newImage(String loc) {
		return DigIt.lib.checkLibrary("/" + loc);
	}
}
