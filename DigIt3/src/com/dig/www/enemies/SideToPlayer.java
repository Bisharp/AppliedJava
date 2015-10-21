package com.dig.www.enemies;

import java.awt.Graphics2D;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class SideToPlayer extends Enemy {
	boolean xAxis = true;
	int speed = 1;
	private int dir;

	public SideToPlayer(int x, int y, String loc, Board owner, boolean xAxis, int health) {
		super(x, y, loc, owner, true, health);
		this.xAxis = xAxis;
		this.damage = Integer.MAX_VALUE;
	}

	@Override
	public void animate() {
		basicAnimate();
		if (xAxis) {
			if (x - speed > owner.getCharacterX()) {
				x -= speed;
				dir = 2;
			} else if (x + speed < owner.getCharacterX()) {
				x += speed;
				dir = 0;
			}
		} else {
			if (y - speed > owner.getCharacterY()) {
				y -= speed;
				dir = 3;
			} else if (y + speed < owner.getCharacterY()) {
				y += speed;
				dir = 1;
			}
		}
	}

	@Override
	public void turnAround(int wallX, int wallY) {

	}

	@Override
	public void draw(Graphics2D g2d) {

		g2d.rotate(Math.toRadians(dir * 90), x + image.getWidth(owner) / 2, y + image.getHeight(owner) / 2);
		g2d.drawImage(image, x, y, owner);
		g2d.rotate(-Math.toRadians(dir * 90), x + image.getWidth(owner) / 2, y + image.getHeight(owner) / 2);
		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y, owner);

	}

	@Override
	public void interact(Moves move, GameCharacter chr,boolean fromP) {

	}
}
