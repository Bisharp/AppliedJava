package com.dig.www.enemies;

import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class TrackingEnemy extends Enemy {

	protected double d = 0;

	public TrackingEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
	}

	@Override
	public void animate() {
		basicAnimate();

		if (stunTimer <= 0) {
			GameCharacter chara=owner.getCharacter();//getClosest();
			if(new Rectangle(chara.getX()-Statics.BOARD_WIDTH/2, chara.getY()-Statics.BOARD_HEIGHT/2,Statics.BOARD_WIDTH,Statics.BOARD_HEIGHT).intersects(getBounds())){
			d = Statics.pointTowards(new Point((int) x, (int) y), new Point(chara.getX(), chara.getY()));
			x += Math.cos((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();
			y += Math.sin((double) Math.toRadians((double) d)) * getSpeed()*owner.mult();}
		}
	}
}
