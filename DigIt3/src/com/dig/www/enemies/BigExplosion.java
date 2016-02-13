package com.dig.www.enemies;

import com.dig.www.start.Board;

public class BigExplosion extends Explosion{
protected int boomTimerTimer=2;
	public BigExplosion(int x, int y, Board owner, int damage) {
		super(x, y, "images/effects/bigExplosion/", owner, damage);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void animate() {
		basicAnimate();
		if(boomTimerTimer<=0){
			boomTimerTimer=2;
		boomTimer+=owner.mult();
		image = newImage(loc);}
		else
			boomTimerTimer-=owner.mult();
		if (boomTimer >= BOOM_MAX)
			alive = false;
	}
}
