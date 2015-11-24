package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class ChainEnemy extends WalkingEnemy {

	protected ArrayList<ChainEnemy> linkList;
	protected boolean added = false;
	protected ChainEnemy follows;
	protected int links;

	// private MyPoint[] pastPoints;

	public ChainEnemy(int x, int y, String loc, Board owner, boolean flying, int health, int links) {
		super(x, y, loc, owner, flying, health);

		this.links = links;
		follows = null;

		// pastPoints = new MyPoint[links];
		// for (int i = 0; i < links; i++)
		// pastPoints[i] = new MyPoint(x, y);
	}

	public ChainEnemy(int x, int y, String loc, Board owner, boolean flying, int health, ChainEnemy follows) {
		super(x, y, loc, owner, flying, health);

		this.follows = follows;
		added = true;
	}

	protected int FOLLOW = 100;

	public void animate() {

		if (!added) {
			added = true;
			linkList = new ArrayList<ChainEnemy>();
			for (int i = 0; i < links; i++)
				linkList.add(new ChainEnemy(x, y, loc, owner, flying, health / 2, i != 0 ? linkList.get(i - 1) : this));
			owner.getEnemies().addAll(linkList);
		}

		if (follows != null) {
			
			if (!follows.isAlive()) {
				// Another rare occurrence of a do/while() loop
				do {
					follows = follows.getFollows();
				} while (!follows.isAlive());
			}
			
			basicAnimate();
			int dist = (int) Math.pow(follows.getX() - x, 2) + (int) Math.pow(follows.getY() - y, 2);

			if (Math.sqrt(dist) > followingDist()) {
				int amount = getSpeed();

				if (follows.getX() < x)
					x -= amount;
				else if (follows.getX() > x)
					x += amount;

				if (follows.getY() < y)
					y -= amount;
				else if (follows.getY() > y)
					y += amount;
			}
		} else {
			super.animate();
		}
	}

	@Override
	public void turnAround(int wallX, int wallY) {
		if (follows == null)
			super.turnAround(wallX, wallY);
	}

	@Override
	public boolean takeDamage(int amount) {
		if (!super.takeDamage(amount) && follows == null) {
			for (Enemy e : linkList)
				e.setAlive(false);
		}

		return alive;
	}
	
	public ChainEnemy getFollows() {
		return follows;
	}

	public void setDistance(int dist) {
		FOLLOW = dist != -1? dist : FOLLOW;
	}
	
	protected int followingDist() {
		if (follows != null)
			return follows.followingDist();
		else
			return FOLLOW;
	}

	// private class MyPoint {
	// protected int x;
	// protected int y;
	//
	// protected MyPoint(int x2, int y2) {
	// x = x2;
	// y = y2;
	// }
	// }
}
