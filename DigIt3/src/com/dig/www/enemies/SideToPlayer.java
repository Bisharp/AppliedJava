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
private boolean turning=true;
private boolean drawsShadow;
	public SideToPlayer(int x, int y, String loc, Board owner, boolean xAxis, int health) {
		super(x, y, loc, owner, true, health);
		this.xAxis = xAxis;
		this.damage = Integer.MAX_VALUE;
		drawsShadow=!loc.startsWith("images/effects/");
	}

	@Override
	public void animate() {
		basicAnimate();
		if (xAxis) {
			if (x - speed > owner.getCharacterX()) {
				x -= speed*owner.mult();
				dir = 2;
			} else if (x + speed < owner.getCharacterX()) {
				x += speed*owner.mult();
				dir = 0;
			}
		} else {
			if (y - speed > owner.getCharacterY()) {
				y -= speed*owner.mult();
				dir = 3;
			} else if (y + speed < owner.getCharacterY()) {
				y += speed*owner.mult();
				dir = 1;
			}
		}
	}

	@Override
	public void turnAround(int wallX, int wallY) {

	}

	@Override
	public void draw(Graphics2D g2d) {
if(turning)
		g2d.rotate(Math.toRadians(dir * 90), x + image.getWidth(owner) / 2, y + image.getHeight(owner) / 2);
		g2d.drawImage(image, x, y, owner);
if(drawsShadow)
		drawShadow(g2d);
if(turning)
		g2d.rotate(-Math.toRadians(dir * 90), x + image.getWidth(owner) / 2, y + image.getHeight(owner) / 2);

		drawStatus(g2d);

	}
public void setTurning(boolean turning) {
	this.turning = turning;
}
	@Override
	public void interact(Moves move, GameCharacter chr, boolean fromP) {

	}
}
