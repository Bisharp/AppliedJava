package com.dig.www.enemies;

import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class WalkingEnemy extends Enemy {

	protected int animateTimer = 0;
	protected int scrollX = 0;
	protected int scrollY = 0;
protected int speed=5;
	public static final int ANIMAX = 75;

	public WalkingEnemy(int x, int y, String loc, Board owner, boolean flying,int health) {
		super(x, y, loc, owner, flying,health);
		// TODO Auto-generated constructor stub
	}

	public WalkingEnemy(int x, int y, String loc, boolean b) {
		// TODO Auto-generated constructor stub
		super(x, y, loc, b);
	}

	@Override
	public void turnAround(int wallX, int wallY) {
		// TODO Auto-generated method stub
		scrollX *= -1;
		scrollY *= -1;

		for (int i = 0; i < 10; i++) {
			x += 5 * scrollX;
			y += 5 * scrollY;
			animateTimer = 10;
		}
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();

		if (stunTimer <= 0 && onScreen) {

			if (animateTimer > 0) {
				x += speed * scrollX;
				y += speed * scrollY;

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
