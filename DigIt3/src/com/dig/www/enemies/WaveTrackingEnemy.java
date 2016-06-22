package com.dig.www.enemies;

import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class WaveTrackingEnemy extends Enemy{
	protected double d = 0;

	public WaveTrackingEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
	}

	@Override
	public void animate() {
		basicAnimate();

		if (stunTimer <= 0) {
			GameCharacter chara=getClosest();
			//if(new Rectangle(chara.getX()-Statics.BOARD_WIDTH/2, chara.getY()-Statics.BOARD_HEIGHT/2,Statics.BOARD_WIDTH,Statics.BOARD_HEIGHT).intersects(getBounds())){
			d = Statics.pointTowards(new Point((int) x, (int) y), new Point(chara.getX(), chara.getY()));
			int oldX=x;
			int oldY=y;
			x += Math.cos((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();
			y += Math.sin((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();
			boolean before=true;
		for(int c=0;c<owner.getEnemies().size();c++){
			if(owner.getEnemies().get(c)==this)
				before=false;
			else if(!before&&owner.getEnemies().get(c).getBounds().intersects(getBounds())){
				x =oldX;
				y =oldY;
				x += Math.cos((double) Math.toRadians((double) d)+90) * getSpeed()*owner.mult()*2;
				y += Math.sin((double) Math.toRadians((double) d)+90) * getSpeed()*owner.mult()*2;
			}
			}
		}}
		//}
	}

