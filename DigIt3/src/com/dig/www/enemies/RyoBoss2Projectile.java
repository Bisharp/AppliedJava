package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;

public class RyoBoss2Projectile extends Projectile{
private int endTimer=75;
	public RyoBoss2Projectile(double dir, int x, int y, int speed, Enemy maker, Board owner,
			int damage) {
		super(dir, x, y, speed, maker, "images/characters/projectiles/fireball.gif", owner, true, damage, true);
		// TODO Auto-generated constructor stub
		isTurning=true;
	}
	@Override
		public void basicAnimate() {
			// TODO Auto-generated method stub
			super.basicAnimate();
			endTimer--;
			if(endTimer<0){
				boolean b=true;
				if(owner.getCharPoint().distance(x, y)<250)
					b=false;
				if(b)
				for(int c=0;c<owner.getFriends().size();c++)
					if(new Point(owner.getFriends().get(c).getX(),owner.getFriends().get(c).getY()).distance(x, y)<250){
						b=false;
						break;
					}
				
				if(b){
				alive=false;
				owner.getEnemies().add(new PoisonOrFlame(x, y, false, owner));
				}}
		}
@Override
public boolean isPoison() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public void turnAround(int wallX, int wallY) {
	alive=false;
	x -= Math.cos((double) Math.toRadians((double) d)) * 50;
	y -= Math.sin((double) Math.toRadians((double) d)) * 50;
	boolean b=true;
	if(owner.getCharPoint().distance(x, y)<250)
		b=false;
	if(b){
	for(int c=0;c<owner.getFriends().size();c++)
		if(new Point(owner.getFriends().get(c).getX(),owner.getFriends().get(c).getY()).distance(x, y)<250){
			b=false;
			break;
		}
	
	if(b)
	owner.getEnemies().add(new PoisonOrFlame(x, y, false, owner));
	}

}
public void animate() {

	basicAnimate();
	x += Math.cos((double) Math.toRadians((double) d)) * speed*owner.mult();
	y += Math.sin((double) Math.toRadians((double) d)) * speed*owner.mult();
}
}
