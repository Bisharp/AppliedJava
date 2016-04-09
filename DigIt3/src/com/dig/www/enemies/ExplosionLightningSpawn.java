package com.dig.www.enemies;

import java.awt.Image;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class ExplosionLightningSpawn extends Explosion {
	protected static final int BOOM_MAX = 25;
	public ExplosionLightningSpawn(int x, int y,Board owner) {
		super(x, y, "images/effects/lightning.gif", owner, 10);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void animate() {
		basicAnimate();
		
		boomTimer+=owner.mult();
		//image = newImage(loc);

		if (boomTimer >= BOOM_MAX){
			alive = false;
		owner.getEnemies().add(new ChargeEnemy(x, y+288, "images/enemies/unique/redStuff.png", owner, true, 100));	
		}
	}
@Override
public Image newImage(String loc) {
	// TODO Auto-generated method stub
	return DigIt.lib.checkLibrary("/" + loc);
}
}
