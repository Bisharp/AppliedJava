package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class ChargeEnemy extends TrackingEnemy {

	private int chargeTimer = 0;
	private static final int CHARGE_MAX = 60;
	private static final int COOLDOWN = 20;
	protected int speedMult = 4;

	protected boolean gX;
	protected boolean gY;

	public ChargeEnemy(int x, int y, String loc, Board owner, boolean flying,int health) {
		super(x, y, loc, owner, flying,health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void turnAround(int wallX, int wallY) {
		super.turnAround(wallX, wallY);
		super.turnAround(wallX, wallY);
		chargeTimer = COOLDOWN;
	}

	@Override
	public void animate() {
		basicAnimate();

		if (onScreen && stunTimer <= 0 && chargeTimer > COOLDOWN) {
			x += Math.cos((double) Math.toRadians((double) d)) * getSpeed() * speedMult;
			y += Math.sin((double) Math.toRadians((double) d)) * getSpeed() * speedMult;
		}

		if (chargeTimer > 0)
			chargeTimer--;
		else {
			chargeTimer = CHARGE_MAX;
			d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());

			gX = x > owner.getCharacterX();
			gY = y > owner.getCharacterY();
		}

//		if (onScreen && (gX && x < owner.getCharacterX()) || (!gX && x > owner.getCharacterX()) || (gY && y < owner.getCharacterY())
//				|| (!gY && y > owner.getCharacterY()))
//			chargeTimer = CHARGE_MAX;
	}
}
