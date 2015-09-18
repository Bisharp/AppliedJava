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

	public ChargeEnemy(int x, int y, String loc, boolean flying) {
		super(x, y, loc, flying);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void turnAround() {
		d += 180;

		if (d > 360)
			d -= 360;

		x += Math.cos((double) Math.toRadians((double) d)) * speed * speedMult * 2;
		y += Math.sin((double) Math.toRadians((double) d)) * speed * speedMult * 2;
		chargeTimer = COOLDOWN;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();

		if (onScreen && stunTimer <= 0 && chargeTimer > COOLDOWN) {
			x += Math.cos((double) Math.toRadians((double) d)) * speed * speedMult;
			y += Math.sin((double) Math.toRadians((double) d)) * speed * speedMult;
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
