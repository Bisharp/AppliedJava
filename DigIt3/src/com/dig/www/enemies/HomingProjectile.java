package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class HomingProjectile extends Projectile{

	public HomingProjectile(double dir, int x, int y, int speed, Enemy maker,
			String loc, Board owner, boolean flying) {
		super(dir, x, y, speed, maker, loc, owner, flying);
		// TODO Auto-generated constructor stub
	}
	
	public void animate() {

		basicAnimate();
		// Move, This is the code Micah it is also in the ImportantLook class

		d = Statics.pointTowards(new Point((int) x, (int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40));
		
			x += Math.cos((double) Math.toRadians((double) d)) * speed;
			y += Math.sin((double) Math.toRadians((double) d)) * speed;
		
		

		if (!onScreen)
			alive = false;
	}
}
