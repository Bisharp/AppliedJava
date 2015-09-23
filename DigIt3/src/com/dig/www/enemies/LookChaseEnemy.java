package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class LookChaseEnemy extends StandEnemy {

	private GameCharacter chara;
	private double d = 0;
	private int speed = 10;
	private transient Image cloud = newImage("images/effects/corruption.png");

	public LookChaseEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	}

	private int sight = 400;

	@Override
	public void animate() {

		chara = owner.getCharacter();
		int cX = chara.getX();
		int cY = chara.getY();
		if (((chara.getDirection() == Direction.UP && cY > y) || (chara.getDirection() == Direction.DOWN && cY < y)
				|| (chara.getDirection() == Direction.RIGHT && cX < x) || (chara.getDirection() == Direction.LEFT && cX > x))
				&& onScreen) {

			basicAnimate();
			d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
			x += Math.cos((double) Math.toRadians((double) d)) * speed;
			y += Math.sin((double) Math.toRadians((double) d)) * speed;
		} else {
			super.animate();
		}
	}

	@Override
	public void turnAround(int wallX, int wallY) {

		int myX = round(x, 2);
		int myY = round(y, 2);
		wallX = round(wallX, 2);
		wallY = round(wallY, 2);

		if (wallX > myX)
			x -= BLOCK * speed;
		else if (wallX < myX)
			x += BLOCK * speed;

		if (wallY > myY)
			y -= BLOCK * speed;
		else if (wallY < myY)
			y += BLOCK * speed;
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);

		if (onScreen && Math.sqrt(Math.pow(x - owner.getCharacterX(), 2) + Math.pow(y - owner.getCharacterY(), 2)) <= sight) {
			g2d.drawImage(cloud, owner.getCharacterX(), owner.getCharacterY(), owner);
			g2d.drawImage(cloud, 0, 0, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT, owner);
		}
	}
}