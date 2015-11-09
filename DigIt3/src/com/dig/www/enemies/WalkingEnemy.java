package com.dig.www.enemies;

import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class WalkingEnemy extends Enemy {

	protected int animateTimer = 0;
	protected int scrollX = 0;
	protected int scrollY = 0;
	public static final int ANIMAX = 75;

	public WalkingEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
	}

	public WalkingEnemy(int x, int y, String loc, boolean b) {
		super(x, y, loc, b);
	}

	@Override
	public void turnAround(int wallX, int wallY) {

		super.turnAround(wallX, wallY);
		scrollX *= -1;
		scrollY *= -1;
		animateTimer = 10;
	}

	@Override
	public void animate() {

		basicAnimate();

		if (stunTimer <= 0 && onScreen) {

			if (animateTimer > 0) {
				x += getSpeed() * scrollX;
				y += getSpeed() * scrollY;

				animateTimer--;
			} else {
				switch (Statics.RAND.nextInt(9)) {

				case 0:
					scrollX = 1;
					scrollY = 1;
					break;
				case 1:
					scrollX = 1;
					scrollY = 0;
					break;
				case 2:
					scrollX = 0;
					scrollY = 1;
					break;
				case 3:
					scrollX = 1;
					scrollY = -1;
					break;

				case 4:
					scrollX = -1;
					scrollY = -1;
					break;
				case 5:
					scrollX = -1;
					scrollY = 0;
					break;
				case 6:
					scrollX = 0;
					scrollY = -1;
					break;
				case 7:
					scrollX = -1;
					scrollY = 1;
					break;
				}

				animateTimer = Statics.RAND.nextInt(ANIMAX) + 50;
			}
		}

	}

}
