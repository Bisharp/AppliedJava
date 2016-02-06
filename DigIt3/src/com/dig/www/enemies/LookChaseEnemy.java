package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class LookChaseEnemy extends TrackingEnemy {

	private GameCharacter chara;
	private double d = 0;
	private static transient Image cloud = Statics.newImage("images/effects/shadow.png");
	private static transient Image corruption = Statics.newImage("images/effects/corruption.png");

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

			super.animate();
		} else {
			basicAnimate();
		}

		if (onScreen && Math.sqrt(Math.pow(x - owner.getCharacterX(), 2) + Math.pow(y - owner.getCharacterY(), 2)) <= sight)
			owner.setCorruptedWorld(true);
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);

		if (onScreen && Math.sqrt(Math.pow(x - owner.getCharacterX(), 2) + Math.pow(y - owner.getCharacterY(), 2)) <= sight) {
			g2d.drawImage(cloud, owner.getCharacterX(), owner.getCharacterY(), owner);
			//g2d.drawImage(corruption, 0, 0, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT, owner);
			// g2d.setXORMode(Color.red);
		}
	}

	@Override
	public int getSpeed() {
		return slowTimer <= 0 ? 5 : 2;
	}
}