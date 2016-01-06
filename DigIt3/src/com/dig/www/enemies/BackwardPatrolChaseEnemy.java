package com.dig.www.enemies;

import com.dig.www.start.Board;

public class BackwardPatrolChaseEnemy extends PatrolChaseEnemy {
	
	private boolean backwards = false;

	public BackwardPatrolChaseEnemy(int x, int y, String loc, Board owner, boolean flying, int health,int[][] point) {
		super(x, y, loc, owner, flying, health, point);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void changePos() {

		position += backwards? -1 : 1;
		
		if (position >= points.length) {
			backwards = true;
			position -= 1;
		} else if (position < 0) {
			backwards = false;
			position += 1;
		}
		
		scrollX = points[position][0] * ((backwards? -2 : 2) / (slowTimer <= 0? 1 : 2));
		scrollY = points[position][1] * ((backwards? -2 : 2) / (slowTimer <= 0? 1 : 2));
		moveTimer = slowTimer <= 0? MOVE_MAX : MOVE_MAX * 2;
	}
}
